(ns fuber.handler
  (:require [fuber.model :as model]
            [fuber.helpers :as helpers]))

(defn handle-redirect [path]
  {:status 302
   :headers {"Location" path}
   :body ""})

(defn home-page
  [req]
  (handle-redirect "/cabs"))

(defn handle-404-error
  [error]
  {:status 404
   :headers {}
   :body error})

(defn handle-400-error
  []
  {:status 400
   :headers {}
   :body "Invalid Body"})

(defn handle-json-reply
  [body]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body body})

(defn get-all-cabs
  "returns a list of all available cabs"
  [req]
  (handle-json-reply {:cabs @model/list-of-available-cabs}))

(defn book-a-ride
  "books a ride for user and returns the booked-cab"
  [req]
  (if (helpers/is-valid-body? (:body req))
    (let [user (get-in req [:body :user])
          nearest-cab (helpers/find-nearest-cab user
                                                @model/list-of-available-cabs)]
      (cond (model/is-user-riding? user) (handle-404-error "You can't book 2 cabs at the same time")
            (nil? nearest-cab) (handle-404-error "Sorry.No cabs are currently available")
            :else (do
                    (model/remove-cab-from-available! nearest-cab)
                    (model/add-cab-to-active-rides! user nearest-cab)
                    (handle-json-reply {:cab nearest-cab}))))
    (handle-400-error)))

(defn end-ride
  "end ride of user and return the total amount to pay"
  [req]
  (if (helpers/is-valid-body? (:body req))
    (let [user (get-in req [:body :user])
          ride (model/find-ride-object user)
          cab (:cab ride)
          end-location (:location user)
          cab-with-updated-location (assoc cab :location end-location)
          end-time (model/current-time-stamp)]
      (if (nil? ride)
        (handle-404-error  "No active ride to end")
        (do (model/remove-ride-from-active! ride)
            (model/add-cab-to-available! cab-with-updated-location)
            (handle-json-reply (helpers/calculate-total-amount ride end-location end-time)))))
    (handle-400-error)))
