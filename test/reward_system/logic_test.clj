(ns reward-system.logic-test
  (:require [midje.sweet :refer :all]
  					[reward-system.data :as data :only confirm-inviteds]
            [reward-system.logic :refer :all]))

(fact "Treat levels test" 
	(let [levels {:1 0 :2 1} 
				vertice :2
				inviteds [:3 :4]
				expected-response {:1 0 :2 1 :3 2 :4 2}]
	(treat-levels levels vertice inviteds) => expected-response))

(fact "Path to count points of graph starting at 1 with 1 3 and 4 as confirmed inviteds"
	(let [graph {:1 '(:3 :2), 
							 :3 '(:4), 
							 :4 '(:6 :5)}]
		(reset! data/confirmed-inviteds (set [1 3 4]))
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(1 0.5 0.0 0.0 0.0 0.0))

(fact "Path to count points of graph starting at 1 with 1 2 3 and 4 as confirmed inviteds"
	(let [graph {:1 '(:3 :2), 
							 :3 '(:4), 
							 :4 '(:6 :5)}]
		(reset! data/confirmed-inviteds (set [1 2 3 4]))
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(2 0.5 0.0 0.0 0.0 0.0))

(fact "Path to count points of graph starting at 1 with 1 2 3 4 and 5 as confirmed inviteds"
	(let [graph {:1 '(:2 :5 :6), 
							 :2 '(:3),
							 :3 '(:4)}]
		(reset! data/confirmed-inviteds (set [1 2 3 4 5]))
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(2 0.5 0.0 0.0 0.25 0.0))

(fact "Path to count points of graph starting at 2 with 1 2 3 4 and 5 as confirmed inviteds"
	(let [graph {:1 '(:2 :5 :6), 
							 :2 '(:3),
							 :3 '(:4)}]
		(reset! data/confirmed-inviteds (set [1 2 3 4 5]))
		(aux-bfs graph
						(hash-map :2 0)
						#{:2} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :2))) => '(1 0.5 0.0))

(fact "Path to count points of graph starting at 1 with 1 2 and 3 as confirmed inviteds"
	(let [graph {:1 '(:3 :2), 
							 :3 '(:4)}]
		(reset! data/confirmed-inviteds (set [1 2 3]))
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(2 0.0 0.0 0.0))

(fact "Path to count points of graph starting at 3 with 1 3 and 4 as confirmed inviteds" 
	(let [graph {:1 '(:3 :2), 
							 :3 '(:4), 
							 :4 '(:6 :5)}]
		(reset! data/confirmed-inviteds (set [1 3 4]))
		(aux-bfs graph
						(hash-map :3 0)
						#{:3} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :3))) => '(1 0.0 0.0 0.0))


(fact "Path to count points of graph starting at 1 with 1 2 3 4 5 8 9 and 11 as confirmed inviteds"
	(let [graph {:1 '(:2 :8),
							 :2 '(:3),
							 :3 '(:4),
							 :4 '(:5),
							 :5 '(:6 :7),
							 :8 '(:9),
							 :9 '(:10 :11),
							 :11 '(:13 :12)}]
		(reset! data/confirmed-inviteds (set [1 2 3 4 5 8 9 11]))
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(2 0.5 0.5 0.25 0.25 0.125 0.0 0.0 0.0 0.0 0.0 0.0 0.0))

(fact "Path to count points of graph starting at 4 with 1 2 3 4 5 8 9 and 11 as confirmed inviteds"
	(let [graph {:1 '(:2 :8),
							 :2 '(:3),
							 :3 '(:4),
							 :4 '(:5),
							 :5 '(:6 :7),
							 :8 '(:9),
							 :9 '(:10 :11),
							 :11 '(:13 :12)}]
		(reset! data/confirmed-inviteds (set [1 2 3 4 5 8 9 11]))
		(aux-bfs graph
						(hash-map :4 0)
						#{:4} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :4))) => '(1 0.0 0.0 0.0))

(fact "Path to count points of empty graph with none confirmed invited"
	(let [graph {}]
		(reset! data/confirmed-inviteds (set []))
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(0))

(fact "Path to count points of only directed invited graph with none confirmed invited"
	(let [graph {:1 '(:2 :3 :4)}]
		(reset! data/confirmed-inviteds (set []))
		(aux-bfs graph
						(hash-map :1 0)
						#{:1} 
						(conj (clojure.lang.PersistentQueue/EMPTY) :1))) => '(0 0.0 0.0 0.0))

