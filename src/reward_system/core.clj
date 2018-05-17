(ns reward-system.core
  (:require [reward-system.adapter :as adapter]
			  		[reward-system.data :as data]
			  		[reward-system.logic :as logic]))

(defn -main []
	(adapter/read-file "resources/sample-in" data/insert!)
	(adapter/bfs @data/graph :1)
	(adapter/bfs @data/graph :2)
	(adapter/bfs @data/graph :3)
	(adapter/bfs @data/graph :4)
	(adapter/bfs @data/graph :5)
	(println @data/ranking))
