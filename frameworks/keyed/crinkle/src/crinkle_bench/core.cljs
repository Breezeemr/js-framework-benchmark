(ns crinkle-bench.core
  (:require
   ["react-dom" :refer [render]]
   ["react" :as react]
   [crinkle-bench.utils :as u]
   [crinkle-bench.view :as view]
   [crinkle.component :refer [CE]]
   [crinkle.dom :as d]))

(defn start []
  (render
   (CE view/app {} )
   (.. js/document (getElementById "main"))))

(defn ^:export init []
  (start))
