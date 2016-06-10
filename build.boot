(set-env!
 :source-paths #{"src"}
 :resource-paths #{"res"}
 :target-path "target"
 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [commons-validator "1.4.1"]])

(task-options!
 pom {:project 'contemporize
      :version "0.1.0"}
 jar {:manifest {"Foo" "bar"}})
