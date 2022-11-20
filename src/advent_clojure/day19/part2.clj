(ns advent-clojure.day19.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day19.txt"))


;; 0: a 2 3 | 3 2 b
;; 0: a (4 4 | 5 5) | (5 5 | 4 4) b
;; 0: a (a a | b b) | (b b | a a) b
(def ex "42: 9 14 | 10 1
9: 14 27 | 1 26
10: 23 14 | 28 1
1: \"a\"
11: 42 31
5: 1 14 | 15 1
19: 14 1 | 14 14
12: 24 14 | 19 1
16: 15 1 | 14 14
31: 14 17 | 1 13
6: 14 14 | 1 14
2: 1 24 | 14 4
0: 8 11
13: 14 3 | 1 12
15: 1 | 14
17: 14 2 | 1 7
23: 25 1 | 22 14
28: 16 1
4: 1 1
20: 14 14 | 1 15
3: 5 14 | 16 1
27: 1 6 | 14 18
14: \"b\"
21: 14 1 | 1 14
25: 1 1 | 1 14
22: 14 14
8: 42
26: 14 22 | 1 20
18: 15 15
7: 14 5 | 1 21
24: 14 1

abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
bbabbbbaabaabba
babbbbaabbbbbabbbbbbaabaaabaaa
aaabbbbbbaaaabaababaabababbabaaabbababababaaa
bbbbbbbaaaabbbbaaabbabaaa
bbbababbbbaaaaaaaabbababaaababaabab
ababaaaaaabaaab
ababaaaaabbbaba
baabbaaaabbaaaababbaababb
abbbbabbbbaaaababbbbbbaaaababb
aaaaabbaabaaaaababaa
aaaabbaaaabbaaa
aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
babaaabbbaaabaababbaabababaaab
aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba")

(re-matches #"(ab)+" "abababababab")
;; replace 8: 42 with 8: 42 | 42 8
;; replace 11: 42 31 with 11: ((42 31)|(42 11 31))

;; 8: becomes (42 | (42 (42 | (42 (42 | (42 (42 | (42 8)))))))) etc
;; essentially it is 42 any number of times (42)+
;; 11: becomes ((42 31) | (42 ((42 31) | (42 11 31)) 31))
;; essentially it is 42 n times followed by 31 n times
;; 0: becomes 42 x times then 42 n times followed by 31 n times
;; this regex will parse 42 (x+n-1) times then 31 n times
;; so we have to check if num 42 > num 31 at the end
;; to make sure that it's possible for the two n's to be equal (since x can be anything >= 1)


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

(defn expand_rules [num rules]
  (let [rule (get rules num)]
    (if (= (type rule) java.lang.String)
      rule
      (str "(?:" (reduce #(str %1 "|" %2)
                         (for [r rule]
                           (str "(?:" (reduce #(str %1 %2)
                                              (for [num r]
                                                (expand_rules num rules)))
                                ")")))
           ")"))))


(re-matches #"(((?:ab)+)((?:ba)+))" "abbaba")

(def updated_rules (-> rules
                       (assoc 8 (str "(?:" (expand_rules 42 rules) ")+"))
                       (assoc 11 (str "(?:" (str "(?:(?:" (expand_rules 42 rules) ")+)")
                                      (str "(?:(?:" (expand_rules 31 rules) ")+)") ")"))))

(def reg (re-pattern (expand_rules 0 updated_rules)))

(print (count (remove (fn [[_ str42 str31]]
                        (<= (count str42) (count str31)))
                      (map #(re-matches (re-pattern (str "((?:" (expand_rules 42 updated_rules) ")+)((?:" (expand_rules 31 updated_rules) ")+)")) %)
                           (remove nil? (map #(re-matches reg %) messages))))))


