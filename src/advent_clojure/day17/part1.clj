(ns advent-clojure.day17.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day17.txt"))
(def ex ".#.
..#
###")

(defn split [re s]
  (str/split s re))

(->> input
     (split #"\n")
     (map-indexed #(remove nil?
                           (map-indexed (fn [idx char]
                                          (when (= char \#)
                                            [idx %1 0])) %2)))
     (apply concat)
     (into #{})
     (def grid))
#{[1 0 0] [2 2 0] [0 2 0] [1 2 0] [2 1 0]}
;; stores all active cells into the set

;; number of cells 1 away in any direction
(defn num_adjacency [[val_x val_y val_z] grid]
  (count (for [x (range -1 2)
               y (range -1 2)
               z (range -1 2)
               :when (and (not= 0 x y z)
                          (contains? grid [(+ val_x x) (+ val_y y) (+ val_z z)]))]
           true)))

;; all cells 1 away from any cell in the grid
(defn vals_to_check [grid]
  (into #{} (for [[gx gy gz] grid
                  x (range -1 2)
                  y (range -1 2)
                  z (range -1 2)]
              [(+ x gx) (+ y gy) (+ z gz)])))

(defn change [grid]
  (set (remove nil? (map (fn [coord]
                           (if (contains? grid coord)
                             (when (<= 2 (num_adjacency coord grid) 3) coord)
                             (when (= (num_adjacency coord grid) 3) coord))) (vals_to_check grid)))))

(print (count (nth (iterate change grid) 6)))
