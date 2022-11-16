(ns advent-clojure.day6.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day6.txt"))
(def strings (map #(str/replace % #"\n" " ") (str/split input #"\n\n")))
(print (apply + (map #(->> (set %)
                           (remove #{\space})
                           (count)) strings)))