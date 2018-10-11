(ns fuber.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found]]
            [fuber.handler :refer :all]
            [fuber.model :as model]))

(defn hello
  [req]
  {:status 200
   :body "Hello!"
   :headers {}})

(defroutes routes
  (GET "/" [] hello)
  (GET "/cabs" [] get-all-cabs)
  (POST "/ride" [] book-a-ride)
  (POST "/end" [] end-ride)
  (not-found "Page not found."))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "Server"] "fuber-server")))

(def app
  (-> routes
      wrap-server
      wrap-content-type
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-json-response))

(defn -main
  [port]
  (model/populate-initial-cab-data! 10)
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))
