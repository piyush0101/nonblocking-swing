(ns nonblocking-swing.runnables)

(defn create-runnable
  [f]
  (proxy [Runnable] []
    (run []
         (f))))
