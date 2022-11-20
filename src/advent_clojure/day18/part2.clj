(ns advent-clojure.day18.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day18.txt"))

(def equations (str/split input #"\n"))

(defn eval_no_parens [eq]
  (let [pattern (re-pattern (str #"(\d+) " (if (str/index-of eq "+")
                                             #"(\+)"
                                             #"([\+\*])") #" (\d+)"))
        [_ x1 op x2]
        (re-find pattern eq)]
    (cond (= op "+") (recur (str/replace-first eq (str x1 " + " x2) (str (+ (Long/parseLong x1) (Long/parseLong x2)))))
          (= op "*") (recur (str/replace-first eq (str x1 " * " x2) (str (* (Long/parseLong x1) (Long/parseLong x2)))))
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
