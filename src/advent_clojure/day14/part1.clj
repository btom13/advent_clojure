(ns advent-clojure.day14.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day14.txt"))


;; either ["mask" "mask-value"] or [key value]
(def parsed (map #(let [matches (re-matches #"mem\[(\d+)\] = (\d+)" %)
                        mask (re-matches #"(mask) = (\w+)" %)]
                    (if matches
                      [(keyword (second matches)) (Integer/parseInt (nth matches 2))]
                      [(second mask) (nth mask 2)])) (str/split input #"\n")))

(defn apply_mask [mask value]
  (Long/parseLong (apply str (reduce #(assoc %1 (first %2) (second %2))
                                     (vec (format "%036d" (biginteger (Integer/toString value 2))))
                                     (for [i (range (count mask))
                                           :let [b (nth mask i)]
                                           :when (not= b \X)]
                                       [i b]))) 2))

(print (apply + (let [data (reduce
                            (fn [{mask :mask :as data} [first second]]
                              (if (= "mask" first)
                                (assoc data :mask second)
                                (assoc data first (apply_mask mask second)))) {:mask ""}
                            parsed)]
                  (for [x (keys data)
                        :when (not= x :mask)]
                    (x data)))))

