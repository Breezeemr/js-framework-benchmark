(ns crinkle-bench.view
  (:require
   ["react" :as react]
   [crinkle-bench.utils :as u]
   [crinkle.component :refer [CE] :as c]
   [crinkle.dom :as d]))

(defn row
  [{:keys [data selected? app-db dispatch]}]
  (d/tr {:className (when selected? "danger")}
        (d/td {:className "col-md-1"})
        (d/td {:className "col-md-4"}
              (d/a {:onClick #(dispatch {:action :select :args {:id (:id data)}})}
                   (:label data)))
        (d/td {:className "col-md-1"}
              (d/a {:onClick #(dispatch {:action :remove :args {:id (:id data)}})}
                   (d/span {:className "glyphicon glyphicon-remove"
                            :aria-hidden "true"})))
        (d/td {:className "col-md-6"})))

;;TODO needs div
(defn button
  [{:keys [id on-click title]}]
  (d/div {:className "col-sm-6 smallpad"}
         (d/button {:className "btn btn-primary btn-block"
                    :type "button"
                    :id id
                    :onClick on-click}
                   title)))

(defn jumbotron
  [{:keys [app-db dispatch]}]
  (d/div {:className "jumbotron"}
         (d/div {:className "row"}
                (d/div {:className "col-md-6"}
                       (d/h1 {} "Crinkle"))
                (d/div {:className "col-md-6"}
                       (d/div {:className "row"}
                              (CE button {:id "run"
                                          :on-click #(dispatch {:action :run})
                                          :title "Create 1,000 rows"})
                              (CE button {:id "runlots"
                                          :on-click #(dispatch {:action :run-lots})
                                          :title "Create 10,000 rows"})
                              (CE button {:id "add"
                                          :title "Append 1,000 rows"
                                          :on-click #(dispatch {:action :add})})
                              (CE button {:id "update"
                                          :title "Update every 10th row"
                                          :on-click #(dispatch :action :update)})
                              (CE button {:id "clear"
                                          :title "Clear"
                                          :on-click #(dispatch {:action :clear})})
                              (CE button {:id "swaprows"
                                          :title "Swap Rows"
                                          :on-click #(dispatch {:action :swap-rows})}))))))

(defn app
  []
  (let [[app-db dispatch] (react/useReducer u/reducer u/initial-state)
        db {:app-db app-db :dispatch dispatch}]
    (d/div {:className "container"}
           (CE jumbotron db)
           (d/table {:className "table table-hover table-striped test-data"}
                    (d/tbody {}
                             (map #(CE row
                                       (assoc db
                                              :data %
                                              :selected? (= (:id %) (:selected app-db)))
                                       :key (:id %))
                                  (:data app-db))))
           (d/span {:className "preloadicon glyphicon glyphicon-remove"
                    :aria-hidden "true"}))))
