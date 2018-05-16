(ns reward-system.adapter
	(:require [clojure.java.io :as io]
						[clojure.string :as str]))

(defn format-response  [function string-seq]
	(let [numbers (-> string-seq
										(str)
										(str/split #" "))]
		(function (first numbers) (second numbers))))

(defn read-file [file-path function]
	(with-open [reader (io/reader file-path)]
  	(doall (map #(format-response function %) (line-seq reader)))))


