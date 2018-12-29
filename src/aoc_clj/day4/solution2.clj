(ns aoc-clj.day4.solution2
  (:require [clojure.string :as str],
            [clojure.java.io :as io]))

(defn number-after [token text]
  (->> text
       (re-find (re-pattern (str token "(\\d+)"))) second
       Integer/parseInt))

(defn parse-event [line]
  (cond (str/includes? line "Guard") [:guard (number-after "#" line)]
        (str/includes? line "falls") [:sleep (number-after ":" line)]
        (str/includes? line "wakes") [:wake  (number-after ":" line)]))

(defn handle-event [{:keys [active-guard sleep-start] :as state}
                    [event-type event-arg]]
  (case event-type
    :guard (assoc state :active-guard event-arg)
    :sleep (assoc state :sleep-start event-arg)
    :wake (update-in state [:sleep-data active-guard]
                     (fn [minutes] (concat (or minutes [])
                                           (range sleep-start event-arg))))))

(defn keep-only-most-minutes [[guard minutes]]
  [guard (apply max-key last (frequencies minutes))])

(->> "day4.txt" io/resource slurp str/split-lines
     sort
     (map parse-event)
     (reduce handle-event {})
     (:sleep-data)
     (map keep-only-most-minutes)
     (apply max-key (comp last last))
     ((fn [[guard [minute count]]] (* guard minute)))
     (str "Solution: "))
