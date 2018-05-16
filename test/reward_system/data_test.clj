(ns reward-system.data_test
	  (:require [midje.sweet :refer :all]
            	[reward-system.data :refer :all]))

(fact "Insert into graph"
	(reset! graph {})
	(reset! inviteds (set []))
	(let [parameters [["1" "2"] ["1" "3"] ["3" "4"] ["2" "4"] ["4" "5"] ["4" "6"]]
				expected-graph {:1 '(:3 :2), 
											  :3 '(:4), 
											  :4 '(:6 :5)}]
		(doseq [[x y] parameters]
			(insert! x y))
		@graph => expected-graph))


(fact "Only the first invitation counts"
	(reset! graph {})
	(reset! inviteds (set []))
	(let [parameters [["1" "2"] ["2" "3"] ["4" "3"] ["5" "3"]]
				expected-graph {:1 '(:2), 
											  :2 '(:3)}]
		(doseq [[x y] parameters]
			(insert! x y))
		@graph => expected-graph))