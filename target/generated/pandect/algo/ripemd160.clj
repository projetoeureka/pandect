(ns
 pandect.algo.ripemd160
 "RipeMD160 algorithm implementation\n\n(requires `org.bouncycastle/bcprov-jdk15on` to be on the classpath)"
 (:require
  [pandect.buffer :refer [*buffer-size*]]
  [pandect.utils.convert]
  pandect.utils.bouncy-castle-provider))
(do
 (do
  (clojure.core/defprotocol G__1231 (compute-ripemd1601229 [data1230]))
  (clojure.core/doseq
   [v__186__auto__ [#'G__1231 #'compute-ripemd1601229]]
   (clojure.core/alter-meta!
    v__186__auto__
    clojure.core/assoc
    :private
    true))
  (clojure.core/extend-protocol
   G__1231
   (clojure.core/class (clojure.core/byte-array 0))
   (compute-ripemd1601229
    [data1230]
    (clojure.core/let
     [md__668__auto__
      (java.security.MessageDigest/getInstance "RipeMD160")]
     (.digest md__668__auto__ data1230)))
   java.lang.String
   (compute-ripemd1601229
    [data1230]
    (clojure.core/let
     [data1230 (.getBytes data1230 "UTF-8")]
     (clojure.core/let
      [md__668__auto__
       (java.security.MessageDigest/getInstance "RipeMD160")]
      (.digest md__668__auto__ data1230)))))
  (clojure.core/extend-protocol
   G__1231
   java.io.InputStream
   (compute-ripemd1601229
    [data1230]
    (clojure.core/let
     [md__669__auto__
      (java.security.MessageDigest/getInstance "RipeMD160")
      c__670__auto__
      (clojure.core/int *buffer-size*)
      buf__671__auto__
      (clojure.core/byte-array c__670__auto__)
      s__672__auto__
      data1230]
     (clojure.core/loop
      []
      (clojure.core/let
       [r__673__auto__
        (.read s__672__auto__ buf__671__auto__ 0 c__670__auto__)]
       (clojure.core/when-not
        (clojure.core/= r__673__auto__ -1)
        (.update md__669__auto__ buf__671__auto__ 0 r__673__auto__)
        (recur))))
     (.digest md__669__auto__)))
   java.io.File
   (compute-ripemd1601229
    [data1230]
    (clojure.core/with-open
     [data1230 (clojure.java.io/input-stream data1230)]
     (clojure.core/let
      [md__669__auto__
       (java.security.MessageDigest/getInstance "RipeMD160")
       c__670__auto__
       (clojure.core/int *buffer-size*)
       buf__671__auto__
       (clojure.core/byte-array c__670__auto__)
       s__672__auto__
       data1230]
      (clojure.core/loop
       []
       (clojure.core/let
        [r__673__auto__
         (.read s__672__auto__ buf__671__auto__ 0 c__670__auto__)]
        (clojure.core/when-not
         (clojure.core/= r__673__auto__ -1)
         (.update md__669__auto__ buf__671__auto__ 0 r__673__auto__)
         (recur))))
      (.digest md__669__auto__)))))
  'G__1231)
 (do
  (clojure.core/defn
   ripemd160*
   "[Hash] RipeMD160 (raw value)"
   [x]
   (compute-ripemd1601229 x))
  (clojure.core/defn
   ripemd160-file*
   "[Hash] RipeMD160 (raw value)"
   [x]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (compute-ripemd1601229 x)))
  (clojure.core/defn
   ripemd160-bytes
   "[Hash] RipeMD160 (value -> byte array)"
   [x]
   (compute-ripemd1601229 x))
  (clojure.core/defn
   ripemd160-file-bytes
   "[Hash] RipeMD160 (file path -> byte array)"
   [x]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (compute-ripemd1601229 x)))
  (clojure.core/defn
   ripemd160
   "[Hash] RipeMD160 (value -> string)"
   [x]
   (pandect.utils.convert/bytes->hex (compute-ripemd1601229 x)))
  (clojure.core/defn
   ripemd160-file
   "[Hash] RipeMD160 (file path -> string)"
   [x]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (pandect.utils.convert/bytes->hex (compute-ripemd1601229 x))))))
(do
 (do
  (clojure.core/defprotocol
   G__1243
   (compute-ripemd1601240 [data1241 key1242]))
  (clojure.core/doseq
   [v__186__auto__ [#'G__1243 #'compute-ripemd1601240]]
   (clojure.core/alter-meta!
    v__186__auto__
    clojure.core/assoc
    :private
    true))
  (clojure.core/extend-protocol
   G__1243
   (clojure.core/class (clojure.core/byte-array 0))
   (compute-ripemd1601240
    [data1241 key1242]
    (clojure.core/let
     [mac__543__auto__
      (javax.crypto.Mac/getInstance "Hmac-RipeMD160")
      msg__544__auto__
      (clojure.core/bytes data1241)
      k__545__auto__
      (javax.crypto.spec.SecretKeySpec. key1242 "Hmac-RipeMD160")]
     (clojure.core/->
      (clojure.core/doto
       mac__543__auto__
       (.init k__545__auto__)
       (.update msg__544__auto__))
      (.doFinal))))
   java.lang.String
   (compute-ripemd1601240
    [data1241 key1242]
    (clojure.core/let
     [data1241 (.getBytes data1241 "UTF-8")]
     (clojure.core/let
      [mac__543__auto__
       (javax.crypto.Mac/getInstance "Hmac-RipeMD160")
       msg__544__auto__
       (clojure.core/bytes data1241)
       k__545__auto__
       (javax.crypto.spec.SecretKeySpec. key1242 "Hmac-RipeMD160")]
      (clojure.core/->
       (clojure.core/doto
        mac__543__auto__
        (.init k__545__auto__)
        (.update msg__544__auto__))
       (.doFinal))))))
  (clojure.core/extend-protocol
   G__1243
   java.io.InputStream
   (compute-ripemd1601240
    [data1241 key1242]
    (clojure.core/let
     [mac__546__auto__
      (javax.crypto.Mac/getInstance "Hmac-RipeMD160")
      k__547__auto__
      (javax.crypto.spec.SecretKeySpec. key1242 "Hmac-RipeMD160")
      c__548__auto__
      (clojure.core/int *buffer-size*)
      buf__549__auto__
      (clojure.core/byte-array c__548__auto__)
      s__550__auto__
      data1241]
     (.init mac__546__auto__ k__547__auto__)
     (clojure.core/loop
      []
      (clojure.core/let
       [r__551__auto__
        (.read s__550__auto__ buf__549__auto__ 0 c__548__auto__)]
       (clojure.core/when-not
        (clojure.core/= r__551__auto__ -1)
        (.update mac__546__auto__ buf__549__auto__ 0 r__551__auto__)
        (recur))))
     (.doFinal mac__546__auto__)))
   java.io.File
   (compute-ripemd1601240
    [data1241 key1242]
    (clojure.core/with-open
     [data1241 (clojure.java.io/input-stream data1241)]
     (clojure.core/let
      [mac__546__auto__
       (javax.crypto.Mac/getInstance "Hmac-RipeMD160")
       k__547__auto__
       (javax.crypto.spec.SecretKeySpec. key1242 "Hmac-RipeMD160")
       c__548__auto__
       (clojure.core/int *buffer-size*)
       buf__549__auto__
       (clojure.core/byte-array c__548__auto__)
       s__550__auto__
       data1241]
      (.init mac__546__auto__ k__547__auto__)
      (clojure.core/loop
       []
       (clojure.core/let
        [r__551__auto__
         (.read s__550__auto__ buf__549__auto__ 0 c__548__auto__)]
        (clojure.core/when-not
         (clojure.core/= r__551__auto__ -1)
         (.update mac__546__auto__ buf__549__auto__ 0 r__551__auto__)
         (recur))))
      (.doFinal mac__546__auto__)))))
  'G__1243)
 (do
  (clojure.core/defn
   ripemd160-hmac*
   "[HMAC] Hmac-RipeMD160 (raw value)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (compute-ripemd1601240
    x
    (pandect.utils.convert/convert-to-byte-array secret)))
  (clojure.core/defn
   ripemd160-hmac-file*
   "[HMAC] Hmac-RipeMD160 (raw value)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (compute-ripemd1601240
     x
     (pandect.utils.convert/convert-to-byte-array secret))))
  (clojure.core/defn
   ripemd160-hmac-bytes
   "[HMAC] Hmac-RipeMD160 (value -> byte array)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (compute-ripemd1601240
    x
    (pandect.utils.convert/convert-to-byte-array secret)))
  (clojure.core/defn
   ripemd160-hmac-file-bytes
   "[HMAC] Hmac-RipeMD160 (file path -> byte array)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (compute-ripemd1601240
     x
     (pandect.utils.convert/convert-to-byte-array secret))))
  (clojure.core/defn
   ripemd160-hmac
   "[HMAC] Hmac-RipeMD160 (value -> string)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (pandect.utils.convert/bytes->hex
    (compute-ripemd1601240
     x
     (pandect.utils.convert/convert-to-byte-array secret))))
  (clojure.core/defn
   ripemd160-hmac-file
   "[HMAC] Hmac-RipeMD160 (file path -> string)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (pandect.utils.convert/bytes->hex
     (compute-ripemd1601240
      x
      (pandect.utils.convert/convert-to-byte-array secret)))))))