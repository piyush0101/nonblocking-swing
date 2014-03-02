(ns nonblocking-swing.macro)

(import java.lang.Runnable)

(defmacro runnable
  [body]
  (proxy [Runnable] []
    (run []
         ~body)))
