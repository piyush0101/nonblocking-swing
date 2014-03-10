(ns nonblocking-swing.button
  (:require [nonblocking-swing.runnables :as runnables]))

(import javax.swing.JButton)
(import java.awt.event.ActionListener)

(defn create-async
  [args]
  (let [button (JButton. (args :title))]
    (let [listener
          (proxy [ActionListener] []
           (actionPerformed [event]
                        (.start
                         (Thread.
                          (proxy [Runnable] []
                                    (run []
                                         (println (args :listener))
                                         (apply (args :listener) [])))))))]
    (.addActionListener button listener)
      button)))
