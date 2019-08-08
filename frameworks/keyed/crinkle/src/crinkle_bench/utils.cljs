(ns crinkle-bench.utils)

(def adjectives ["pretty", "large", "big", "small", "tall", "short", "long", "handsome", "plain", "quaint", "clean", "elegant", "easy", "angry", "crazy", "helpful", "mushy", "odd", "unsightly", "adorable", "important", "inexpensive", "cheap", "expensive", "fancy"])
(def colours ["red", "yellow", "blue", "green", "pink", "brown", "purple", "brown", "white", "black", "orange"])
(def nouns ["table", "chair", "house", "bbq", "desk", "car", "pony", "cookie", "sandwich", "burger", "pizza", "mouse", "keyboard"])

(defrecord Data [id label])

(defn build-label
  []
  (str (rand-nth adjectives)
       " "
       (rand-nth colours)
       " "
       (rand-nth nouns)))

(defn build-data [{:keys [id] :as state} {:keys [count]}]
  (let [last-id (+ id count)
        new-data (for [next-id (range id last-id)]
                   (->Data next-id (build-label)))]
    (assoc state
           :id last-id
           :data new-data)))
(defn run
  [{:keys [id] :as state} {:keys [count] :as args}]
  (-> state
      (build-data args)
      (assoc :selected nil)))

(defn add [data id-atom]
  (into data (build-data id-atom 1000)))

(defn update-some [data]
  (reduce (fn [data index]
            (let [row (get data index)]
              (assoc data index (assoc row :label (str (:label row) " !!!")))))
          data
          (range 0 (count data) 10)))

(defn swap-rows [data]
  (if (> (count data) 998)
    (-> data
        (assoc 1 (get data 998))
        (assoc 998 (get data 1)))
    data))

(defn delete-row [data id]
  (vec (remove #(identical? id (:id %)) data)))

(def initial-state
  {:id 0
   :selected nil
   :data []})

(defn reducer [state {:keys [action args] :as arg}]
  (let [new-state
        (case action
          :run (run state args))]
          
    ;;Printing for debugging purposes, this should can be refactored out.
    (println {:arg arg
              :old-state state
              :new-state new-state})
    new-state))
