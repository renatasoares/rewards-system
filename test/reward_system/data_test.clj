(ns reward-system.data_test
	  (:require [midje.sweet :refer :all]
            	[reward-system.data :refer :all]))

(fact "Insert into graph with inviteds rules"
	(reset! graph {})
	(reset! inviteds (sorted-set))
	(reset! confirmed-inviteds (set []))
	(let [parameters [["1" "2"] ["1" "3"] ["3" "4"] ["2" "4"] ["4" "5"] ["4" "6"]]
				expected-graph {:1 '(:3 :2), 
											  :3 '(:4), 
											  :4 '(:6 :5)}]
		(doseq [[x y] parameters]
			(insert! x y))
		@graph => expected-graph))


(fact "Insert into graph with inviteds rules (Only the first invitation counts)"
	(reset! graph {})
	(reset! inviteds (sorted-set))
	(reset! confirmed-inviteds (set []))
	(let [parameters [["1" "2"] ["2" "3"] ["4" "3"] ["5" "3"]]
				expected-graph {:1 '(:2), 
											  :2 '(:3)}]
		(doseq [[x y] parameters]
			(insert! x y))
		@graph => expected-graph))

(fact "Insert into graph with no inviteds rules"
	(reset! graph {})
	(let [parameters [["1" "2"] ["2" "3"] ["3" "4"] ["3" "5"]]
				expected-graph {:1 '(:2), 
											  :2 '(:3),
											  :3 '(:5 :4)}]
		(doseq [[x y] parameters]
			(insert-graph! x y))
		@graph => expected-graph))

(fact "Insert inviteds"
	(reset! inviteds (set []))
	(run! insert-inviteds! '("1" "3" "2"))
	@inviteds => #{1 3 2})

(fact "Insert confirmed inviteds"
	(reset! confirmed-inviteds (set []))
	(run! insert-confirmed-inviteds! '("1" "2" "3"))
	@confirmed-inviteds => #{1 2 3})