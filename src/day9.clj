(ns day9
  (:require util))

(defn parse [lines]
  (map (fn [[dir _ & num]]
         [dir (parse-long (apply str num))]) lines))

(defn split-input [instructions]
  (mapcat (fn [[dir mult]]
            (repeat mult [dir 1])) instructions))

(defn point+ [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn point* [[x y] m]
  [(* x m) (* y m)])

(def dir-point
  {\R [1 0]
   \D [0 1]
   \L [-1 0]
   \U [0 -1]})

(defn new-head-pos [head-pos [dir mult]]
  (point+ head-pos (point* (dir-point dir) mult)))

(defn new-tail-pos [[xh yh] [xt yt]]
  (let [x-diff (- xh xt)
        y-diff (- yh yt)
        move (cond
               (and (<= -1 x-diff 1)
                    (<= -1 y-diff 1))
               [0 0]

               :else (let [move-x (if (zero? x-diff) 0 (/ x-diff (abs x-diff)))
                           move-y (if (zero? y-diff) 0 (/ y-diff (abs y-diff)))]
                       [move-x move-y]))]
    (point+ [xt yt] move)))

(defn move [{:keys [knots tail-seen]} [dir mult]]
  (let [[head & tails] knots
        new-head (new-head-pos head [dir mult])
        new-tails (loop [head new-head
                         tails tails
                         new-tails []]
                    (if (seq tails)
                      (let [[tail & rest] tails
                            new-tail (new-tail-pos head tail)]
                        (recur new-tail rest (conj new-tails new-tail)))
                      new-tails))]
    {:knots (concat [new-head] new-tails)
     :tail-seen (conj tail-seen (last (concat [new-head] new-tails)))}))


(comment
  ;; example 1
  (->>
   (util/read-lines "example/day9.txt")
   parse
   split-input
   (reduce move {:knots [[0 0] [0 0]] #_#_#_#_:head [0 0] :tail [0 0] :tail-seen #{[0 0]}})
   :tail-seen
   count
   )

  ;; part 1
  (->>
   (util/read-lines "input/day9.txt")
   parse
   split-input
   (reduce move {:knots [[0 0] [0 0]] :tail-seen #{[0 0]}})
   :tail-seen
   count
   )

  ;; example 2
  (->>
   (util/read-lines "example/day9-2.txt")
   parse
   split-input
   (reduce move {:knots (repeat 10 [0 0]) :tail-seen #{[0 0]}})
   :tail-seen
   count
   )

  ;; part 2
  (->>
   (util/read-lines "input/day9.txt")
   parse
   split-input
   (reduce move {:knots (repeat 10 [0 0]) :tail-seen #{[0 0]}})
   :tail-seen
   count
   )

  )
