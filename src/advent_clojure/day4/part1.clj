(ns advent-clojure.day4.part1)
(require '[clojure.string :as str])

(def input (slurp "./resources/day4.txt"))

(def strings (map #(str/replace % #"\n" " ") (str/split input #"\n\n")))

(def fields [:byr :iyr :eyr :hgt :hcl :ecl :pid])

(def vec_of_map (map #(into (sorted-map) (map (fn [x] [(keyword (nth x 0)) (nth x 1)]) (partition 2 (str/split % #"[: ]")))) strings))
(print (count (remove false? (map #(apply = true (map (partial contains? %) fields)) vec_of_map))))
