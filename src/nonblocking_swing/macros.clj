(ns nonblocking-swing.macro)

(import java.lang.Runnable)
(import java.lang.Thread)

(defmacro runnable
  [body]
  `(proxy [Runnable] []
    (run []
         ~body)))
