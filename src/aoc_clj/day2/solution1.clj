(ns aoc-clj.day2.solution1
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def words (->> "day2.txt"
                io/resource
                slurp
                (#(str/split % #"\r\n"))))

(defn count-matches? [n string] (->> string
                                     (group-by identity)
                                     (map last)
                                     (some #(= n (count %)))))

(defn count-all [n]
  (->> words
       (filter (partial count-matches? n))
       count))

(->> [2 3]
     (map count-all)
     (apply *)
     (str "Solution: "))
