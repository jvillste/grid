(ns grid.svg
  (:require [clojure.xml :as xml]))

(defn make-line [x1 y1 x2 y2 color width]
  {:tag :path
   :attrs {:d (str "M " x1 "," y1 " " x2 "," y2)
           :style (str "fill:none;stroke:#" color ";stroke-width:" width ";stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:4;stroke-opacity:1;stroke-dasharray:none")}})

(defn make-ellipse [x y width height]
  {:tag :ellipse
   :attrs {:cx x
           :cy y
           :rx (/ width
                  2)
           :ry (/ height
                  2)
           :style "fill:none;stroke:black;stroke-width:2"}})


(defn make-rectangle [x y width height]
  {:tag :rect
   :attrs {:x x
           :y y
           :width width
           :height height
           :style "fill:none;stroke:black;stroke-width:2"}})

(defn make-svg [content]
  {:tag :svg
   :attrs {:width "100%"
           :height "100%"
           :version "1.1"
           :xmlns "http://www.w3.org/2000/svg"}
   :content content})

(defn write-svg-file [file-name content]
  (spit file-name (with-out-str (xml/emit (make-svg content)) )))
