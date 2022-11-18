(ns advent-clojure.day11.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day11.txt"))

(def example
  "L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL")

(def nums (map vec (str/split input #"\n")))

(defn num_adjacent [x y arr]
  (count (for [i (range (dec x) (+ 2 x))
               j (range (dec y) (+ 2 y))
               :when (and (> (count (first arr)) i -1) (> (count arr) j -1)
                          (or (not= i x) (not= j y))
                          (= \# (nth (nth arr j) i)))]
           [i j])))

(defn change_element [x y n arr]
  (cond
    (and (= n \L) (= 0 (num_adjacent x y arr))) \#
    (and (= n \#) (>= (num_adjacent x y arr) 4)) \L
    :else n))

(defn modify [nums]
  (map-indexed (fn [y arr]
                 (map-indexed #(change_element %1 y %2 nums) arr)) nums))

(print (apply + (map #(count (filter (partial = \#) %))
                     (loop [arr nums]
                       (let [new (modify arr)]
                         (if (= new arr)
                           new
                           (recur new)))))))



