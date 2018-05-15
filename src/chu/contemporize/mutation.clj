(ns chu.contemporize.mutation
  (:require [clojure.string :as str]
            [chu.contemporize.model :as m]))


(defn- tag-word
  ([w tag1 tag2]
   (update w :cont #(str tag1 % tag2)))
  ([w tag]
   (tag-word w tag tag)))

(defmulti transform-word (fn [t w] t))

(defmacro def-transform-word-fn
  [word type defs]
  `(do
     ~@(for [[key-type form] defs]
         `(defmethod transform-word ~key-type
            [~type ~word]
            ~form))))

(def-transform-word-fn w t
  {
   :upper (update w :cont str/upper-case)
   :capitalize (update w :cont str/capitalize)
   :quote (tag-word w "\"")
   :html-bold (tag-word w "<b>" "</b>")
   :html-em (tag-word w "<em>" "</em>")
   :html-it (tag-word w "<i>" "</i>")
   :hyphen (tag-word w "− " " −")
   :parentheses (tag-word w "(" ")")
   }
  )

;; (defmethod transform-word :upper
;;   [t word]
;;   (update word :cont str/upper-case))
;; (defmethod transform-word :capitalize
;;   [t word]
;;   (update word :cont str/capitalize))
;; (defmethod transform-word :quote
;;   [t w]
;;   (tag-word w "\""))
;; (defmethod transform-word :html-bold
;;   [t w]
;;   (tag-word w "<b>" "</b>"))
;; (defmethod transform-word :html-em
;;   [t w]
;;   (tag-word w "<em>" "</em>"))
;; (defmethod transform-word :html-it
;;   [t w]
;;   (tag-word w "<i>" "</i>"))
;; (defmethod transform-word :hyphen
;;   [t w]
;;   (tag-word w "− " " −"))
;; (defmethod transform-word :parentheses
;;   [t w]
;;   (tag-word w "(" ")"))

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
         (< test p) (m/add-line))))
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
