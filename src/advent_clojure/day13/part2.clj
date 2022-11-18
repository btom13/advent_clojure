(ns advent-clojure.day13.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day13.txt"))

(def buses (remove nil? (map-indexed #(when (not= %2 "x")
                                        [(Integer/parseInt %2) %1])
                                     (str/split (nth (str/split input #"\n") 1) #","))))
(def example "17,x,13,19")

;; 7,13,x,x,59,x,31,19
;; find a number n where 
;; 0 mod 7  = n
;; 1 mod 13 = n
;; 4 mod 59 = n
;; 6 mod 31 = n
;; 7 mod 19 = n
;; the chinese remainder theorem solves a set of equations like this


(defn chinese_remainder_theorem [ls]
  (let [N (apply * (map first ls))]
    (mod (apply + (for [l ls
                        :let [[n b] l
                              a (mod (- b) n)
                              Ni (/ N n)
                              Mi (.modInverse (biginteger Ni) (biginteger n))]]
                    (* a Mi Ni))) N)))

(print (str (chinese_remainder_theorem buses)))

