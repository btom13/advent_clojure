(ns advent-clojure.day19.part1
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day19.txt"))

;; 0: a 2 3 | 3 2 b
;; 0: a (4 4 | 5 5) | (5 5 | 4 4) b
;; 0: a (a a | b b) | (b b | a a) b
(def ex "0: 4 1 5
1: 2 3 | 3 2
2: 4 4 | 5 5
3: 4 5 | 5 4
4: \"a\"
5: \"b\"

ababbb
bababa
abbbab
aaabbb
aaaabbb")

(defn parse_rule [rule]
  (if (str/index-of rule "\"")
    (let [[_ num c] (re-matches #"(\d+): \"([a-z])\"" rule)]
      {(Integer/parseInt num) c})
    (let [[num rules] (str/split rule #": ")]
      {(Integer/parseInt num) (map #(map read-string (str/split % #" "))
                                   (str/split rules #"\| "))})))

(def rules (let [[rules _] (str/split input #"\n\n")
                 rules (str/split rules #"\n")]
             (into {} (map parse_rule rules))))

(def messages (let [[_ messages] (str/split input #"\n\n")]
                (str/split messages #"\n")))

(defn expand_rules [num]
  (let [rule (get rules num)]
    (if (= (type rule) java.lang.String)
      rule
      (str "(?:" (reduce #(str %1 "|" %2)
                         (for [r rule]
                           (str "(?:" (reduce #(str %1 %2)
                                              (for [num r]
                                                (expand_rules num)))
                                ")")))
           ")"))))

(def reg (re-pattern (expand_rules 0)))

(print (count (remove nil? (map #(re-matches reg %) messages))))
