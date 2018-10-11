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
