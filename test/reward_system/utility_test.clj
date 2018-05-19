(ns reward-system.utility-test
	(:require [midje.sweet :refer :all]
						[reward-system.utility :refer :all]))

(fact "Testing pow base case"
	(pow 2 2) => 4)

(fact "Testing pow property n^0 = 1"
	(pow 0.5 0) => 1)

(fact "Testing pow property n^1 = n"
	(pow 0.5 1) => 0.5)

(fact "Testing pow property 1^n = 1"
	(pow 1 15) => 1)

(fact "Testing pow property 0^n = 0"
	(pow 0 15) => 0)

(fact "If contains in set returns founded element"
	(contains-set 4 (set [1 4 5])) => 4)

(fact "If NOT contains in set returns nil"
	(contains-set 6 (set [1 4 5])) => nil)