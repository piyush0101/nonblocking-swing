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
   (.setSize frame 500 500)
   {:main-frame frame}))

(defn create-image-panel
  [image-id]
  (fn []
    (Thread/sleep 5000)
    (let [main-frame (component/by-id :main-frame)
          image (component/by-id image-id)
          image-panel (proxy [JPanel] []
                        (paintComponent
                         [graphic]
                         (.drawImage graphic image 0 0 nil)))]
      (component/update
       (fn []
         (.setPreferredSize image-panel (Dimension. 400 100))
         (doto main-frame (.add image-panel) (.pack)))))))

(defn create-image
  [id path]
  (let [image
        (.getImage
         (ImageIcon. path))]
    (component/create {id image})))

(create-image :image "images/clojure.jpg")
(create-image :image-grayscale "images/clojure-grayscale.png")

(component/create {:load-colored-image
                   (button/create-async
                    {:title "Load Colored Image"
                     :listener (create-image-panel :image)})})

(component/create {:load-grayscale-image
                   (button/create-async
                    {:title "Load Grayscale Image"
                     :listener (create-image-panel :image-grayscale)})})

(defn create-ui
  "Creates an interface from components"
  []
  (doto (component/by-id :main-frame)
    (.setLayout (GridLayout. 4 1))
    (.add (component/by-id :load-colored-image))
    (.add (component/by-id :load-grayscale-image))
    (.setVisible true)))

(create-ui)

