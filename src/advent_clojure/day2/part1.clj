(ns advent-clojure.day2.part1)
(require '[clojure.string :as str])

(def input (slurp "./resources/day2.txt"))

(def strings (str/split input #"\n"))
(def strings2 (map #(str/split % #" ") strings))

(def formatted (map #(concat (map read-string (str/split (nth % 0) #"-"))
                             (drop-last 1 (nth % 1)) (drop 2 %)) strings2))


(print (count (remove false? (map #(as-> (str/split (nth % 3) #"") $
                                     (filter (partial = (str (nth % 2))) $)
                                     (count $)
                                     (and (<= (nth % 0) $) (>= (nth % 1) $))) formatted))))
