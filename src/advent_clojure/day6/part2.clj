(ns advent-clojure.day6.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day6.txt"))
(def strings (map #(str/replace % #"\n" " ") (str/split input #"\n\n")))
(def strings1 (conj (drop-last strings) (subs (last strings) 0 (dec (count (last strings))))))

(defn num_occurences [s c]
  (count (filter (partial = c) s)))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(print (apply + (map #(let [people (inc (num_occurences % \space))]
                        (count (remove false? (map (partial = people) (for [c alphabet]
                                                                        (num_occurences % c)))))) strings1)))