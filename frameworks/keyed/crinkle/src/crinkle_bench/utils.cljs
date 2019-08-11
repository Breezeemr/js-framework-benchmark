(ns crinkle-bench.utils)

(def adjectives ["pretty", "large", "big", "small", "tall", "short", "long", "handsome", "plain", "quaint", "clean", "elegant", "easy", "angry", "crazy", "helpful", "mushy", "odd", "unsightly", "adorable", "important", "inexpensive", "cheap", "expensive", "fancy"])
(def colours ["red", "yellow", "blue", "green", "pink", "brown", "purple", "brown", "white", "black", "orange"])
(def nouns ["table", "chair", "house", "bbq", "desk", "car", "pony", "cookie", "sandwich", "burger", "pizza", "mouse", "keyboard"])

(defrecord Data [id label])

(def next-id (atom 0))

(def initial-state
  {:selected-atom (atom nil)
   :data []})

(defn build-label
  []
  (str (rand-nth adjectives)
       " "
       (rand-nth colours)
       " "
       (rand-nth nouns)))

(defn build-data
  [count]
  (loop [i count
         data (transient [])]
    (if (pos? i)
      (recur (dec i) (conj! data (->Data (swap! next-id inc)
                                         (build-label))))
      (persistent! data))))

(defn run
  [state]
  (assoc state
         :data (build-data 1000)))

(defn add
  [state]
  (update state :data into (build-data 1000)))

(defn update-some
  [{:keys [data] :as state}]
  (assoc state :data
         (reduce (fn [data index]
                   (let [row (get data index)]
                     (assoc  data index (assoc row :label (str (:label row) " !!!")))))
                 data  
                 (range 0 (count data) 10))))

(defn swap-rows
  [{:keys [data] :as state}]
  (assoc state :data (if (> (count data) 998)
                       (let [tdata (transient data)]
                         (-> tdata
                           (assoc! 1 (get data 998))
                           (assoc! 998 (get data 1))
                           persistent!))
                       data)))

(defn delete-row
  [{:keys [data] :as state} {:keys [id]}]
  (assoc state
         :data (into [] (remove #(identical? id (:id %)) data))))

(defn run-lots
  [state]
  (assoc state
         :data (build-data 10000)))

(defn clear
  [state]
  (assoc state
         :data []))

(defn reducer [state {:keys [action args] :as arg}]
  (case action
    :run (run state)
    :run-lots (run-lots state)
    :add (add state)
    :update (update-some state)
    :clear (clear state)
    :swap-rows (swap-rows state)
    :remove (delete-row state args)))
