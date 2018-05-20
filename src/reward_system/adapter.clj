(ns reward-system.adapter
	(:require [clojure.java.io :as io]
						[clojure.string :as str]
						[clojure.data.json :as json]
            [clojure.set :as set :only union]
						[reward-system.data :as data]
            [reward-system.logic :as logic]))

(defn format-response [function string-seq]
	(let [numbers (str/split string-seq #" ")]
		(function (first numbers) (second numbers))))

(defn read-file [file-path function]
	(with-open [reader (io/reader file-path)]
  	(doall (map #(format-response function %) (line-seq reader)))))

(defn bfs [graph start-point]
  (data/insert-ranking! 
  	start-point
  	(reduce + (logic/aux-bfs 
  							graph 
  							(hash-map start-point 0) 
  							#{start-point} 
  							(conj (clojure.lang.PersistentQueue/EMPTY) start-point)))))

(defn show-ranking [ranking]
  (json/write-str 
  	(into (sorted-map-by (fn [point reward-points]
		                       	(compare [(get ranking reward-points) reward-points]
		                                 [(get ranking point) point]))) 
  				ranking)))

(defn run []
  (let [union-sets (set/union @data/confirmed-inviteds @data/inviteds)]
    (run! deref (doall (map #(future (bfs @data/graph (-> % (str) (keyword))))
      union-sets)))))

(defn old-run []
  (let [union-sets (set/union @data/confirmed-inviteds @data/inviteds)
        max-size (last union-sets)]
    (doall (map #(bfs @data/graph (-> % (str) (keyword)))
      (range 1 max-size)))))






