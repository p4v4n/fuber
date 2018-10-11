(ns fuber.handler
  (:require [fuber.model :as model]
            [fuber.helpers :as helpers]))


(defn handle-error
  [error]
  {:status 404
   :headers {}
   :body error})

(defn get-all-cabs
  "returns a list of all available cabs"
  [req]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body  {:cabs @model/list-of-available-cabs}})

(defn book-a-ride
  "books a ride for user and returns the booked-cab"
  [req]
  (let [user (get-in req [:body :user])
        nearest-cab (helpers/find-nearest-cab user
                                              @model/list-of-available-cabs)]
    (cond (model/is-user-riding? user)  (handle-error "You can't book 2 cabs at the same time")
          (nil? nearest-cab)  (handle-error "Sorry.No cabs are currently available")
          :else (do
                  (model/remove-cab-from-available! nearest-cab)
                  (model/add-cab-to-active-rides! user nearest-cab)
                  {:status 200
                   :headers {"Content-Type" "text/json"}
                   :body {:cab nearest-cab}}))))

(defn end-ride
  "end ride of user and return the total amount to pay"
  [req]
  (let [user (get-in req [:body :user])
        ride (model/find-ride-object user)
        cab (:cab ride)
        end-location (:location user)
        cab-with-updated-location (assoc cab :location end-location)
        end-time (model/current-time-stamp)]
    (model/remove-ride-from-active! ride)
    (model/add-cab-to-available! cab-with-updated-location)
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body (helpers/calculate-total-amount ride end-location end-time)}))
