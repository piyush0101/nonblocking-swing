(ns nonblocking-swing.components
  (:require [nonblocking-swing.runnables :as runnables]))

(import '(javax.swing JComponent JButton SwingUtilities))

(def components (atom {}))

(defn create
  [component]
  (swap! components merge component))

(defn by-id
  [id]
  (id @components))

(defn update
  [body]
  (SwingUtilities/invokeLater
   (runnables/create-runnable body)))


