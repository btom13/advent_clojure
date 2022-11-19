(ns advent-clojure.day14.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day14.txt"))

;; either ["mask" "mask-value"] or [key value]
(def parsed (map #(let [matches (re-matches #"mem\[(\d+)\] = (\d+)" %)
                        mask (re-matches #"(mask) = (\w+)" %)]
                    (if matches
                      [(Integer/parseInt (second matches)) (Integer/parseInt (nth matches 2))]
                      [(second mask) (nth mask 2)])) (str/split input #"\n")))

(defn apply_mask [mask value]
  (apply str (reduce #(assoc %1 (first %2) (second %2))
                     (vec (format "%036d" (biginteger (Integer/toString value 2))))
                     (for [i (range (count mask))
                           :let [b (nth mask i)]
                           :when (not= b \0)]
                       [i b]))))

(defn expand_addresses [address]
  (map #(Long/parseLong % 2)
       (loop [addresses [address]]
         (if (str/includes? (first addresses) "X")
           (let [new (flatten (map #(vector (str/replace-first % #"X" "1")
                                            (str/replace-first % #"X" "0")) addresses))]
             (recur new))
           addresses))))

(defn set_addresses [data val keys]
  (reduce #(assoc %1 %2 val) data keys))

(print (apply + (let [data (reduce
                            (fn [{mask :mask :as data} [first second]]
                              (if (= "mask" first)
                                (assoc data :mask second)
                                (->> (apply_mask mask first)
                                     expand_addresses
                                     (set_addresses data second)))) {:mask ""}
                            parsed)]
                  (for [x (keys data)
                        :when (not= x :mask)]
                    (get data x)))))
