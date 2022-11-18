(ns advent-clojure.day13.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day13.txt"))

(def earliest_time (read-string (nth (str/split input #"\n") 0)))
(def buses (map read-string (remove (partial = "x") (str/split (nth (str/split input #"\n") 1) #","))))

(->> (for [b buses
           :let [t (- b (mod earliest_time b))]]
       [b (if (= t b) 0 t)])
     (apply min-key second)
     (apply *)
     print)
