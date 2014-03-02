(ns nonblocking-swing.core
  (:require [nonblocking-swing.button :as button]
            [nonblocking-swing.runnables :as runnables]
            [nonblocking-swing.components :as component]))

(import '(javax.swing JFrame JButton JTextField SwingUtilities))
(import java.awt.GridLayout)
(import '(java.awt.event ActionListener ActionEvent))
(import '(java.lang Runnable Thread))

(component/create
 {:main-frame (JFrame. "Main Frame")})

(component/create
 {:long-running-task-button
   (button/create-async
            {:title "LongRunningTask"
             :listener (fn []
                         (Thread/sleep 5000)
                         (println "I am a long running task in a different thread")
                         (component/update
                          (.setText (component/by-id :text-field) "FromButton")))})})

(component/create
 {:always-clickable
 (JButton. "AlwaysClickable")})

(component/create
 {:text-field (JTextField. "Hello World")})

(defn create-ui
  "Creates an interface from components"
  []
  (let [frame (component/by-id :main-frame)]
    (.setLayout frame (GridLayout. 3 1))
    (.add frame (component/by-id :long-running-task-button))
    (.add frame (component/by-id :always-clickable))
    (.add frame (component/by-id :text-field))
    (.setSize frame 300 300)
    (.setVisible frame true)))

(create-ui)


