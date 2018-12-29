(ns aoc-clj.solution1b
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn search
  ([to-check] (search to-check #{} 0))
  ([to-check seen freq]
   (let [new-value (+ freq (first to-check))]
     (if (seen new-value)
       new-value
       (recur (rest to-check) (conj seen new-value) new-value)))))

(->> "day1.txt"
     io/resource
     slurp
     (#(str/split % #"\r\n"))
     (map read-string)
     cycle
     search
     (str "Solution: "))
