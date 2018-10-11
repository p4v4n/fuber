(ns fuber.model
  (:require
   [fuber.helpers :as helpers]))

(def list-of-available-cabs (atom []))

(def active-rides (atom '()))


(defn current-time-stamp
  []
  (quot (System/currentTimeMillis) 1000))
