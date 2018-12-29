(ns aoc-clj.day3.solution1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn fill-rect [id x y w h]
  (for [ix (range x (+ x w))
        iy (range y (+ y h))]
    [[ix iy] id]))

(defn parse-line [s]
  (->> s
       (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)")
       (drop 1)
       (map read-string)
       (apply fill-rect)))

(def squares (->> "day3.txt"
      io/resource
      slurp
      str/split-lines
      (mapcat parse-line)))

(def single-squares (->> squares
                         (map first)
                         (group-by identity)
                         (map last)
                         (filter foo)
                         (map first)
                         (into #{})))

(defn all-single? [s]
  (->> (last s)
       (map last)
       (every? single-squares)))

(->> (map reverse squares)
     (group-by first)
     (filter all-single?)
     first
     first
     (str "Solution: "))
