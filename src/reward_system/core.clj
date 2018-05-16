(ns reward-system.core
  (:require [reward-system.adapter :as adapter]
			  		[reward-system.data :as data]
			  		[reward-system.logic :as logic]))

(defn -main []
	(adapter/read-file "resources/in" data/insert!)
  (println
	(adapter/bfs @data/graph :1)))
