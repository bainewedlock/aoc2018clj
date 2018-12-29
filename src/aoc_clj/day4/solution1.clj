(ns aoc-clj.day4.solution1
  (:require [clojure.string :as str],
            [clojure.java.io :as io]))

(defn parse-line [line]
  (->> (re-find #"\[\S+ \S+:(\S+)] (\w+) (#(\d+))?" line)
       ((fn [[_ time type _ guard]]
          (into {} [ [ :time (Integer/parseInt time) ]
                    [ :guard-no guard ]
                    [ (keyword type) true]])))))

(defn join-down-guard-no [records]
  (->> records
       (reduce (fn [[records guard-no] record]
                 (if (:guard record)
                   [records (:guard-no record)]
                   [(conj records (assoc record :guard-no guard-no)) guard-no]))
               [[] nil])
       first))

(defn parse-records [records]
  (->> records
       (partition 2) 
       (map (fn [[a b]] { :guard-no (:guard-no a)
                         :from (:time a)
                         :to (:time b)
                         :duration (- (:time b) (:time a))}))))

(def best-guard (->> "day4.txt" io/resource slurp str/lower-case str/split-lines
                     sort
                     (map parse-line)
                     join-down-guard-no
                     parse-records
                     (group-by :guard-no) vals
                     (sort-by (fn [records] (reduce + (map :duration records)))) last))

(def best-guard-no (read-string (:guard-no (first best-guard))))

(def best-minute (->> best-guard
                      (mapcat #(range (% :from) (% :to)))
                      frequencies
                      (sort-by last) last first))

(str "Solution: " (* best-guard-no best-minute)
     " (" best-guard-no " * " best-minute  ")")
