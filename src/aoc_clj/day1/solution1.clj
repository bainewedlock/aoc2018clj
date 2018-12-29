(ns aoc-clj.solution1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(import '(java.io BufferedReader StringReader))

(->> "day1.txt"
     io/resource
     slurp
     (#(str/split % #"\r\n"))
     (map read-string)
     (reduce +)
     (str "Solution: "))
