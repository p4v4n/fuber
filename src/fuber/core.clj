(ns fuber.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]))

(defn hello
  [req]
  {:status 200
   :body "Hello!"
   :headers {}})

(defroutes routes
  (GET "/" [] hello)
  (not-found "Page not found."))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "Server"] "fuber-server")))

(def app
  (wrap-server
   (wrap-params routes)))

(defn -main
  [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))
