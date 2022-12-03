(ns day3
  (:require [util]
            [clojure.set :as set]))

(defn parse [lines]
  (map seq lines))

(defn split-half [xs]
  (split-at (/ (count xs) 2) xs))

(def all-chars
  (map identity (str
                 "abcdefghijklmnopqrstuvwxyz"
                 "ABCDEFGHIJKLMNOPQRSTUVWXYZ")))

(def prio
  (->> all-chars
       (map (fn [p c] [c (inc p)]) (range))
       (into {})))

(defn part1 [chars]
  (->> chars
       (map #(->> %
                  split-half
                  (map set)
                  (apply set/intersection)
                  (map prio)
                  (reduce +)))
       (reduce +)))

(defn part2 [chars]
  (->> chars
       (partition 3)
       (map #(map set %))
       (map #(apply set/intersection %))
       (map #(map prio %))
       (map #(reduce + %))
       (reduce +)))

(comment
  (part1 (parse (util/read-lines "example/day3.txt"))) ;; 157

  (part1 (parse (util/read-lines "input/day3.txt")))

  (part2 (parse (util/read-lines "example/day3.txt"))) ;; 70

  (part2 (parse (util/read-lines "input/day3.txt")))

  
  )
