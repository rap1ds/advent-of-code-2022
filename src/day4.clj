(ns day4
  (:require util
            [clojure.set :as set]))

(defn parse [lines]
  (map #(->> (re-matches #"(\d+)-(\d+),(\d+)-(\d+)" %)
             (map parse-long)
             (drop 1)
             (partition 2))
       lines))

(defn range-incl [a b]
  (range a (inc b)))

(comment
  ;; part1 example
  (->> (parse (util/read-lines "example/day4.txt"))
       (map #(map (fn [[a b]] (set (range-incl a b))) %))
       (map (fn [[a b]]
              (or (set/superset? a b)
                  (set/superset? b a))))
       (filter true?)
       count)

  ;; part1
  (->> (parse (util/read-lines "input/day4.txt"))
       (map #(map (fn [[a b]] (set (range-incl a b))) %))
       (map (fn [[a b]]
              (or (set/superset? a b)
                  (set/superset? b a))))
       (filter true?)
       count)

  ;; part2 example
  (->> (parse (util/read-lines "example/day4.txt"))
       (map #(map (fn [[a b]] (set (range-incl a b))) %))
       (map (fn [[a b]]
              (seq (set/intersection a b))))
       (filter some?)
       count)

  ;; part2
  (->> (parse (util/read-lines "input/day4.txt"))
       (map #(map (fn [[a b]] (set (range-incl a b))) %))
       (map (fn [[a b]]
              (seq (set/intersection a b))))
       (filter some?)
       count)

  )
