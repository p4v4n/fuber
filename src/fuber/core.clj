(ns fuber.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn hello [req]
  {:status 200
   :body "Hello!"
   :headers {}})

(defn -main
  [port]
  (jetty/run-jetty (wrap-reload #'hello)
                   {:port (Integer. port)}))
