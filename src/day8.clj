(ns day8
  (:require util))

(defn parse [lines]
  (map #(map (comp parse-long str) %) lines))

(defn xy-map [rows]
  (into {}
        (apply concat
               (map-indexed (fn [y cols]
                              (map-indexed (fn [x val]
                                             [[x y] val]) cols)) rows))))

(def dirs [:left :down :right :up])

(defn point-at-dir [[x y] dir]
  (case dir
    :right [(inc x) y]
    :left [(dec x) y]
    :up [x (dec y)]
    :down [x (inc y)]))

(defn height-at-dir [heights current-pos dir]
  (let [point (point-at-dir current-pos dir)]
    (get heights point)))

(defn highest-at-dir [heights current-pos dir]
  (let [neighbour-height (height-at-dir heights current-pos dir)]
    (when neighbour-height
      (let [highest (highest-at-dir heights (point-at-dir current-pos dir) dir)]
        (cond
          (nil? highest) neighbour-height

          (< neighbour-height highest)
          highest

          :else neighbour-height)))))

(defn visible? [heights current-pos]
  (let [current-height (get heights current-pos)
        highest-trees (set (map #(highest-at-dir heights current-pos %) dirs))]
    (if (contains? highest-trees nil)
      true
      (< (apply min highest-trees) current-height))))

(defn score-at-dir [heights height pos dir]
  (loop [pos pos
         trees 0]
    (let [neighbour-height (height-at-dir heights pos dir)]
      (cond
        (nil? neighbour-height) trees
        (< neighbour-height height) (recur (point-at-dir pos dir) (inc trees))
        :else (inc trees)))))

(defn scenic-score [heights pos]
  (let [current-height (get heights pos)]
    (apply * (map #(score-at-dir heights current-height pos %) dirs))))

(comment
  ;; example 1
  (let [heights (->> (util/read-lines "example/day8.txt")
                     parse
                     xy-map)]
    (->> (keys heights)
         (map #(visible? heights %))
         (filter true?)
         count))

  ;; part 1
  (let [heights (->> (util/read-lines "input/day8.txt")
                     parse
                     xy-map)]
    (->> (keys heights)
         (map #(visible? heights %))
         (filter true?)
         count))

  ;; example 2
  (let [heights (->> (util/read-lines "example/day8.txt")
                     parse
                     xy-map)]
    (->> (keys heights)
         (map #(scenic-score heights %))
         (apply max)))

  ;; part 2
  (let [heights (->> (util/read-lines "input/day8.txt")
                     parse
                     xy-map)]
    (->> (keys heights)
         (map #(scenic-score heights %))
         (apply max)))


  )
