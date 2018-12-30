(ns aoc-clj.day7.solution1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn parse-line [line]
  (rest (re-matches #"Step (.) must be finished before step (.) can begin." line)))

(defn push-dependency [requirements [left-step right-step]]
  (update
   (update requirements left-step #(or % #{}))
   right-step #(conj (or % #{}) left-step)))

(defn choose-next [requirements]
  (->> requirements
       (filter (fn [[left right]] (empty? right)))
       (map first)
       sort
       first))

(defn remove-all [[remaining-requirements to-remove] [next-key next-vals]]
  (if (= next-key to-remove)
    [remaining-requirements to-remove]
    [(conj remaining-requirements
           [next-key (remove #(= to-remove %) next-vals)]) to-remove]))

(defn solve [order requirements]
  (if (empty? requirements)
      order
      (let [chosen (choose-next requirements)]
        (recur (conj order chosen)
               (first (reduce remove-all [{} chosen] requirements))))))

(->> "day7.txt"
     io/resource
     slurp
     str/split-lines
     (map parse-line)
     (reduce push-dependency {})
     (solve [])
     (apply str)
     (str "Solution: "))
