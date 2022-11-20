(ns advent-clojure.day18.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day18.txt"))

(def ex "2 * 3 + (4 * 5)")
;;                  20
;;         6
;;             26
(def equations (str/split input #"\n"))


(defn eval_no_parens [eq]
  (let [[_      x1     op       x2      rest]
        (re-find #"(\d+) ([\+\*]) (\d+) ?(.*)" eq)]
    (cond (= op "+") (recur (str (+ (Long/parseLong x1) (Long/parseLong x2)) " " rest))
          (= op "*") (recur (str (* (Long/parseLong x1) (Long/parseLong x2)) " " rest))
          :else (str/replace eq " " ""))))

(defn evaluate [eq]
  (if (nil? (str/index-of eq ")"))
    (eval_no_parens eq)
    (let [ss (str/reverse (subs eq 0 (str/index-of eq ")")))
          inner (str/reverse (subs ss 0 (str/index-of ss "(")))]
      (recur (str/replace-first eq (str "(" inner ")") (eval_no_parens inner))))))

(print (->> equations
            (map evaluate)
            (map read-string)
            (apply +)))

