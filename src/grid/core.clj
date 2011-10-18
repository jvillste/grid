(ns grid.core
  (:require (grid [svg :as svg])))


(defn make-grid [left-margin top-margin width height]
  (let [color "aaaaaa"
        line-width 1
        square-size 10]
    (concat (for [x (range (+ 1 width))]
              (svg/make-line (+ left-margin (* x square-size))
                             top-margin
                             (+ left-margin (* x square-size))
                             (+ top-margin (* height square-size))
                             color
                             line-width))
            (for [y (range (+ 1 height))]
              (svg/make-line left-margin
                             (+ top-margin (* y square-size))
                             (+ left-margin (* width square-size))
                             (+ top-margin (* y square-size))
                             color
                             line-width)))))

(svg/write-svg-file "squares.svg"
                    (concat (make-grid 50 50 125 21)
                            (make-grid 50 350 60 21)
                            (make-grid 50 650 51 21)))
