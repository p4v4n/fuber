(ns fuber.model
  (:require
   [fuber.helpers :as helpers]))

(def list-of-available-cabs (atom []))

(def active-rides (atom '()))


(defn current-time-stamp
  []
  (quot (System/currentTimeMillis) 1000))

;;Example Data for Active-Ride
(def example-active-ride   {:ride-id "some-uuid"
                            :cab {:cab-id "some-uuid"
                                  :location {:latitude 50
                                             :longitude 70}
                                  :is-pink true}
                            :user {:user-id "some-uuid"
                                   :location {:latitude 50
                                              :longitude 70}
                                   :is-hipster true}
                            :start-time "unix-timestamp"})


(defn is-user-riding?
  "returns true if user is currently riding"
  [user]
  (->> @active-rides
       (map #(get-in % [:user :user-id]))
       (filter #(= % (:user-id user)))
       seq))

(defn find-ride-object
  "return the ride map associated with the user"
  [user]
  (->> @active-rides
       (filter #(= (:user-id user) (get-in % [:user :user-id])))
       first))

;;Mutating Stuff

(defn populate-initial-cab-data!
  "Add n cabs to populate list-of-cabs"
  [n]
  (reset! list-of-available-cabs (helpers/generate-random-cabs n)))

(defn remove-cab-from-available!
  "revome cab from list of avaliable cabs"
  [cab]
  (->> @list-of-available-cabs
       (remove #(= (:cab-id cab) (:cab-id %)))
       (reset! list-of-available-cabs)))

(defn add-cab-to-active-rides!
  "add cab-user pair to active-rides list"
  [user cab]
  (swap! active-rides conj
         {:ride-id (helpers/new-uuid)
          :user user
          :cab cab
          :start-time (current-time-stamp)}))

(defn remove-ride-from-active!
  "remove the ride object from active rides"
  [ride]
  (->> @active-rides
       (remove #(= (:ride-id ride) (:ride-id %)))
       (reset! active-rides)))
