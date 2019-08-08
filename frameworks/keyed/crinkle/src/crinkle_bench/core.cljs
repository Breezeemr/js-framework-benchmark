(ns crinkle-bench.core
  (:require
   ["react-dom" :refer [render]]
   [crinkle-bench.view :as view]
   [crinkle.component :refer [CE]]))

(defn start []
  (render
   (CE view/app {} )
   (.. js/document (getElementById "main"))))

(defn ^:export init []
  (start))
