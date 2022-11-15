(ns advent-clojure.day2.part2)
(require '[clojure.string :as str])

(def input (slurp "./resources/day2.txt"))

(def strings (str/split input #"\n"))
(def strings2 (map #(str/split % #" ") strings))

(def formatted (map #(concat (map read-string (str/split (nth % 0) #"-"))
                             (drop-last 1 (nth % 1)) (drop 2 %)) strings2))

(print (apply + (map #(as-> (str/split (nth % 3) #"") $
                        (distinct? (= (str (nth % 2)) (nth $ (dec (nth % 0))))
                                   (= (str (nth % 2)) (nth $ (dec (nth % 1)))))
                        (if $ 1 0)) formatted)))
