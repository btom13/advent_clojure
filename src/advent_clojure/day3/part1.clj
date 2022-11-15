(ns advent-clojure.day3.part1)
(require '[clojure.string :as str])

(def input (slurp "./resources/day3.txt"))

(def strings (str/split input #"\n"))

strings

(defn tree_at [h, s]
  (let [x (mod (* 3 h) (count s))]
    (= (get s x) \#)))

(print (count (remove false? (for [x (range (count strings))]
                               (tree_at x (nth strings x))))))

