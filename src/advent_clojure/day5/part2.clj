(ns advent-clojure.day5.part2)
(require '[clojure.string :as str])
(require '[clojure.math :as math])

(def input (slurp "./resources/day5.txt"))
(def strings (str/split input #"\n"))

(defn avg [low high]
  (/ (+ low high) 2))

(defn search [s]
  (loop [n 0 x (get s 0) lower 0 upper (math/pow 2.0 (count s))]
    (if (not= n (count s))
      (recur (inc n) (get s (inc n))
             (if (nil? (some #{x} [\B \R]))
               lower
               (avg lower upper))
             (if (nil? (some #{x} [\F \L]))
               upper
               (avg lower upper)))
      lower)))

(defn find_pos [s]
  [(search (subs s 0 7)) (search (subs s 7))])

(def ls (sort (map #(+ (* (nth (find_pos %) 0) 8) (nth (find_pos %) 1)) strings)))

(print (nth (remove nil?
                    (map #(if (not= %1 (- %2 1))
                            (inc %1) nil) ls (drop 1 ls))) 0))
