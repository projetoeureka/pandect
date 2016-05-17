(ns
 pandect.algo.siphash
 "Siphash-2-4 algorithm implementation\n\n(requires `org.bouncycastle/bcprov-jdk15on` to be on the classpath)"
 (:require
  [pandect.buffer :refer [*buffer-size*]]
  [pandect.utils.convert]
  pandect.utils.bouncy-castle-provider))
(do
 (do
  (clojure.core/defprotocol
   G__1335
   (compute-siphash1332 [data1333 key1334]))
  (clojure.core/doseq
   [v__186__auto__ [#'G__1335 #'compute-siphash1332]]
   (clojure.core/alter-meta!
    v__186__auto__
    clojure.core/assoc
    :private
    true))
  (clojure.core/extend-protocol
   G__1335
   (clojure.core/class (clojure.core/byte-array 0))
   (compute-siphash1332
    [data1333 key1334]
    (clojure.core/let
     [mac__543__auto__
      (javax.crypto.Mac/getInstance "Siphash-2-4")
      msg__544__auto__
      (clojure.core/bytes data1333)
      k__545__auto__
      (javax.crypto.spec.SecretKeySpec. key1334 "Siphash-2-4")]
     (clojure.core/->
      (clojure.core/doto
       mac__543__auto__
       (.init k__545__auto__)
       (.update msg__544__auto__))
      (.doFinal))))
   java.lang.String
   (compute-siphash1332
    [data1333 key1334]
    (clojure.core/let
     [data1333 (.getBytes data1333 "UTF-8")]
     (clojure.core/let
      [mac__543__auto__
       (javax.crypto.Mac/getInstance "Siphash-2-4")
       msg__544__auto__
       (clojure.core/bytes data1333)
       k__545__auto__
       (javax.crypto.spec.SecretKeySpec. key1334 "Siphash-2-4")]
      (clojure.core/->
       (clojure.core/doto
        mac__543__auto__
        (.init k__545__auto__)
        (.update msg__544__auto__))
       (.doFinal))))))
  (clojure.core/extend-protocol
   G__1335
   java.io.InputStream
   (compute-siphash1332
    [data1333 key1334]
    (clojure.core/let
     [mac__546__auto__
      (javax.crypto.Mac/getInstance "Siphash-2-4")
      k__547__auto__
      (javax.crypto.spec.SecretKeySpec. key1334 "Siphash-2-4")
      c__548__auto__
      (clojure.core/int *buffer-size*)
      buf__549__auto__
      (clojure.core/byte-array c__548__auto__)
      s__550__auto__
      data1333]
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
   (compute-siphash1332
    [data1333 key1334]
    (clojure.core/with-open
     [data1333 (clojure.java.io/input-stream data1333)]
     (clojure.core/let
      [mac__546__auto__
       (javax.crypto.Mac/getInstance "Siphash-2-4")
       k__547__auto__
       (javax.crypto.spec.SecretKeySpec. key1334 "Siphash-2-4")
       c__548__auto__
       (clojure.core/int *buffer-size*)
       buf__549__auto__
       (clojure.core/byte-array c__548__auto__)
       s__550__auto__
       data1333]
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
  'G__1335)
 (do
  (clojure.core/defn
   siphash*
   "[HMAC] Siphash-2-4 (raw value)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (compute-siphash1332
    x
    (pandect.utils.convert/convert-to-byte-array secret)))
  (clojure.core/defn
   siphash-file*
   "[HMAC] Siphash-2-4 (raw value)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (compute-siphash1332
     x
     (pandect.utils.convert/convert-to-byte-array secret))))
  (clojure.core/defn
   siphash-bytes
   "[HMAC] Siphash-2-4 (value -> byte array)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (compute-siphash1332
    x
    (pandect.utils.convert/convert-to-byte-array secret)))
  (clojure.core/defn
   siphash-file-bytes
   "[HMAC] Siphash-2-4 (file path -> byte array)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (compute-siphash1332
     x
     (pandect.utils.convert/convert-to-byte-array secret))))
  (clojure.core/defn
   siphash
   "[HMAC] Siphash-2-4 (value -> string)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (pandect.utils.convert/bytes->hex
    (compute-siphash1332
     x
     (pandect.utils.convert/convert-to-byte-array secret))))
  (clojure.core/defn
   siphash-file
   "[HMAC] Siphash-2-4 (file path -> string)\n\n'secret' can be given as a byte array, string, java.io.File, java.io.InputStream\nor any value implementing `pandect.utils.convert/ByteConvertable`."
   [x secret]
   (clojure.core/with-open
    [x (clojure.java.io/input-stream (clojure.java.io/file x))]
    (pandect.utils.convert/bytes->hex
     (compute-siphash1332
      x
      (pandect.utils.convert/convert-to-byte-array secret)))))))
