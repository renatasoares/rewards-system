(ns reward-system.core
  (:require [reward-system.adapter :as adapter]
			  		[reward-system.data :as data]
			  		[reward-system.logic :as logic]))

(defn -main []
	(adapter/read-file "resources/sample-in" data/insert!)
	(let [max-size (read-string (last @data/inviteds))]
		(doall (map #(adapter/bfs @data/graph (-> % (str) (keyword))) (range 1 max-size))))
	(adapter/show-ranking @data/ranking))

