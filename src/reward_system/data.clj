(ns reward-system.data)

(def graph (atom {}))
(def inviteds (atom (set [])))

(defn insert! [x y]
	(when-not (some #{y} @inviteds)
		(let [conected-points (get @graph (keyword x))]
	  	(swap! graph conj (hash-map (keyword x) (conj conected-points (keyword y))))))
	(swap! inviteds conj x y))
