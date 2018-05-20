(ns reward-system.handler-test
  (:require [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [reward-system.data :as data]
            [reward-system.handler :refer :all]))

(fact "On main route"
  (let [response (app (mock/request :get "/"))]
    (:status response) => 200
    (:body response) => "Reward System"))

(fact "Ranking with no insertion"
	(reset! data/graph {})
  (let [response (app (mock/request :get "/ranking"))]
    (:status response) => 200
    (:body response) => "Reward system is empty"))

(fact "Not found route"
  (let [response (app (mock/request :get "/invalid"))]
    (:status response) => 404
(:body response) => "Not Found"))

(fact "Insert file endpoint"
    (let [response
          (app
            (mock/content-type (mock/body
                                 (mock/request :post "/insert/file")
                                 (json/write-str { :path "resources/sample-in" }))
                                 "application/json"))]
      (:status response) => 200
      (:body response) => "resources/sample-in inserted!\n"))

(fact "Insert endpoint"
    (let [response
          (app
            (mock/content-type (mock/body
                                 (mock/request :post "/insert")
                                 (json/write-str { :inviter "1" :invited "2"}))
                                 "application/json"))]
      (:status response) => 200
      (:body response) => "1 invited 2!\n"))


(fact "Insert file endpoint and get ranking"
		(reset! data/graph {})
		(reset! data/inviteds (set []))
		(reset! data/confirmed-inviteds (set []))
    (reset! data/ranking {})
    (let [insert-response
          (app
            (mock/content-type (mock/body
                                 (mock/request :post "/insert/file")
                                 (json/write-str { :path "resources/sample-in" }))
                               	 "application/json"))
          ranking-response (app (mock/request :get "/ranking"))]
      (:status insert-response) => 200
      (:status ranking-response) => 200
      (:body insert-response) => "resources/sample-in inserted!\n"
    	(:body ranking-response) => "{\"1\":2.5,\"3\":1.0,\"6\":0,\"5\":0,\"4\":0.0,\"2\":0}"))

(fact "Insert endpoint and get ranking"
    (reset! data/graph {})
    (reset! data/inviteds (set []))
    (reset! data/confirmed-inviteds (set []))
    (reset! data/ranking {})
    (let [insert-response
          (app
            (mock/content-type (mock/body
                                 (mock/request :post "/insert")
                                 (json/write-str { :inviter "1" :invited "2"}))
                                 "application/json"))
          ranking-response (app (mock/request :get "/ranking"))]
      (:status insert-response) => 200
      (:status ranking-response) => 200
      (:body insert-response) => "1 invited 2!\n"
      (:body ranking-response) => "{\"2\":0,\"1\":0.0}"))