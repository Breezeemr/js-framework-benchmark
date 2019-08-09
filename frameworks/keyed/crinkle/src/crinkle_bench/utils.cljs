(ns crinkle-bench.utils)

(def adjectives ["pretty", "large", "big", "small", "tall", "short", "long", "handsome", "plain", "quaint", "clean", "elegant", "easy", "angry", "crazy", "helpful", "mushy", "odd", "unsightly", "adorable", "important", "inexpensive", "cheap", "expensive", "fancy"])
(def colours ["red", "yellow", "blue", "green", "pink", "brown", "purple", "brown", "white", "black", "orange"])
(def nouns ["table", "chair", "house", "bbq", "desk", "car", "pony", "cookie", "sandwich", "burger", "pizza", "mouse", "keyboard"])

(defrecord Data [id label])

(def next-id (atom 0))

(def initial-state
  {:selected nil
   :data []})

(defn build-label
  []
  (str (rand-nth adjectives)
       " "
       (rand-nth colours)
       " "
       (rand-nth nouns)))

(defn build-data [count]
  (repeatedly count #(->Data (swap! next-id inc)
                             (build-label))))

(defn run
  [state]
  (assoc state
         :data (build-data 10)
         :selected nil))

(defn add [state]
  (update state :data into (build-data 1)))

(defn update-some [{:keys [data] :as state}]
  (assoc state :data
         (reduce (fn [data index]
                   (let [row (get data index)]
                     (assoc  data index (assoc row :label (str (:label row) " !!!")))))
                 ;; TODO: consider why we have to run vec here.
                 ;; The immediate need is because you can't assoc into lazy lists
                 (vec data)  
                 ;; mocked number
                 (range 0 (count data) 3))))

(defn swap-rows [data]
  (if (> (count data) 998)
    (-> data
        (assoc 1 (get data 998))
        (assoc 998 (get data 1)))
    data))

(defn delete-row [data id]
  (vec (remove #(identical? id (:id %)) data)))

(defn run-lots
  [state]
  (assoc state
         :data (build-data 100)
         :selected nil))

(defn reducer [state {:keys [action args] :as arg}]
  (let [new-state
        (case action
          :run (run state)
          :run-lots (run-lots state)
          :add (add state)
          :update (update-some state))]
    ;;Printing for debugging purposes, this should can be refactored out.
    (println {:arg arg
              :old-state state
              :new-state new-state})
    new-state))
