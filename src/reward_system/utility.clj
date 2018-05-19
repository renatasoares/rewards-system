(ns reward-system.utility)

(defn pow [base exp]
  (apply * (repeat exp base)))

(defn contains-set [x arg-set]
  (some #{x} arg-set))
