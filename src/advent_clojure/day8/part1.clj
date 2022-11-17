(ns advent-clojure.day8.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day8.txt"))

(defn split [reg string]
  (str/split string reg))

(def strings (split #"\n" input))

(defn parse [s] ; jmp +1 or acc -5 or nop +0)
  (let [[_ cmd sign n]
        (re-matches #"(jmp|acc|nop) (\+|\-)(\d+)" s)]
    [cmd sign (Integer/parseInt n)]))

(defn parse_sign [sign number]
  (if (= sign "+")
    number
    (- number)))

(print (loop [line 0 st #{} acc 0]
         (if (contains? st line)
           acc
           (let  [[cmd sign number] (parse (nth strings line))]
             (case cmd
               "jmp" (recur (+ line (parse_sign sign number)) (conj st line) acc)
               "acc" (recur (inc line) (conj st line) (+ acc (parse_sign sign number)))
               "nop" (recur (inc line) (conj st line) acc)
               acc)))))