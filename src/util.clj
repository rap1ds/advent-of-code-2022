(ns util
  (:refer-clojure :exclude [read])
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn read [file]
  (slurp (io/resource file)))

(defn read-lines [file]
  (-> file
      read
      (str/split-lines)))

(defn read-longs [file]
  (map parse-long (read-lines file)))
