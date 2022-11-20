(ns advent-clojure.day16.part2
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(def input (slurp "./resources/day16.txt"))
input
(re-matches #"(\w+.*?):" "hi bye:")
(def ex "class: 0-1 or 4-19
row: 0-5 or 8-19
seat: 0-13 or 16-19

your ticket:
11,12,13

nearby tickets:
3,9,18
15,1,5
5,14,9
")

(defn split [re s]
  (str/split s re))

(def data
  (let [[fields your_ticket nearby_tickets] (str/split input #"your ticket:\n|nearby tickets:\n")
        fields (->> fields
                    (split #"\n")
                    (map #(let [[_ f before1 after1 before2 after2]
                                (re-matches #"(\w+.*?): (\d+)-(\d+) or (\d+)-(\d+)" %)]
                            [(keyword (str/replace f " " "_"))
                             (set (concat (range (Integer/parseInt before1) (inc (Integer/parseInt after1)))
                                          (range (Integer/parseInt before2) (inc (Integer/parseInt after2)))))]))
                    (into {}))
        your_ticket (map read-string (split #"," (str/replace your_ticket "\n" "")))
        nearby_tickets (->> nearby_tickets
                            (split #"\n")
                            (map #(map read-string (split #"," %))))]
    {:fields fields :your_ticket your_ticket :nearby_tickets nearby_tickets}))

(def possible_vals (set (apply concat (vals (:fields data)))))

(def validated_data
  (assoc data :nearby_tickets
         (filter #(= 0 (count (for [f % :when (not (contains? possible_vals f))]
                                false))) (:nearby_tickets data))))
(defn possible_fields [ticket]
  (map #(set (for [[key value] (:fields validated_data)
                   :when (contains? value %)]
               key)) ticket))

;; (#{:row} #{:class :row} #{:class :row :seat})
;; corresponds to column 1 having row be possible
;; column 2 having class and row, and column 3 having all 3 be possible
;; we then have to filter out row from column 2 and 3
;; then class from column 3
(def fields (let [possible_nearby_fields (map possible_fields (:nearby_tickets validated_data))
                  your_ticket_fields (possible_fields (:your_ticket validated_data))]
              (for [i (range (count (:your_ticket validated_data)))
                    :let [nf (map #(nth % i) possible_nearby_fields)
                          yf (nth your_ticket_fields i)]]
                (apply set/intersection (conj nf yf)))))


(print (apply * (for [n (for [f [:departure_location :departure_station :departure_platform
                                 :departure_track :departure_date :departure_time]]
                          (f (into {} (loop [fields fields
                                             final []]
                                        (if (= 0 (count (remove #(= 0 (count %)) fields)))
                                          final
                                          (let [confirmed (remove nil? (map-indexed #(when (= 1 (count %2))
                                                                                       [(first %2) %1]) fields))]
                                            (recur (for [f fields]
                                                     (apply disj f (map first confirmed))) (concat final confirmed))))))))]
                  (nth (:your_ticket validated_data) n))))
