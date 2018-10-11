(ns fuber.core-test
  (:require [clojure.test :refer :all]
            [fuber.core :refer :all]
            [fuber.helpers :refer :all]))

(deftest distance-tests
  (testing "testing distances between 2 locations"
    (is (= 0.0 (find-distance  {:latitude 50
                                :longitude 40}
                               {:latitude 50
                                :longitude 40})))
    (is (= 5.0 (find-distance  {:latitude 10
                                :longitude 20}
                               {:latitude 13
                                :longitude 16})))
    (is (= 13.0 (find-distance  {:latitude 72
                                 :longitude -90}
                                {:latitude 84
                                 :longitude -95})))))
