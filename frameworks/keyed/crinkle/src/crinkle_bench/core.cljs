(ns crinkle-bench.core
  (:require
   ["react-dom" :refer [render]]
   ["react" :as react]
   [crinkle-bench.utils :as u]
   [crinkle.dom :as d]))

(def start-time (atom nil))
(def last-measure (atom nil))

(defn start-measure [name]
  (reset! start-time (.now js/performance))
  (reset! last-measure name))

(defn stop-measure []
  (if-let [last @last-measure]
    (.setTimeout js/window
                 (fn []
                   (reset! last-measure nil)
                   (let [stop (.now js/performance)]
                     (.log js/console (str last " took " (- stop @start-time)))))
                 0)))

(defn row
  [data selected? on-click on-delete]
  (d/tr {:class (when selected? "danger")}
        (d/td {:className "col-md-1"})
        (d/td {:className "col-md-4"}
              (d/a {:onClick (fn [e] (on-click (:id data)))}
                   (:label data)))
        (d/td {:className "col-md-1"}
              (d/a {:onClick (fn [e] (on-delete (:id data)))}
                   (comment "TODO Is this the correct syntax for multiple classes?")
                   (d/span {:className "glyphicon.glyphicon-remove"
                            :aria-hidden "true"})))
        (d/td {:className "col-md-6"})))

(enable-console-print!)

(defn start []
  (render
   (d/h1 {} "HELLO WORLD")
   (.. js/document (getElementById "app"))))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (prn "starting up!")
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
