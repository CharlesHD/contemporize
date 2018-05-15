(ns chu.contemporize.core
  (:require [chu.contemporize.parse :refer [string-to-poem poem-to-string make]]
            [chu.contemporize.mutation :as m]
            [chu.contemporize.mutation :refer [mutate]]))

(def mutations {:form 0.06
                :upper 0.03
                :capitalize 0.02
                :quote 0.02
                :hyphen 0.01
                :parentheses 0.01})

(defn -main
  [f]
  (let [s (slurp f)]
    (spit "target/poem" (make s mutations))))
