(ns aoc-clj.day2.solution2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def words (->> "day2.txt"
                io/resource
                slurp
                (#(str/split % #"\r\n"))))

(defn almost-match
  ([a b] (->> (map = a b)
              (filter false?)
              count
              (= 1)))
  ([pair] almost-match (first pair) (last pair)))

(defn not-different [a b]
  (if (= a b) a))

(defn remove-different [a b]
  (->> (map not-different a b)
       (filter identity)
       (apply str)))

(->> (for [a words b words] [a b])
     (filter almost-match)
     first
     (apply remove-different)
     (str "Solution: "))
