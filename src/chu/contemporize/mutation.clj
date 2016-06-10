(ns chu.contemporize.mutation
  (:require [clojure.string :as str]
            [chu.contemporize.model :as m]))


(defn- tag-word
  ([w tag1 tag2]
   (update w :cont #(str tag1 % tag2)))
  ([w tag]
   (tag-word w tag tag)))

(defmulti transform-word (fn [t w] t))

(defmethod transform-word :upper
  [t word]
  (update word :cont str/upper-case))

(defmethod transform-word :capitalize
  [t word]
  (update word :cont str/capitalize))

(defmethod transform-word :quote
  [t w]
  (tag-word w "\""))

(defmethod transform-word :hyphen
  [t w]
  (tag-word w "− " " −"))

(defmethod transform-word :parentheses
  [t w]
  (tag-word w "(" ")"))

(defn mutate-word
  [poem type prob]
  (println "mutate " type " with proba" prob)
  (reduce
   (fn [np w]
     (let [test (rand)]
       (conj np
             (if (and (< test prob) (= (:type w) :word))
               (transform-word type w)
               w))))
   []
   poem))


(defn mutate-poem-form
  [poem p]
  (println "mutate form with proba " p)
  (reduce
   (fn [np w]
     (let [test (rand)]
       (cond-> np
         true (conj w)
         (< test p) (m/add-Ln))))
   []
   poem))


(defn mutate
  [poem probs]
  (reduce-kv
   (fn [p k v]
     ;; (println p "\n" k "\n" v)
     (if (= k :form)
       (mutate-poem-form p v)
       (mutate-word p k v)))
   poem
   probs))
