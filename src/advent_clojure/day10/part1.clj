(ns advent-clojure.day10.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day10.txt"))

(def nums (map read-string (str/split input #"\n")))
(def sorted (sort (conj nums (+ 3 (apply max nums)) 0)))

(def differences (map #(- (second %) (first %))
                      (partition 2 1 sorted)))

(print (* (count (filter (partial = 3) differences)) (count (filter (partial = 1) differences))))
