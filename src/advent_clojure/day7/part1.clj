(ns advent-clojure.day7.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day7.txt"))

(def strings (map #(str/split % #" contain ") (str/split input #"\n")))

(def dict (apply merge (map #(hash-map
                              (keyword (str/replace (subs (nth % 0) 0 (- (count (nth % 0)) 5)) #" " "_"))
                              (str/split (nth % 1) #", ")) strings)))

(defn bag_in_bag [small big]
  (if (str/includes? (big dict) small)
    big nil))

(print (count (loop [check ["shiny gold"] all #{}]
                (let [ls (remove nil? (for [big (keys dict) small check]
                                        (bag_in_bag small big)))]
                  (if (zero? (count ls)) all
                      (recur (map #(str/replace (subs (str %) 1) #"_" " ") ls) (set (concat all ls))))))))

