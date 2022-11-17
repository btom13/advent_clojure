(ns advent-clojure.day8.part2
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

(defn run [s]
  (loop [line 0 st #{} acc 0]
    (if (= line (count s))
      acc
      (if (contains? st line)
        nil
        (let  [[cmd sign number] (parse (nth s line))]
          (case cmd
            "jmp" (when (<= (+ line (parse_sign sign number)) (count s))
                    (recur (+ line (parse_sign sign number)) (conj st line) acc))
            "acc" (recur (inc line) (conj st line) (+ acc (parse_sign sign number)))
            "nop" (recur (inc line) (conj st line) acc)
            acc))))))

(defn change_string [s]
  (let [[cmd sign n] (parse s)]
    (case cmd
      "jmp" (str "nop " sign n)
      "acc" (str "acc " sign n)
      "nop" (str "jmp " sign n))))

(->> (for [i (range (count strings))]
       (->> (nth strings i)
            change_string
            (assoc strings i)))
     (map run)
     (remove nil?)
     first
     print)

