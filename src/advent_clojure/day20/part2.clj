(ns advent-clojure.day20.part2
  (:require [clojure.string :as str]))

(def input (slurp "./resources/day20.txt"))
(def tiles (into {} (map #(let [[tile & grid] (str/split % #"\n")]
                            {(keyword (str/replace (str/replace tile " " "") ":" ""))
                             {:grid (map vec grid) :top (seq (first grid)) :bottom (reverse (seq (last grid)))
                              :left (reverse (map first grid)) :right (map last grid)
                              :orientation 0 :flipped false}}) (str/split input #"\n\n"))))

(defn get_borders [key]

  (vals (select-keys (key tiles) [:left :right :top :bottom])))

(defn has_adjecents [tile]
  (let [others (set (apply concat (map get_borders (keys (dissoc tiles tile)))))]
    (filter #(or (contains? others %)
                 (contains? others (reverse %)))
            (get_borders tile))))

(def corners (->> (map #(hash-map % (has_adjecents %)) (keys tiles))
                  (remove #(not= 2 (count (first (vals %)))))
                  (map keys)
                  flatten))

(some #{`(\. \# \# \. \. \. \. \# \# \#)} (vals (select-keys (:Tile1009 tiles) [:left :right :top :bottom])))

(defn new_tile_orientation [current_tile_side current_tile_flipped
                            current_tile_orientation flipped connected_tile_side]
  (let [connected_tile_flipped (not= current_tile_flipped flipped)
        current_directions {:top (if current_tile_flipped 2 0)
                            :right 1
                            :bottom (if current_tile_flipped 0 2)
                            :left 3}
        new_directions {:top (if connected_tile_flipped 2 0)
                        :right 1
                        :bottom (if connected_tile_flipped 0 2)
                        :left 3}
        connected_tile_orientation (mod (+ (current_tile_side current_directions) current_tile_orientation (- (connected_tile_side new_directions))) 4)]
    [connected_tile_orientation (not= current_tile_flipped flipped)]))

(new_tile_orientation :bottom false 0 true :top)



(defn deep-merge [v & vs]
  (letfn [(rec-merge [v1 v2]
            (if (and (map? v1) (map? v2))
              (merge-with deep-merge v1 v2)
              v2))]
    (if (some identity vs)
      (reduce #(rec-merge %1 %2) v vs)
      (last vs))))

(loop [tiles
       tiles #_{:Tile2311
                {:grid
                 `([\. \. \# \# \. \# \. \. \# \.]
                   [\# \# \. \. \# \. \. \. \. \.]
                   [\# \. \. \. \# \# \. \. \# \.]
                   [\# \# \# \# \. \# \. \. \. \#]
                   [\# \# \. \# \# \. \# \# \# \.]
                   [\# \# \. \. \. \# \. \# \# \#]
                   [\. \# \. \# \. \# \. \. \# \#]
                   [\. \. \# \. \. \. \. \# \. \.]
                   [\# \# \# \. \. \. \# \. \# \.]
                   [\. \. \# \# \# \. \. \# \# \#]),
                 :left `(\. \# \. \. \# \. \# \# \. \.),
                 :right `(\. \. \# \# \# \. \. \# \# \#),
                 :top `(\. \# \# \# \# \# \. \. \# \.),
                 :bottom `(\# \. \. \# \# \. \# \. \. \.),
                 :orientation 0,
                 :flipped false},
                :Tile1427
                {:grid
                 `([\# \# \# \. \# \# \. \# \. \.]
                   [\. \# \. \. \# \. \# \# \. \.]
                   [\. \# \. \# \# \. \# \. \. \#]
                   [\# \. \# \. \# \. \# \# \. \#]
                   [\. \. \. \. \# \. \. \. \# \#]
                   [\. \. \. \# \# \. \. \# \# \.]
                   [\. \. \. \# \. \# \# \# \# \#]
                   [\. \# \. \# \# \# \# \. \# \.]
                   [\. \. \# \. \. \# \# \# \. \#]
                   [\. \. \# \# \. \# \. \. \# \.]),
                 :left `(\. \. \# \. \# \# \. \# \# \#),
                 :right `(\. \. \# \# \. \# \. \. \# \.),
                 :top `(\# \. \. \# \. \. \. \. \. \.),
                 :bottom `(\. \# \. \# \. \# \# \# \. \.),
                 :orientation 0,
                 :flipped false},
                :Tile2971
                {:grid
                 `([\. \. \# \. \# \. \. \. \. \#]
                   [\# \. \. \. \# \# \# \. \. \.]
                   [\# \. \# \. \# \# \# \. \. \.]
                   [\# \# \. \# \# \. \. \# \. \.]
                   [\. \# \# \# \# \# \. \. \# \#]
                   [\. \# \. \. \# \# \# \# \. \#]
                   [\# \. \. \# \. \# \. \. \# \.]
                   [\. \. \# \# \# \# \. \# \# \#]
                   [\. \. \# \. \# \. \# \# \# \.]
                   [\. \. \. \# \. \# \. \# \. \#]),
                 :left `(\# \. \. \. \. \# \. \# \. \.),
                 :right :Tile2729,
                 :top `(\. \# \# \# \. \. \# \. \. \.),
                 :bottom :Tile1489,
                 :orientation 0,
                 :flipped false},
                :Tile1489
                {:grid
                 `([\# \# \. \# \. \# \. \. \. \.]
                   [\. \. \# \# \. \. \. \# \. \.]
                   [\. \# \# \. \. \# \# \. \. \.]
                   [\. \. \# \. \. \. \# \. \. \.]
                   [\# \# \# \# \# \. \. \. \# \.]
                   [\# \. \. \# \. \# \. \# \. \#]
                   [\. \. \. \# \. \# \. \# \. \.]
                   [\# \# \. \# \. \. \. \# \# \.]
                   [\. \. \# \# \. \# \# \. \# \#]
                   [\# \# \# \. \# \# \. \# \. \.]),
                 :left `(\. \. \. \. \# \. \# \. \# \#),
                 :right `(\# \# \# \. \# \# \. \# \. \.),
                 :top :Tile2971,
                 :bottom `(\. \# \. \. \# \. \. \. \. \.),
                 :orientation 0,
                 :flipped true},
                :Tile1171
                {:grid
                 `([\# \# \# \# \. \. \. \# \# \.]
                   [\# \. \. \# \# \. \# \. \. \#]
                   [\# \# \. \# \. \. \# \. \# \.]
                   [\. \# \# \# \. \# \# \# \# \.]
                   [\. \. \# \# \# \. \# \# \# \#]
                   [\. \# \# \. \. \. \. \# \# \.]
                   [\. \# \. \. \. \# \# \# \# \.]
                   [\# \. \# \# \. \# \# \# \# \.]
                   [\# \# \# \# \. \. \# \. \. \.]
                   [\. \. \. \. \. \# \# \. \. \.]),
                 :left `(\. \# \# \. \. \. \# \# \# \#),
                 :right `(\. \. \. \. \. \# \# \. \. \.),
                 :top `(\# \# \# \. \. \. \. \# \# \.),
                 :bottom `(\. \. \. \. \. \# \. \. \# \.),
                 :orientation 0,
                 :flipped false},
                :Tile2473
                {:grid
                 `([\# \. \. \. \. \# \# \# \# \.]
                   [\# \. \. \# \. \# \# \. \. \.]
                   [\# \. \# \# \. \. \# \. \. \.]
                   [\# \# \# \# \# \# \. \# \. \#]
                   [\. \# \. \. \. \# \. \# \. \#]
                   [\. \# \# \# \# \# \# \# \# \#]
                   [\. \# \# \# \. \# \. \. \# \.]
                   [\# \# \# \# \# \# \# \# \. \#]
                   [\# \# \. \. \. \# \# \. \# \.]
                   [\. \. \# \# \# \. \# \. \# \.]),
                 :left `(\. \# \# \# \# \. \. \. \. \#),
                 :right `(\. \. \# \# \# \. \# \. \# \.),
                 :top `(\# \# \# \# \. \. \. \# \# \.),
                 :bottom `(\. \. \# \. \# \# \# \. \. \.),
                 :orientation 0,
                 :flipped false},
                :Tile1951
                {:grid
                 `([\# \. \# \# \. \. \. \# \# \.]
                   [\# \. \# \# \# \# \. \. \. \#]
                   [\. \. \. \. \. \# \. \. \# \#]
                   [\# \. \. \. \# \# \# \# \# \#]
                   [\. \# \# \. \# \. \. \. \. \#]
                   [\. \# \# \# \. \# \# \# \# \#]
                   [\# \# \# \. \# \# \. \# \# \.]
                   [\. \# \# \# \. \. \. \. \# \.]
                   [\. \. \# \. \# \. \. \# \. \#]
                   [\# \. \. \. \# \# \. \# \. \.]),
                 :left `(\. \# \# \. \. \. \# \# \. \#),
                 :right `(\# \. \. \. \# \# \. \# \. \.),
                 :top `(\# \# \. \# \. \. \# \. \. \#),
                 :bottom `(\. \# \. \. \# \# \# \# \# \.),
                 :orientation 0,
                 :flipped false},
                :Tile3079
                {:grid
                 `([\# \. \# \. \# \# \# \# \# \.]
                   [\. \# \. \. \# \# \# \# \# \#]
                   [\. \. \# \. \. \. \. \. \. \.]
                   [\# \# \# \# \# \# \. \. \. \.]
                   [\# \# \# \# \. \# \. \. \# \.]
                   [\. \# \. \. \. \# \. \# \# \.]
                   [\# \. \# \# \# \# \# \. \# \#]
                   [\. \. \# \. \# \# \# \. \. \.]
                   [\. \. \# \. \. \. \. \. \. \.]
                   [\. \. \# \. \# \# \# \. \. \.]),
                 :left `(\. \# \# \# \# \# \. \# \. \#),
                 :right `(\. \. \# \. \# \# \# \. \. \.),
                 :top `(\# \. \. \# \# \. \# \. \. \.),
                 :bottom `(\. \. \. \# \. \. \. \. \# \.),
                 :orientation 0,
                 :flipped false},
                :Tile2729
                {:grid
                 `([\. \. \. \# \. \# \. \# \. \#]
                   [\# \# \# \# \. \# \. \. \. \.]
                   [\. \. \# \. \# \. \. \. \. \.]
                   [\. \. \. \. \# \. \. \# \. \#]
                   [\. \# \# \. \. \# \# \. \# \.]
                   [\. \# \. \# \# \# \# \. \. \.]
                   [\# \# \# \# \. \# \. \# \. \.]
                   [\# \# \. \# \# \# \# \. \. \.]
                   [\# \# \. \. \# \. \# \# \. \.]
                   [\# \. \# \# \. \. \. \# \# \.]),
                 :left :Tile2971,
                 :right `(\# \. \# \# \. \. \. \# \# \.),
                 :top `(\. \# \. \. \. \. \# \# \# \#),
                 :bottom `(\. \. \. \. \. \. \# \. \. \#),
                 :orientation 2,
                 :flipped true}} #_tiles
       tiles_to_check #_`(:Tile2729 :Tile1489) (vector (first corners))]
  (let [res (for [t tiles_to_check
                  :let [temp (->> (for [sides [:top :right :bottom :left]
                                        :let [border (sides (t tiles))]
                                        :when (not (keyword? border))]
                                    (for [[tile val] (dissoc tiles t)
                                          :let [unflipped (= 1 (count (remove nil? (map #{border} (get_borders tile)))))
                                                flipped (= 1 (count (remove nil? (map #{(reverse border)} (get_borders tile)))))]
                                          :when (or unflipped flipped)
                                          :let [side (for [s [:top :right :bottom :left]
                                                           :when (or (= (s val) border) (= (s val) (reverse border)))]
                                                       s)]
                                          :when (not= 0 (count side))
                                          :let [[new_tile_orientation new_tile_flipped] (new_tile_orientation sides (:flipped (t tiles)) (:orientation (t tiles)) flipped (first side))]]
                                      [{tile (-> (tile tiles)
                                                 (assoc :orientation new_tile_orientation)
                                                 (assoc :flipped new_tile_flipped)
                                                 (assoc (first side) t))}
                                       {sides tile}]))
                                  (remove #(= (count %) 0))
                                  (map first))
                        new_tiles (into {} (map first temp))
                        old_tile {t (merge (t tiles) (apply merge (map second temp)))}]] ; [match current_tile_side current_tile_orientation connected_tile_side]

              [new_tiles old_tile])]
    #_(merge tiles (apply merge (map first res)) (apply deep-merge (map second res)))
    #_(keys (apply merge (map first res)))
    #_#_(merge tiles (apply merge (map first res)) (apply deep-merge (map second res)))
      (apply deep-merge (map second res))
    res
    #_(merge tiles (apply merge (map first res)) (if (nil? (apply deep-merge (map second res)))
                                                   (first (map second res))
                                                   (apply deep-merge (map second res))))
    (if (= 0 (count res))
      tiles
      (recur (merge tiles (apply merge (map first res)) (if (nil? (apply deep-merge (map second res)))
                                                          (first (map second res))
                                                          (apply deep-merge (map second res)))) (keys (apply merge (map first res)))))))


(for [sides [:top :right :bottom :left]
      :let [border (sides (:Tile2729 tiles))]
      :when (not (keyword? border))]
  (for [[tile val] (dissoc tiles :Tile2729)
        :let [unflipped (= 1 (count (remove nil? (map #{border} (get_borders tile)))))
              flipped (= 1 (count (remove nil? (map #{(reverse border)} (get_borders tile)))))]
        :when (or unflipped flipped)
        :let [side (for [s [:top :right :bottom :left]
                         :when (or (= (s val) border) (= (s val) (reverse border)))]
                     s)]
        :when (not= 0 (count side))
        :let [[new_tile_orientation new_tile_flipped] (new_tile_orientation sides (:flipped (:Tile2729 tiles)) (:orientation (:Tile2729 tiles)) flipped (first side))]]
    [{tile (-> (tile tiles)
               (assoc :orientation new_tile_orientation)
               (assoc :flipped new_tile_flipped)
               (assoc (first side) :Tile2729))}
     {sides tile}]))

corners