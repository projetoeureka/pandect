(ns ^:no-doc pandect.gen.hmac-generator
  (:require [pandect.gen.core :refer :all])
  (:import [java.io File FileInputStream InputStream]))

;; ## Coercion

(defprotocol ByteArrayConvertable
  "Protocol for Entities that can be converted to byte arrays."
  (convert-to-byte-array [this]))

(extend-protocol ByteArrayConvertable
  (class (byte-array 0))
  (convert-to-byte-array [this] this)
  String
  (convert-to-byte-array [this] (.getBytes this "UTF-8"))
  java.io.File
  (convert-to-byte-array [this]
    (.getBytes ^String (slurp this) "UTF-8"))
  java.io.InputStream
  (convert-to-byte-array [this]
    (.getBytes ^String (slurp this) "UTF-8")))

;; ## Generation

(defprotocol HMACGen
  (base-symbol [this sym]
    "Create base symbol for function generation.")
  (bytes->hmac [this msg-form key-form]
    "Generate code to convert the byte array produced by the given `msg-form`
     to a value representing the hash-based message authentication code using the given
     `key-form` (a byte array).")
  (stream->hmac [this stream-form key-form buffer-size]
    "Generate code to convert the input stream produced by the given `stream-form`
     to a value representing the hash-based message authentication code using the given
     `key-form` (a byte array).")
  (hmac->string [this form]
    "Generate code to convert the HMAC value produced by the given form to
     a hex string.")
  (hmac->bytes [this form]
    "Generate code to convert the HMAC value produced by the given form to
     a byte array."))

(def hmac-generator
  "Generates function representing a HMAC algorithm:

   - `X-hmac` : outputs hex string
   - `X-hmac-bytes` : outputs byte array
   - `X-hmac-file` : input is path to file, outputs string
   - `X-hmac-file-bytes` : input is path to file, outputs byte array
   - `X-hmac*` : outputs the actual HMAC value generated by the given function
   - `X-hmac-file*` : input is path to file, outputs the actual HMAC value generated by the given function

   "
  (reify Generator
    (can-generate? [_ code-gen]
      (satisfies? HMACGen code-gen))
    (generate-protocol [_ code-gen id buffer-size]
      (let [f (vary-meta id assoc :private true)
            sym (gensym "data")
            k (gensym "key")
            P (with-meta (gensym) {:private true})
            stream-form (stream->hmac code-gen sym k buffer-size)]
        `(do
           (defprotocol ~P
             (~f [this# key#]))
           (doseq [v# [(var ~P) (var ~f)]]
             (alter-meta! v# assoc :private true))
           (extend-protocol ~P
             (class (byte-array 0))
             (~f [~sym ~k] ~(bytes->hmac code-gen sym k))
             String
             (~f [~sym ~k] ~(bytes->hmac code-gen `(.getBytes ~sym "UTF-8") k))
             InputStream
             (~f [~sym ~k] ~stream-form)
             File
             (~f [~sym ~k]
               ~(wrap-file-stream stream-form sym))))))
    (generate-functions [_ code-gen id f]
      (let [f (base-symbol code-gen f)
            algorithm (algorithm-string code-gen)
            sym 'x
            fsym (vary-meta sym assoc :tag `String)
            k 'secret
            mk (fn [suffix docstring call]
                 `(defn ~(symbol+ f suffix)
                    ~(format
                       (str "[HMAC] %s (%s)%nThe secret can be given as any "
                            "value implementing `pandect.gen.hmac-generator/ByteConvertable`.")
                       algorithm docstring)
                    [~sym ~k]
                    ~call))
            call `(~id ~sym (convert-to-byte-array ~k))
            call-file (wrap-file-stream call sym fsym)]
        (->> [[:*          "raw value"                 call]
              [:file*      "file path -> raw value"  call-file]
              [:file-bytes "file path -> byte array" (hmac->bytes code-gen call-file)]
              [:file       "file path -> string"     (hmac->string code-gen call-file)]
              [:bytes      "value -> byte array"     (hmac->bytes code-gen call)]
              [nil         "value -> string"         (hmac->string code-gen call)]]
             (mapv #(apply mk %)))))))