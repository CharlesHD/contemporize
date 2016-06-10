(ns chu.contemporize.parse
  (:require [clojure.string :as str]
            [chu.contemporize.model :as m]))


(defn poem-to-string
  [poem]
  (let [lines (partition-by #(= (:type %) :word) poem)]
    (loop [[l & ls] lines
           res ""]
      (cond
        (nil? l) res
        (= (:type (first l)) :word) (recur ls
                                           (apply str res (interpose " " (map :cont l))))
        (= (:type (first l)) :ln) (recur ls
                                        (apply str res (map (fn [x] "\n") l)))
        :else (recur ls res)))))


(defn string-to-words
  [s]
  (-> s
      (str/replace #"[\.,:;\?!]" " ")
      (str/replace #"\n" " \n ")
      (str/replace #" +" " ")
      (str/replace #"^ " "")
      (str/replace #" $" "")
      (str/split #" ")))

(defn string-to-poem
  [s]
  (let [words (string-to-words s)
        poem (m/create-empty-poem)]
    (reduce
     (fn [p w]
       (case w
         "\n" (m/add-Ln p)
         (m/add-word p (str/lower-case w))))
     poem
     words)))
