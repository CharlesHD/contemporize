(ns chu.contemporize.core
  (:require [chu.contemporize.parse :refer [string-to-poem poem-to-string]]
            [chu.contemporize.mutation :as m]
            [chu.contemporize.mutation :refer [mutate]]))


(defn make
  [s mutations]
  (let [p (string-to-poem s)]
    (-> p
        (mutate mutations)
        (poem-to-string))))
(defn -main
  [f]
  (let [s (slurp f)
        mutations {:form 0.06
                   :upper 0.03
                   ;; :capitalize 0.02
                   :quote 0.02
                   :hyphen 0.01
                   :parentheses 0.01}]
        (spit "target/poem" (make s mutations))
        ))
