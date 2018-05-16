(ns reward-system.logic-test
  (:require [midje.sweet :refer :all]
            [reward-system.logic :refer :all]))

(fact "Path to count points of graph starting at 1"
	(let [graph {:1 '(:3 :2), 
							 :3 '(:4), 
							 :4 '(:6 :5)}]
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(2 0.5 0.0 0.0 0.0 0.0))

(fact "Path to count points of graph starting at 3"
	(let [graph {:1 '(:3 :2), 
							 :3 '(:4), 
							 :4 '(:6 :5)}]
		(aux-bfs graph
						(hash-map :3 0)
						#{:3} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :3))) => '(1 0.0 0.0 0.0))


(fact "Path to count points of graph starting at 1"
	(let [graph {:1 '(:2 :8),
							 :2 '(:3),
							 :3 '(:4),
							 :4 '(:5),
							 :5 '(:6 :7),
							 :8 '(:9),
							 :9 '(:10 :11),
							 :11 '(:13 :12)}]
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(2 0.5 0.5 0.25 0.25 0.125 0.0 0.0 0.0 0.0 0.0 0.0 0.0))

(fact "Path to count points of graph starting at 4"
	(let [graph {:1 '(:2 :8),
							 :2 '(:3),
							 :3 '(:4),
							 :4 '(:5),
							 :5 '(:6 :7),
							 :8 '(:9),
							 :9 '(:10 :11),
							 :11 '(:13 :12)}]
		(aux-bfs graph
						(hash-map :4 0)
						#{:4} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :4))) => '(1 0.0 0.0 0.0))

(fact "Path to count points of empty graph"
	(let [graph {}]
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(0))

(fact "Path to count points of only directed invited graph"
	(let [graph {:1 '(:2 :3 :4)}]
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(3 0.0 0.0 0.0))