(ns nonblocking-swing.macro)

(import java.lang.Runnable)
(import java.lang.Thread)

(defmacro runnable
  [body]
  `(proxy [Runnable] []
    (run []
         ~body)))

(do
  (println "Hello World")
  (println "Hi How are you"))

(.start (Thread.
        (runnable
         ((println "Hello")
          (println "World")))))
