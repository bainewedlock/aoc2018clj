(ns aoc-clj.day3.solution1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn fill-rect [id x y w h]
  (for [ix (range x (+ x w))
        iy (range y (+ y h))]
    [ix iy]))

(defn parse-line [s]
  (->> s
       (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)")
       (drop 1)
       (map read-string)
       (apply fill-rect)))

(->> "day3.txt"
     io/resource
     slurp
     str/split-lines
     (mapcat parse-line)
     (group-by identity)
     (map last)
     (map count)
     (map dec)
     (filter (complement zero?))
     count)


