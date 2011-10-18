(ns grid.card
  (:require (grid [svg :as svg])))

(defn point-in-quad [point quad]
  (and (>= (:x point)
           (:x quad))
       (>= (:y point)
           (:y quad))
       (<= (:x point)
           (+ (:x quad)
              (:size quad)))
       (<= (:y point)
           (+ (:y quad)
              (:size quad)))))

(defn points-in-quad [points quad]
  (filter (fn [point] (point-in-quad point quad))
          points))

(defn split-quad [quad]
  (let [size (/ (:size quad)
                2)
        x1 (:x quad)
        y1 (:y quad)
        x2 (+ size
              (:x quad))
        y2 (+ size
              (:y quad))]
    [{:x x1
      :y y1
      :size size}
     {:x x2
      :y y1
      :size size}
     {:x x1
      :y y2
      :size size}
     {:x x2
      :y y2
      :size size}]))

(defn create-quad-tree [points parent-quad]
  (if (> (count (points-in-quad points parent-quad))
         1)
    (assoc parent-quad
      :children (map (fn [child-quad] (create-quad-tree points child-quad))
                     (split-quad parent-quad)))
    parent-quad))

(defn draw-quad-tree [root-quad]
  (concat [(svg/make-rectangle (float (:x root-quad))
                               (float (:y root-quad))
                               (float (:size root-quad))
                               (float (:size root-quad)))]
          (if (:children root-quad)
            []
            [(svg/make-ellipse (+ (float (:x root-quad))
                                  (/ (float (:size root-quad))
                                     2))
                               (+ (float (:y root-quad))
                                  (/ (float (:size root-quad))
                                     2))
                               (float (:size root-quad))
                               (float (:size root-quad)))])
          (flatten (map draw-quad-tree (:children root-quad)))))

(defn draw-points [points]
  (map (fn [point] (svg/make-rectangle (:x point) (:y point) 3 3))
       points))


(defn generate-points []
  (concat (map #(hash-map :x 10 :y %) (range 10 390 19))
          (map #(hash-map :x % :y 310) (range 10 390 19))))

(defn generate-points-s []
  (concat (map #(hash-map :x (+ (* (Math/sin (* (/ % 360)
                                                (* 3.141
                                                   2.0)))
                                   180)
                                230)
                          :y (+ %
                                150))
               (range 10 390 5))))

(svg/write-svg-file "card.svg"
                    (concat (draw-quad-tree (create-quad-tree (generate-points-s) {:x 0 :y 0 :size 400}))
;                     (draw-points (generate-points-s))
                            ))




