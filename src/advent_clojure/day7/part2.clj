(ns advent-clojure.day7.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day7.txt"))

(def strings (map #(str/split % #" contain ") (str/split input #"\n")))

(def dict (apply merge (map #(hash-map
                              (keyword (str/replace (subs (nth % 0) 0 (- (count (nth % 0)) 5)) #" " "_"))
                              (str/split (nth % 1) #", ")) strings)))


(defn get_smalls [big val]
  (if (not= (nth (big dict) 0) "no other bags.")
    (apply merge (map #(let [spl (str/split % #" ")]
                         {(keyword (str (nth spl 1) "_" (nth spl 2)))
                          (* val (read-string (nth spl 0)))}) (big dict)))
    {}))

(print (loop [check {:shiny_gold 1} number 0]
         (let [ls (apply merge-with + (for [big (keys check)]
                                        (get_smalls big (big check))))]
           (if (zero? (count ls)) number
               (recur ls (+ number (apply + (vals ls))))))))
