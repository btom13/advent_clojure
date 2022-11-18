(ns advent-clojure.day12.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day12.txt"))
(def instructions (map #(let [[_ dir num]
                              (re-matches #"([NSEWLRF])(\d+)" %)]
                          [dir (Integer/parseInt num)]) (str/split input #"\n")))

(defn parse_instructions [[ship_x ship_y way_x way_y] [dir num]]
  (case dir
    "N" [ship_x ship_y way_x (+ way_y num)]
    "S" [ship_x ship_y way_x (- way_y num)]
    "E" [ship_x ship_y (+ way_x num) way_y]
    "W" [ship_x ship_y (- way_x num) way_y]
    "L" (case num
          0 [ship_x ship_y way_x way_y]
          90 [ship_x ship_y (- way_y) way_x]
          180 [ship_x ship_y (- way_x) (- way_y)]
          270 [ship_x ship_y way_y (- way_x)])
    "R" (parse_instructions [ship_x ship_y way_x way_y] ["L" (mod (- num) 360)])
    "F" [(+ ship_x (* way_x num)) (+ ship_y (* way_y num)) way_x way_y]
    [ship_x ship_y way_x way_y]))

(print (let [[x y _] (reduce parse_instructions [0 0 10 1] instructions)]
         (+ (abs x) (abs y))))
