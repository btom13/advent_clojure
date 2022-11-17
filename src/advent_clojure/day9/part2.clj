(ns advent-clojure.day9.part2
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

(def sum (->> (let [preamble 25 nums strings]
                (remove #(is_sum? (first %) (second %))
                        (for [x (range preamble (count nums))]
                          [(subvec nums (- x preamble) x) (nth nums x)])))
              first
              second))

(defn make_vecs [arr n]
  (for [x (range (- (count arr) (dec n)))
        :let [sv (subvec arr x (+ x n))]
        :when (and (= (apply + sv) sum) (not= 1 (count sv)))]
    sv))

(print (as-> (remove #(= 0 (count %))
                     (for [x (range 1 (count strings))]
                       (make_vecs strings x))) $
         (first $)
         (first $)
         (+ (apply min $) (apply max $))))

