(ns reward-system.logic)

(defn treat-levels [levels vertice inviteds]
 (apply merge levels (doall (map #(hash-map % (inc (get levels vertice))) inviteds))))

(defn pow [base exp]
  (apply * (repeat exp base)))

(defn aux-bfs [graph levels visited adjacent]
    (lazy-seq
      (if (empty? adjacent)
        nil
        (let [vertice (peek adjacent)
              inviteds (graph vertice)
              confirm-inviteds (reduce + (doall (map #(if (zero? (count (graph %))) 0 1) inviteds)))
              level (get levels vertice)
              points (* (pow 0.5 level) confirm-inviteds)]
          (cons points (aux-bfs
                         graph
                         (treat-levels levels vertice inviteds)
                         (into visited inviteds)
                         (into (pop adjacent) (remove visited inviteds))))))))




