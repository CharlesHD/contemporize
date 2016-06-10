(ns chu.contemporize.core
  (:require [chu.contemporize.parse :refer [string-to-poem poem-to-string]]
            [chu.contemporize.mutation :as m]
            [chu.contemporize.mutation :refer [mutate]]))


(defn -main
  [f]
  (let [s (slurp f)
        p (string-to-poem s)
        mutations {:form 0.06
                   :upper 0.04
                   ;; :capitalize 0.02
                   :quote 0.02
                   :hyphen 0.01
                   :parentheses 0.01}]
    (-> p
        (mutate mutations)
        (poem-to-string)
        (#(spit "target/poem" %))
        )))
