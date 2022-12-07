(ns day7
  (:require util
            [clojure.string :as str])
  )

(defn parse* [ctx line]
  (cond
    (str/starts-with? line "$ cd")
    (let [new-path (subs line (count "$ cd "))]
      (if (= ".." new-path)
        (update ctx :path pop)
        (update ctx :path conj new-path)))
    (= "$ ls" line)
    ctx

    (str/starts-with? line "dir")
    ctx

    :else
    (let [[size-str file] (str/split line #" ")]
      (update ctx :files assoc (conj (:path ctx) file) (parse-long size-str))
      )))

(defn parse [lines]
  (:files
   (reduce
    parse*
    {:files {}
     :path []}
    lines)))

(defn calc-parent-sizes [dir-sizes]
  (reduce
   (fn [sizes [path size]]
     (loop [path path
            sizes sizes]
       (if (seq path)
         (recur (drop-last path) (update sizes path (fnil + 0) size))
         sizes)))
   {}
   (sort-by (fn [i] (- (count (first i)))) dir-sizes)))

(comment
  ;; example 1
  (->> (util/read-lines "example/day7.txt")
       parse
       (group-by (comp vec butlast first))
       (map (fn [[path files]]
              [path (reduce (fn [acc [_ size]] (+ acc size)) 0 files)]))
       (into {})
       calc-parent-sizes
       (map second)
       (filter #(< % 100000))
       (reduce +)
       )

  ;; part 1
  (->> (util/read-lines "input/day7.txt")
       parse
       (group-by (comp vec butlast first))
       (map (fn [[path files]]
              [path (reduce (fn [acc [_ size]] (+ acc size)) 0 files)]))
       (into {})
       calc-parent-sizes
       (map second)
       (filter #(< % 100000))
       (reduce +)
       )

  ;; example 2
  (let [total-size 70000000
        size-needed 30000000
        dir-sizes
        (->> (util/read-lines "example/day7.txt")
             parse
             (group-by (comp vec butlast first))
             (map (fn [[path files]]
                    [path (reduce (fn [acc [_ size]] (+ acc size)) 0 files)]))
             (into {})
             calc-parent-sizes
             )
        size-available (- total-size (get dir-sizes ["/"]))]

    (->> dir-sizes
         (filter #(< size-needed (+ size-available (second %))))
         (sort-by second)
         first)
    )

  ;; part 2
  (let [total-size 70000000
        size-needed 30000000
        dir-sizes
        (->> (util/read-lines "input/day7.txt")
             parse
             (group-by (comp vec butlast first))
             (map (fn [[path files]]
                    [path (reduce (fn [acc [_ size]] (+ acc size)) 0 files)]))
             (into {})
             calc-parent-sizes
             )
        size-available (- total-size (get dir-sizes ["/"]))]

    (->> dir-sizes
         (filter #(< size-needed (+ size-available (second %))))
         (sort-by second)
         first)
    )

  )
