(ns advent-clojure.day20.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day20.txt"))
(def tiles (into {} (map #(let [[tile & grid] (str/split % #"\n")]
                            {(keyword (str/replace (str/replace tile " " "") ":" ""))
                             {:grid (map vec grid) :left (seq (first grid)) :right (seq (last grid))
                              :top (map first grid) :bottom (map last grid)}}) (str/split input #"\n\n"))))

(defn get_borders [key]
  (vals (select-keys (key tiles) [:left :right :top :bottom])))

(defn has_adjecents [tile]
  (let [others (set (apply concat (map get_borders (keys (dissoc tiles tile)))))]
    (filter #(or (contains? others %)
                 (contains? others (reverse %)))
            (get_borders tile))))

(->> (map #(hash-map % (has_adjecents %)) (keys tiles))
     (remove #(not= 2 (count (first (vals %)))))
     (map keys)
     flatten
     (map #(subs (str %) 5))
     (map read-string)
     (apply *)
     print)
