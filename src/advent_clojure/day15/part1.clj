(ns advent-clojure.day15.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day15.txt"))
(def nums (map read-string (str/split input #"[,\n]")))
(def ex '(0 3 6))
(def data (apply conj (map-indexed #(hash-map %2 [(inc %1) (inc %1)]) nums)))

(print (loop [d data
              n (count d)
              prev (last nums)]
         (let [[prev_time prev_prev_time] (get d prev)
               new (- prev_time prev_prev_time)]
           (if (= n 2020)
             prev
             (if (nil? (get d new))
               (recur (assoc d new [(inc n) (inc n)]) (inc n) new)
               (recur (assoc d new [(inc n) (first (get d new))]) (inc n) new))))))
