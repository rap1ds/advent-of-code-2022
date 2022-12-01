(ns day1
  (:require [util]))

(defn parse [lines]
  (->> lines
       (partition-by empty?)
       (remove (comp empty? first))
       (map #(map parse-long %))))

(comment

  ;; Day 1 example
  (->> (util/read-lines "example/day1.txt")
       parse
       (map #(reduce + %))
       (apply max)
       )


  ;; Day 1
  (->> (util/read-lines "input/day1.txt")
       parse
       (map #(reduce + %))
       (apply max)
       )


  ;; Day 2 example
  (->> (util/read-lines "example/day1.txt")
       parse
       (map #(reduce + %))
       sort
       reverse
       (take 3)
       (reduce +)
       )


  ;; Day 2 input
  (->> (util/read-lines "input/day1.txt")
       parse
       (map #(reduce + %))
       sort
       reverse
       (take 3)
       (reduce +)
       )

  )
