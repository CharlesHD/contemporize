(ns chu.contemporize.model)

(defn create-empty-poem
  []
  (vec []))

(defn add-word
  [poem word]
  (conj poem
        {:type :word
         :cont word
         }))

(defn add-line
  [poem]
  (conj poem
        {:type :ln}))
