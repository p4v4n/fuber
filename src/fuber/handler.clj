(ns fuber.handler
  (:require [fuber.model :as model]
            [fuber.helpers :as helpers]))


(defn get-all-cabs
  "returns a list of all available cabs"
  [req]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body  {:cabs @model/list-of-available-cabs}})

(defn book-a-ride
  [req]
  {})

(defn end-ride
  [req]
  {})
