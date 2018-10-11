(ns fuber.helpers)

;;example cab info
(def example-cab {:id "some-uuid"
                  :location {:latitude 50
                             :longitude 70}
                  :is-pink true})


;;example user info
(def example-user {:user-id "some-uuid"
                   :location {:latitude 40
                              :longitude 65}
                   :is-hipster false})

;; Generating Data for Testing

;;generate a random cab
(defn generate-random-cab
  []
  {:id (str (java.util.UUID/randomUUID))
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
  {:user-id (str (java.util.UUID/randomUUID))
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


(defn find-distance-with-id
  "returns distance between user and cab along with cab-id"
  [user cab]
  {:id (:id cab)
   :distance (find-distance (:location user)
                            (:location cab))})

