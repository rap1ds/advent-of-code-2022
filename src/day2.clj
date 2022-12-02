(ns day2
  (:require [util]))

(defn parse [lines]
  (map (fn [[a _ c]] [a c]) lines))

(def opponent-char->shape
  {\A :rock
   \B :paper
   \C :scissors})

(def own-char->shape
  {\X :rock
   \Y :paper
   \Z :scissors})

(def shape->score
  {:rock 1
   :paper 2
   :scissors 3})

(def outcome->score
  {:lost 0
   :draw 3
   :won 6})

(def outcome
  {[:rock :rock] :draw
   [:paper :paper] :draw
   [:scissors :scissors] :draw
   [:rock :paper] :won
   [:rock :scissors] :lost
   [:paper :rock] :lost
   [:paper :scissors] :won
   [:scissors :rock] :won
   [:scissors :paper] :lost})

(def char->outcome
  {\X :lost
   \Y :draw
   \Z :won})

(defn round [[opp own]]
  (+ (shape->score own)
     (outcome->score (outcome [opp own]))))

(defn shapes [[opp result]]
  (let [round-outcome (first (filter #(let [s (first %)
                                            r (second %)]
                                        (and (= (first s) opp)
                                             (= result r)))  outcome))]
    [opp (second (first round-outcome))]))

(defn part1 [lines]
  (->> lines
       parse
       (map (fn [[opp own]] [(opponent-char->shape opp)
                             (own-char->shape own)]))
       (map round)
       (reduce +)))

(defn part2 [lines]
  (->> lines
       parse
       (map (fn [[opp outcome-char]] [(opponent-char->shape opp)
                                      (char->outcome outcome-char)]))
       (map shapes)
       (map round)
       (reduce +)))

(comment
  (->> (util/read-lines "example/day2.txt")
       part1) ;; #=> 15

  (->> (util/read-lines "input/day2.txt")
       part1)

  (->> (util/read-lines "example/day2.txt")
       part2) ;; #=> 12

  (->> (util/read-lines "input/day2.txt")
       part2)

  )
