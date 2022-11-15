(ns advent-clojure.day3.part2)
(require '[clojure.string :as str])

(def input (slurp "./resources/day3.txt"))

(def strings (str/split input #"\n"))

(defn tree_at [h s slope]
  (let [x (mod (* slope h) (count s))]
    (= (get s x) \#)))

(range 0 (count strings) 2)

(defn trees_with_slope [x y]
  (for [i (range 0 (count strings) y)]
    (tree_at i (nth strings i) (/ x y))))

(print (->> [(trees_with_slope 1 1) (trees_with_slope 3 1) (trees_with_slope 5 1) (trees_with_slope 7 1) (trees_with_slope 1 2)]
            (map (partial remove false?))
            (map count)
            (apply *)))
