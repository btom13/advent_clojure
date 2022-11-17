(ns advent-clojure.day9.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day9.txt"))

(defn split [reg string]
  (str/split string reg))

(def strings (->> input
                  (split #"\n")
                  (mapv read-string)))

(defn is_sum? [vec n]
  (->> (for [e vec]
         (when (some #{(- n e)} vec) e))
       (filter some?)
       count
       (< 1)))

(->> (let [preamble 25 nums strings]
       (remove #(is_sum? (first %) (second %))
               (for [x (range preamble (count nums))]
                 [(subvec nums (- x preamble) x) (nth nums x)])))
     first
     second
     print)
