(ns nonblocking-swing.macro)

(import java.lang.Runnable)
(import java.lang.Thread)

(defmacro runnable
  [body & args]
  (if (= (first body) 'fn)
    `(proxy [Runnable] []
       (run []
            (apply ~body '~args)))
    `(proxy [Runnable] []
       (run []
            ~body))))

