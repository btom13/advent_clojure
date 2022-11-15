(ns advent-clojure.day1.part1)
(require '[clojure.string :as str])

(def input (slurp "./resources/day1.txt"))

(def strings (str/split input #"\n"))
(def numbers (map read-string strings))

(print (apply * (nth (remove nil? (for [x numbers y numbers]
                                    (when (= (+ x y) 2020)
                                      [x y]))) 0)))

