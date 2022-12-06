(ns day6
  (:require util)
  )

(defn solve [input size]
  (->> input
       (partition size 1)
       (map set)
       (map count)
       (map-indexed (fn [idx item] [idx item]))
       (filter (fn [[_ c]] (= c size)))
       first
       first
       (+ size)))

(defn part1 [input]
  (solve input 4))

(defn part2 [input]
  (solve input 14))

(comment
  ;; example 1
  (part1 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")

  (part1 "bvwbjplbgvbhsrlpgdmjqwftvncz")

  (part1 "nppdvjthqldpwncqszvftbrmjlhg")

  (part1 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")

  (part1 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")

  (part1 (util/read "input/day6.txt"))

  (part2 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")

  (part2 "bvwbjplbgvbhsrlpgdmjqwftvncz")

  (part2 "nppdvjthqldpwncqszvftbrmjlhg")

  (part2 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")

  (part2 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")

  (part2 (util/read "input/day6.txt"))

  )
