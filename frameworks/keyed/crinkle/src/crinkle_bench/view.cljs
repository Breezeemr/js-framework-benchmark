(ns crinkle-bench.view
  (:require
   ["react" :as react]
   [crinkle-bench.utils :as u]
   [crinkle.component :refer [CE]]
   [crinkle.dom :as d]))

(defn row
  [{:keys [data selected? on-click on-delete]}]
  (d/tr {:className (when selected? "danger")}
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

(defn app
  []
  (let [[app-db dispatch] (react/useReducer u/reducer u/initial-state)
        db {:app-db app-db :dispatch dispatch}]
    (d/div {:className "container"}
           (d/div {:className "jumbotron"}
                  (d/div {:className "row"}
                         (d/div {:className "col-md-6"}
                                (d/h1 {} "Reagent"))
                         (d/div {:className "col-md-6"}
                                (d/div {:className "row"}
                                       (d/div {:className "col-sm-6.smallpad"}
                                              (d/button {:className "button btn btn-primary btn-block"
                                                         :type "button"
                                                         :id "run"
                                                         :onClick #(dispatch {:action :run :args {:count 5}})}
                                                        "Create 1,000 rows"))))))
                                              
           (d/table {:className "table table-hover table-striped test-data"}
                    (d/tbody {}
                             (let [selected (:selected app-db)]
                               (for [{:keys [id] :as d} (:data app-db)]
                                 (CE row {:data d
                                          :selected? "TODO"
                                          :on-click "TODO"
                                          :on-delete "TODO"}
                                     :key id))))))))
                                 

