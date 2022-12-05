(ns day5
  (:require util)
  )

(defn to-stacks [stack-strs]
  (mapv #(apply list %) stack-strs))

;; I'm lazy, skip parsing the starting stack positionw
(def example-starting-stacks
  (to-stacks
   ["NZ"
    "DCM"
    "P"]))

(def input-starting-stacks
  (to-stacks
   ["NTBSQHGR"
    "JZPDFSH"
    "VHZ"
    "HGFJZM"
    "RSMLDCZT"
    "JZHVWTM"
    "ZLPFT"
    "SWVQ"
    "CNDTMLHW"
    ]))

(defn parse [lines]
  (map #(->> (re-matches #"move (\d+) from (\d+) to (\d+)" %)
             (drop 1)
             (map parse-long))
       lines))

(defn move-one [stacks from to]
  (let [crane (peek (get stacks (dec from)))]
    (-> stacks
        (update (dec from) pop)
        (update (dec to) conj crane))))

(defn make-a-move [stacks move]
  (let [[n from to] move]
    (last (take (inc n) (iterate #(move-one % from to) stacks)))))

(defn make-a-move-2 [stacks move]
  (let [[n from to] move]
    (-> stacks
        (make-a-move [n from (count stacks)])
        (make-a-move [n (count stacks) to]))))

(comment
  ;; example 1
  (let [stacks example-starting-stacks
        moves (->> (util/read-lines "example/day5.txt")
                   parse)]
    (->> moves
         (reduce make-a-move stacks)
         (map first)
         (apply str)))

  ;; input 1
  (let [stacks input-starting-stacks
        moves (->> (util/read-lines "input/day5.txt")
                   parse)]
    (->> moves
         (reduce make-a-move stacks)
         (map first)
         (apply str)))

  ;; example 2
  (let [stacks (conj example-starting-stacks '())
        moves (->> (util/read-lines "example/day5.txt")
                   parse)]
    (->> moves
         (reduce make-a-move-2 stacks)
         (map first)
         (apply str)))

  ;; input 2
  (let [stacks (conj input-starting-stacks '())
        moves (->> (util/read-lines "input/day5.txt")
                   parse)]
    (->> moves
         (reduce make-a-move-2 stacks)
         (map first)
         (apply str)))

  )
