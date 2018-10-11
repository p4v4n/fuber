(ns fuber.model
  (:require
   [fuber.helpers :as helpers]))

(def list-of-available-cabs (atom []))

(def active-rides (atom '()))


(defn current-time-stamp
  []
  (quot (System/currentTimeMillis) 1000))

;;Example Data for Active-Ride
(def example-active-ride   {:ride-id {:cab {:cab-id "some-uuid"
                                            :location {:latitude 50
                                                       :longitude 70}
                                            :is-pink true}
                                      :user {:user-id "some-uuid"
                                             :location {:latitude 50
                                                        :longitude 70}
                                             :is-hipster true}
                                      :start-time "unix-timestamp"}})

