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

;;generate a random cab
(defn generate-random-cab
  []
  {:cab-id (new-uuid)
   :location {:latitude (- (rand 180) 90)
              :longitude (- (rand 360) 180)}
   :is-pink (< (rand-int 2) 0.5)})

;;generate n random cabs
(defn generate-random-cabs
  [n]
  (repeatedly n #(generate-random-cab)))

;;generate a random user
(defn generate-random-user
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
  "find the nearest cab to user"
  [user list-of-cabs]
  (->> list-of-cabs
       (filter-for-pink-cabs (:is-hipster user))
       (map #(find-distance-with-cab user %))
       (apply min-key :distance)
       :cab))
