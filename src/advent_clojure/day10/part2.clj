(ns advent-clojure.day10.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day10.txt"))

(def nums (map read-string (str/split input #"\n")))
(def sorted (sort (conj nums (+ 3 (apply max nums)) 0)))

(def find_num_possible
  (memoize (fn [n st]
             (cond
               (= n 0) 1
               (not (contains? st n)) 0
               :else (apply + (for [x (range 1 4)]
                                (find_num_possible (- n x) st)))))))

(print (find_num_possible (apply max sorted) (set sorted)))