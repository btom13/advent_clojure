(ns advent-clojure.day12.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day12.txt"))
(def instructions (map #(let [[_ dir num]
                              (re-matches #"([NSEWLRF])(\d+)" %)]
                          [dir (Integer/parseInt num)]) (str/split input #"\n")))

(defn parse_instructions [[x y facing] [dir num]]
  (case dir
    "N" [x (+ y num) facing]
    "S" [x (- y num) facing]
    "E" [(+ x num) y facing]
    "W" [(- x num) y facing]
    "L" [x y (mod (- facing num) 360)]
    "R" [x y (mod (+ facing num) 360)]
    "F" (case facing
          0 (parse_instructions [x y facing] ["N" num])
          90 (parse_instructions [x y facing] ["E" num])
          180 (parse_instructions [x y facing] ["S" num])
          270 (parse_instructions [x y facing] ["W" num]))
    [x y facing]))

(print (let [[x y _] (reduce parse_instructions [0 0 90] instructions)]
         (+ (abs x) (abs y))))

