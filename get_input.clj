(require '[clj-http.client :as client])

(def id (clojure.string/trim-newline (slurp "./cookie.txt")))

(def inputs (for [x (range 1 26)]
              (:body (client/get (str "https://adventofcode.com/2020/day/" x "/input")
                                 {:headers {"Cookie" (str "session=" id)}}))))

(for [x (range 1 26)]
  (spit (str "./resources/day" x ".txt") (nth inputs (dec x))))
