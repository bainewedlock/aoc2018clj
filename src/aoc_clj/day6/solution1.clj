(ns aoc-clj.day6.solution1
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

(defn nearest-to [[location best-distance best-points] point]
  (let [distance (manhattan-distance point location)]
    (cond
      (or (nil? best-distance) (< distance best-distance))
      [location distance [point]]

      (<= distance best-distance)
      [location distance (conj best-points point)]

      :else
      [location best-distance best-points])))

(defn at-border? [[x y]]
  (or (= (first x-range) x)
      (= (first y-range) y)
      (= (last x-range) x)
      (= (last y-range) y)))

(defn push-location [areas new-location]
  (let [nearest (last (reduce nearest-to [new-location] points))]
    (if (at-border? new-location)
      (update-in areas [(first nearest) :infinite] #(or % true))
      (if (= (count nearest) 1)
        (update-in areas [(first nearest) :size] #(inc (or % 0)))
        areas))))

(->> (for [y y-range x x-range] [x y])
     (reduce push-location {})
     (vals)
     (filter (complement :infinite))
     (map :size)
     (apply max)
     (str "Solution: "))
