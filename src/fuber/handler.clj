(ns fuber.handler
  (:require [fuber.model :as model]
            [fuber.helpers :as helpers]))

(defn populate-initial-cab-data!
  "Add 10 cabs to populate list-of-cabs"
  []
  (reset! model/list-of-cabs (helpers/generate-random-cabs 10)))


(defn get-all-cabs
  [req]
  {})

(defn book-a-ride
  [req]
  {})

(defn end-ride
  [req]
  {})
