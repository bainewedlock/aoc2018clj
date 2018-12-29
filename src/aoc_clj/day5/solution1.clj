(ns aoc-clj.day5.solution1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn collide? [x y]
  (and (= (str/upper-case x) (str/upper-case y))
       (not= x y)))

(defn push-unit [stack unit]
  (cond
    (empty? stack)
    (list unit)

    (collide? unit (first stack))
    (rest stack)

    :else
    (conj stack unit)))

(->> "day5.txt"
     io/resource
     slurp
     str/trim
     (reduce push-unit '())
     count)
