(ns reward-system.adapter-test
  (:require [midje.sweet :refer :all]
  					[reward-system.data :as data]
            [reward-system.adapter :refer :all]))

(fact "Format response"
	(format-response (fn [x y] (+ (read-string x) (read-string y))) "10 20") => 30)

(fact "Format response only consider the first two parameters"
	(format-response (fn [x y] (- (read-string x) (read-string y))) "25 10 12") => 15)

(fact "Read file and apply function"
	(read-file "resources/sample-in" str) => '("12" "13" "34" "24" "45" "46"))

(fact "Show ranking should order by value and convert to JSON"
	(let [expected-ranking "{\"4\":5,\"2\":2.01,\"3\":2,\"1\":1.5,\"6\":1.2,\"5\":1}"]
		(show-ranking {:1 1.5 :2 2.01 :3 2 :4 5 :5 1 :6 1.2}) => expected-ranking))

(fact "bfs should insert result to ranking"
	(let [graph {:1 '(:3 :2), 
							 :3 '(:4), 
							 :4 '(:6 :5)}]
		(reset! data/confirmed-inviteds (set [1 3 4]))
		(reset! data/ranking {})
		(get (bfs graph :1) :1)
		(get (bfs graph :2) :2))
	@data/ranking => {:1 1.5 :2 0})
