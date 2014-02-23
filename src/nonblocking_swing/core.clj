(ns nonblocking-swing.core)

(import javax.swing.JFrame)
(import javax.swing.JButton)
(import java.awt.GridLayout)
(import '(java.awt.event ActionListener ActionEvent))
(import '(java.lang Runnable Thread))
(import javax.swing.SwingUtilities)

(def frame (JFrame. "Frame"))

(defn create-runnable
  []
  (proxy [Runnable] []
    (run []
         (Thread/sleep 5000)
         (println "i am a long running task in a different thread"))))

(defn create-long-running-task-button
  []
  (let [button (JButton. "LongRunningTask")]
    (.addActionListener
       button
       (proxy [ActionListener] []
         (actionPerformed [event]
                          (.start (Thread. (create-runnable))))))
    button))

(defn add-buttons
  []
  (let [layout (GridLayout. 3 1)]
    (.setLayout frame layout)
    (.add frame (create-long-running-task-button))
    (.add frame (JButton. "Always Clickable"))))

(defn create-frame
  []
  (.setSize frame 300 300)
  (.setVisible frame true)
  (add-buttons))


(create-frame)


