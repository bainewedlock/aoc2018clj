(ns aoc-clj.day6.solution2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-line [line]
  (map read-string (str/split line #", ")))

(def points (->> "day6.txt"
                 io/resource
                 slurp
                 str/split-lines
                 (map parse-line)))

(defn find-limits [numbers]
  [(apply min numbers) (inc (apply max numbers))])

(defn make-range [numbers]
  (apply range (find-limits numbers)))

(def x-range (->> points (map first) make-range))
(def y-range (->> points (map last) make-range))

(defn linear-distance [a b] (max (- a b) (- b a)))

(defn manhattan-distance [point-a point-b]
  (apply + (map linear-distance point-a point-b)))

(defn add-distance [[location total-distance] new-point]
  [location (+ total-distance
               (manhattan-distance new-point location))])

(def max-total-distance 10000)

(defn push-location [area new-location]
  (if (> max-total-distance (last (reduce add-distance [new-location 0] points)))
    (inc area)
    area))

(->> (for [y y-range x x-range] [x y])
     (reduce push-location 0)
     (str "Solution: "))
