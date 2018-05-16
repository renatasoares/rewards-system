(ns reward-system.core
  (:require [reward-system.adapter :as adapter]
			  		[reward-system.data :as data]
			  		[reward-system.logic :as logic]))

(defn -main []
	(adapter/read-file "resources/sample-in" data/insert!)
	(logic/dfs @data/graph :1))
