(ns grid.test.card
  (:use clojure.test)
  (:require [grid.card :as card]))


(deftest point-in-quad-test
  (is (= false
         (card/point-in-quad {:x 10 :y 100}
                             {:x 10 :y 10 :size 10}))
      (= true
         (card/point-in-quad {:x 10 :y 10}
                             {:x 10 :y 10 :size 10}))))

(deftest create-quad-tree-test
  (is (= {:children '({:x 0, :y 0, :size 100}
                      {:x 100, :y 0, :size 100}
                      {:x 0, :y 100, :size 100}
                      {:x 100, :y 100, :size 100})
          :y 0, :x 0, :size 200}
         (card/create-quad-tree [{:x 10 :y 100}
                                 {:x 120 :y 100}]
                                {:x 0 :y 0 :size 200}))))

(deftest draw-quad-tree-test
  (is (= '({:tag :rect, :attrs {:cx 0.0, :cy 0.0, :width 200.0, :height 200.0, :style "fill:none;stroke:black;stroke-width:2"}}
           {:tag :rect, :attrs {:cx 0.0, :cy 0.0, :width 200.0, :height 200.0, :style "fill:none;stroke:black;stroke-width:2"}})
         (card/draw-quad-tree {:x 0 :y 0 :size 200 :children [{:x 0 :y 0 :size 200}
                                                              {:x 0 :y 0 :size 100}]}))))

(run-tests 'grid.test.card)
