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

(deftest filter-pink-cabs
  (testing "testing filtering of pink cabs"
    (is (= '() (filter-for-pink-cabs true '({:is-pink false} {:is-pink false}))))
    (is (= '({:is-pink false} {:is-pink true}) (filter-for-pink-cabs false '({:is-pink false} {:is-pink true}))))
    (is (=  '({:a 1 :is-pink true}) (filter-for-pink-cabs true '({:a 1 :is-pink true} {:is-pink false}))))))

(deftest total-amount
  (testing "testing calculation of total ride amount"
    (is (= {:total-distance 13.0, :total-time 1, :total-amount 32.0}
           (calculate-total-amount {:start-time 0
                                    :user {:is-hipster true
                                           :location {:latitude 0
                                                      :longitude 0}}} {:latitude 5
                                                                       :longitude 12} 60)))
    (is (= {:total-distance 0.0, :total-time 0, :total-amount 0.0}
           (calculate-total-amount {:start-time 0
                                    :user {:is-hipster false
                                           :location {:latitude 0
                                                      :longitude 0}}} {:latitude 0
                                                                       :longitude 0} 0)))))

(deftest nearest-cab-tests
  (testing "testing nearest cab"
    (is (= {:cab-id "0773ad28-d09c-4f9e-a56e-6e34031f4ce2"
            :location {:latitude -68.39101950550773
                       :longitude -84.20921537932544}
            :is-pink true}
           (find-nearest-cab {:user-id "b4aaacfe-d68a-4193-b2cd-358e0fcb6bd3"
                              :location {:latitude -28.51021355227215
                                         :longitude -81.73216638874958}
                              :is-hipster true}
                             '({:cab-id "0773ad28-d09c-4f9e-a56e-6e34031f4ce2"
                                :location {:latitude -68.39101950550773
                                           :longitude -84.20921537932544}
                                :is-pink true}
                               {:cab-id "86843804-e90b-40a7-a1c1-adc3b245ef17"
                                :location {:latitude 14.331750709085654
                                           :longitude -56.723501178613986}
                                :is-pink false}
                               {:cab-id "89d7d63f-677f-46f8-8101-613e2c90c818"
                                :location {:latitude -9.07877347877239
                                           :longitude 177.63810034669092}
                                :is-pink false}
                               {:cab-id "b32adc99-8c7e-4705-9411-59b3e94599b1"
                                :location {:latitude -51.241657284914474
                                           :longitude 106.97828376283013}
                                :is-pink false}
                               {:cab-id "0ef3b3eb-0a17-4d48-942b-3f4b9c8b6cde"
                                :location {:latitude -32.316457129458165
                                           :longitude 15.134705793279494}
                                :is-pink true}))))))


(deftest valid-body
  (testing "if body of post requests are valid")
  (is (= false (is-valid-body? nil)))
  (is (= false (is-valid-body? {:user {}})))
  (is (= false (is-valid-body? {:user {:user-id "883131a8-61a2-4daa-8441-d7057510c126"
                                       :location {:latitude 83.14032406010887
                                                  :longitude "-135.50348319303532"}
                                       :is-hipster true}})))
  (is (= true (is-valid-body?  {:user {:user-id "883131a8-61a2-4daa-8441-d7057510c126"
                                       :location {:latitude 83.14032406010887
                                                  :longitude -135.50348319303532}
                                       :is-hipster true}})))
  (is (= false (is-valid-body?  {:user {:user-id "883131a8-61a2-4daa-8441-d7057510c126"
                                        :location {:latitude 93.14032406010887
                                                   :longitude -135.50348319303532}
                                        :is-hipster true}}))))

(deftest valid-body-and-generate-random-user
  (testing "is-valid-body and generate-random-user together"
    (is (= true (is-valid-body? {:user (generate-random-user)})))
    (is (= true (is-valid-body? {:user (generate-random-user)})))
    (is (= true (is-valid-body? {:user (generate-random-user)})))))
