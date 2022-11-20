(ns advent-clojure.day16.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day16.txt"))

(def ex "class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12")

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

(print (apply + (for [t (flatten (:nearby_tickets data))
                      :when (not (contains? possible_vals t))]
                  t)))

