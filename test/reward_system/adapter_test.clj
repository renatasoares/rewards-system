(ns reward-system.adapter-test
  (:require 
  	 				[midje.sweet :refer :all]
            [reward-system.adapter :refer :all]))

(fact "Format response"
	(format-response (fn [x y] (+ (read-string x) (read-string y))) "10 20") => 30)

(fact "Format response only consider the first two parameters"
	(format-response (fn [x y] (- (read-string x) (read-string y))) "25 10 12") => 15)

(fact "Read file and apply function"
	(read-file "resources/sample-in" str) => (list "12" "13" "34" "24" "45" "46"))

(fact "1 rewards points of graph"
	(let [graph {:1 [:3 :2], 
							 :3 [:4], 
							 :4 [:6 :5]}]
		(bfs graph :1)) => 2.5)

(fact "3 rewards points of graph"
	(let [graph {:1 [:3 :2], 
							 :3 [:4], 
							 :4 [:6 :5]}]
		(bfs graph :3)) => 1.0)

(fact "1 rewards points of graph"
	(let [graph {:1 [:3 :2],
	  					 :3 [:4]}]
		(bfs graph :1)) => 2.0)


(fact "1 rewards points of graph"

	(let [graph {:1 [:2 :8],
							 :2 [:3],
							 :3 [:4],
							 :4 [:5],
							 :5 [:6 :7]
							 :8 [:9]
							 :9 [:10 :11]
							 :11 [:13 :12]}]
		(bfs graph :1)) => 3.625)

(fact "1 rewards points of graph"

	(let [graph {:1 [:2 :3 :4],
							 :2 [:5 :6 :7],
							 :3 [:10 :11 :12],
							 :7 [:8 :9]
							 :12 [:13 :14 :15]
							 :15 [:16]}]
		(bfs graph :1)) => 4.25)

(fact "1 rewards points of graph"

	(let [graph {:1 [:2 :3 :4],
							 :2 [:5 :6 :7],
							 :3 [:10 :11 :12],
							 :7 [:8 :9]
							 :12 [:13 :14 :15]
							 :15 [:16]}]
		(bfs graph :3)) => 3.5)

(fact "1 rewards points of graph"

	(let [graph {:1 [:2 :8],
							 :2 [:3],
							 :3 [:4],
							 :4 [:5],
							 :5 [:6 :7]
							 :8 [:9]
							 :9 [:10 :11]
							 :11 [:13 :12]}]
		(bfs graph :8)) => 1.5)


(fact "1 rewards points of empty graph"
	(let [graph {}]
		(bfs graph :1)) => 0)

(fact "1 rewards points of only directed invited graph"
	(let [graph {:1 [:2 :3 :4]}]
		(bfs graph :1)) => 3.0)

(fact "2 rewards points of graph"
	(let [graph {:1 [:3 :2], 
							 :3 [:4], 
							 :4 [:6 :5]}]
		(bfs graph :2)) => 0)

