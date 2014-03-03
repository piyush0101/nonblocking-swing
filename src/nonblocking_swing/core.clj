(ns nonblocking-swing.core
  (:require [nonblocking-swing.button :as button]
            [nonblocking-swing.runnables :as runnables]
            [nonblocking-swing.components :as component]))

(import '(javax.swing JFrame JButton JTextField SwingUtilities JPanel ImageIcon))
(import '(java.awt GridLayout Color Dimension))
(import '(java.awt.event ActionListener ActionEvent))
(import '(java.lang Runnable Thread))

(component/create
 (let [frame (JFrame. "MainFrame")]
   (.setSize frame 300 300)
   {:main-frame frame}))

(component/create (let [image
                        (.getImage
                         (ImageIcon. "images/clojure.jpg"))]
                    {:image image}))

(component/create {:long-running-task-button
                   (button/create-async
                    {:title "LongRunningTask"
                     :listener (fn []
                                 (Thread/sleep 5000)
                                 (let [main-frame (component/by-id :main-frame)
                                       image (component/by-id :image)
                                       image-panel (proxy [JPanel] []
                                                     (paintComponent
                                                      [graphic]
                                                      (.drawImage graphic image 0 0 nil)))]
                                   (component/update
                                    (doto main-frame
                                      (.setSize (Dimension. (.getWidth image nil) (.getHeight image nil)))
                                      (.add image-panel)))))})})

(component/create {:always-clickable
                   (JButton. "AlwaysClickable")})

(component/create {:text-field (JTextField. "Hello World")})

(defn create-ui
  "Creates an interface from components"
  []
  (doto (component/by-id :main-frame)
    (.setLayout (GridLayout. 3 1))
    (.add (component/by-id :long-running-task-button))
    (.add (component/by-id :always-clickable))
    (.setVisible true)))

(create-ui)


