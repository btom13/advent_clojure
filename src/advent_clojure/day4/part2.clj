(ns advent-clojure.day4.part2)
(require '[clojure.string :as str])

(def input (slurp "./resources/day4.txt"))

(def strings (map #(str/replace % #"\n" " ") (str/split input #"\n\n")))

(defn check_value [value lower upper]
  (and (>= value lower) (<= value upper)))

(defn valid_string [value]
  (and (= (count value) 6) (= (count (str/split value #"[0123456789abcdef]")) 0)))

(def fields [:byr :iyr :eyr :hgt :hcl :ecl :pid])


(defn verify_pair [key value]
  (case key
    :byr (check_value (read-string value) 1920 2002)
    :iyr (check_value (read-string value) 2010 2020)
    :eyr (check_value (read-string value) 2020 2030)
    :hgt (if (= "cm" (subs value (- (count value) 2)))
           (check_value (read-string (subs value 0 (- (count value) 2))) 150 193)
           (if (= "in" (subs value (- (count value) 2)))
             (check_value (read-string (subs value 0 (- (count value) 2))) 59 76)
             false))
    :hcl (if (= \# (get value 0))
           (valid_string (subs value 1))
           false)
    :pid (and (every? #(Character/isDigit %) value) (= 9 (count value)))
    :ecl (= 1 (count (remove false? (map (partial = value) ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"]))))
    :cid true
    false))

(def vec_of_map (map #(into (sorted-map) (map (fn [x] [(keyword (nth x 0)) (nth x 1)]) (partition 2 (str/split % #"[: ]")))) strings))

(print (->> vec_of_map
            (map #(->> (for [x fields]
                         (if (x %) (verify_pair x (x %)) false))
                       (remove false?)
                       (count)
                       (= 7)))
            (remove false?)
            (count)))

