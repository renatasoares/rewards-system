(ns reward-system.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.tools.logging :as log]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [response]]
            [reward-system.adapter :as adapter]
            [reward-system.data :as data]))

(defroutes app-routes
  (GET "/" [] "Reward System")
  (GET  "/ranking" [] (if (empty? @data/graph)
                        "Reward system is empty"
                        (do (adapter/run)
                          (adapter/show-ranking @data/ranking))))
  (POST "/insert/file" {request :body}
    (let [file-path (get request "path")]
      (adapter/read-file file-path data/insert!)
      (str file-path " inserted!\n")))
  (POST "/insert" {request :body}
    (let [inviter (get request "inviter")
          invited (get request "invited")]
      (data/insert! inviter invited)
      (str inviter " invited " invited "!\n")))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
  (middleware/wrap-json-response)))