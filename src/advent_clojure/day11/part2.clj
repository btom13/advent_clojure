(ns advent-clojure.day11.part2
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
L.LLLLL.LL
")

(def nums (map vec (str/split input #"\n")))

(defn closest_in_direction [x y i j arr]
  (cond
    (not (and (> (count (first arr)) (+ i x) -1) (> (count arr) (+ j y) -1))) \.
    (not= \. (nth (nth arr (+ j y)) (+ i x))) (nth (nth arr (+ j y)) (+ i x))
    :else (recur (+ x i) (+ y j) i j arr)))

(defn num_adjacent [x y arr]
  (count (for [i (range -1 2)
               j (range -1 2)
               :when (and (or (not= i 0) (not= j 0))
                          (= \# (closest_in_direction x y i j arr)))]
           true)))

(defn change_element [x y n arr]
  (cond
    (and (= n \L) (= 0 (num_adjacent x y arr))) \#
    (and (= n \#) (>= (num_adjacent x y arr) 5)) \L
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

