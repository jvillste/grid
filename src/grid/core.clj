(ns grid.core
  (:require [clojure.xml :as xml]))


(defn make-line [x1 y1 x2 y2 color width]
  {:tag :path
   :attrs {:d (str "M " x1 "," y1 " " x2 "," y2)
           :style (str "fill:none;stroke:#" color ";stroke-width:" width ";stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:4;stroke-opacity:1;stroke-dasharray:none")}})

(defn make-svg [content]
  {:tag :svg
   :attrs {:width "100%"
           :height "100%"
           :version "1.1"
           :xmlns "http://www.w3.org/2000/svg"}
   :content content})


(defn make-grid [left-margin top-margin width height]
  (let [color "aaaaaa"
        line-width 1
        square-size 10]
    (concat (for [x (range (+ 1 width))]
              (make-line (+ left-margin (* x square-size))
                         top-margin
                         (+ left-margin (* x square-size))
                         (+ top-margin (* height square-size))
                         color
                         line-width))
            (for [y (range (+ 1 height))]
              (make-line left-margin
                         (+ top-margin (* y square-size))
                         (+ left-margin (* width square-size))
                         (+ top-margin (* y square-size))
                         color
                         line-width)))))

(def content
  (concat (make-grid 50 50 125 21)
          (make-grid 50 350 60 21)
          (make-grid 50 650 51 21)))

(spit "squares.svg" (with-out-str (xml/emit (make-svg content)) ))