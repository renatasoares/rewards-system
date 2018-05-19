(ns reward-system.logic 
  (:require [reward-system.data :as data]
            [reward-system.utility :as utility]))

(defn treat-levels [levels vertice inviteds]
 (apply merge levels (doall (map #(hash-map % (inc (get levels vertice))) inviteds))))

(defn confirmed-inviteds-quantity [inviteds]
  (reduce + (doall (map #(if (utility/contains-set (read-string (name %)) @data/confirmed-inviteds) 1 0) inviteds))))

(defn aux-bfs [graph levels visited adjacent]

  (lazy-seq
    (if (empty? adjacent)
      nil
      (let [vertice (peek adjacent)
            inviteds (graph vertice)
            confirm-inviteds (confirmed-inviteds-quantity inviteds)
            level (get levels vertice)
            points (* (utility/pow 0.5 level) confirm-inviteds)]
        (cons points (aux-bfs
                       graph
                       (treat-levels levels vertice inviteds)
                       (into visited inviteds)
                       (into (pop adjacent) (remove visited inviteds))))))))




