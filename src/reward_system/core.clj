(ns reward-system.core
  (:require [reward-system.adapter :as adapter]
			  		[reward-system.data :as data]
			  		[reward-system.logic :as logic]
			  		[clojure.set :as set :only union]))

(defn -main []
	(adapter/read-file "resources/sample-in" data/insert!)
	(let [union-sets (set/union @data/confirmed-inviteds @data/inviteds)
				max-size (-> union-sets 
										 (last) 
										 (read-string))]
		(doall (map #(adapter/bfs @data/graph (-> % (str) (keyword))) (range 1 max-size))))
	(println @data/graph)
	(println (adapter/show-ranking @data/ranking)))

