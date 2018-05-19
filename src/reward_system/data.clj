(ns reward-system.data
	(:require [clojure.set :only union :as set]
						[reward-system.utility :as utility]))

(def graph (atom {}))
(def inviteds (atom (sorted-set)))
(def confirmed-inviteds (atom (set [])))
(def ranking (atom {}))

(defn insert-graph! [x y]
	(let [conected-points (get @graph (keyword x))]
	  (swap! graph conj (hash-map (keyword x) (conj conected-points (keyword y))))))

(defn insert-confirmed-inviteds! [point] 
	(swap! confirmed-inviteds conj (read-string point)))

(defn insert-inviteds! [point]
	(swap! inviteds conj (read-string point)))

(defn insert-ranking! [start-point reward-points]
	(swap! ranking into (hash-map start-point reward-points)))

(defn insert! [x y]
	(when-not (utility/contains-set (read-string y) (set/union @inviteds @confirmed-inviteds))
		(insert-graph! x y))
	(insert-confirmed-inviteds! x)
	(insert-inviteds! y))

