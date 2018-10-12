(ns fuber.helpers)

;;example cab info
(def example-cab {:cab-id "some-uuid"
                  :location {:latitude 50
                             :longitude 70}
                  :is-pink true})


;;example user info
(def example-user {:user-id "some-uuid"
                   :location {:latitude 40
                              :longitude 65}
                   :is-hipster false})


(defn new-uuid
  "generate a new uuid"
  []
  (str (java.util.UUID/randomUUID)))


;; Generating Data for Testing

(defn generate-random-cab
  "generate a random cab"
  []
  {:cab-id (new-uuid)
   :location {:latitude (- (rand 180) 90)
              :longitude (- (rand 360) 180)}
   :is-pink (< (rand-int 2) 0.5)})

(defn generate-random-cabs
  "generate n cabs with random locations"
  [n]
  (repeatedly n #(generate-random-cab)))

(defn generate-random-user
  "generate a random user"
  []
  {:user-id (new-uuid)
   :location {:latitude (- (rand 180) 90)
              :longitude (- (rand 360) 180)}
   :is-hipster (< (rand-int 2) 0.5)})

;; Distance Stuff

(defn find-distance
  "returns distance between 2 locations"
  [location1 location2]
  (let [{lat1 :latitude lon1 :longitude} location1
        {lat2 :latitude lon2 :longitude} location2]
    (->> [(- lat2 lat1) (- lon2 lon1)]
         (map #(* % %))
         (reduce +)
         Math/sqrt)))


(defn find-distance-with-cab
  "returns distance between user and cab along with cab details"
  [user cab]
  {:cab cab
   :distance (find-distance (:location user)
                            (:location cab))})


;;Finding the Nearest Cab

(defn filter-for-pink-cabs
  "removes non-pink cabs from list if user is hipster"
  [is-hipster list-of-cabs]
  (if is-hipster
    (filter :is-pink list-of-cabs)
    list-of-cabs))

(defn find-nearest-cab
  "find the nearest cab to user.If none available return nil"
  [user list-of-cabs]
  (let [select-cab  (fn [x] (if (empty? x)
                              nil
                              (->> x
                                   (apply min-key :distance)
                                   :cab)))]
    (->> list-of-cabs
         (filter-for-pink-cabs (:is-hipster user))
         (map #(find-distance-with-cab user %))
         select-cab)))


;;assuming 1-unit of distance as 1-km
(defn calculate-total-amount
  "calculate total amount for the ride"
  [ride end-location end-time]
  (let [start-location (get-in ride [:user :location])
        start-time (:start-time ride)
        is-hipster (get-in ride [:user :is-hipster])
        ride-time-min (/ (- end-time start-time)
                         60)
        ride-distance-km (find-distance start-location end-location)]
    {:total-distance ride-distance-km
     :total-time ride-time-min
     :total-amount (+ (if is-hipster 5 0)
                      ride-time-min
                      (* ride-distance-km 2))}))
