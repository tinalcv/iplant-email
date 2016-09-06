(use '[clojure.java.shell :only (sh)])
(require '[clojure.string :as string])

(defn git-ref
  []
  (or (System/getenv "GIT_COMMIT")
      (string/trim (:out (sh "git" "rev-parse" "HEAD")))
      ""))

(defproject org.cyverse/iplant-email "2.8.1-SNAPSHOT"
  :description "iPlant Email Service"
  :url "https://github.com/iPlantCollaborativeOpenSource/DE"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :manifest {"Git-Ref" ~(git-ref)}
  :uberjar-name "iplant-email-standalone.jar"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.cyverse/clojure-commons "2.8.0"]
                 [org.cyverse/service-logging "2.8.0"]
                 [cheshire "5.5.0"
                   :exclusions [[com.fasterxml.jackson.dataformat/jackson-dataformat-cbor]
                                [com.fasterxml.jackson.dataformat/jackson-dataformat-smile]
                                [com.fasterxml.jackson.core/jackson-annotations]
                                [com.fasterxml.jackson.core/jackson-databind]
                                [com.fasterxml.jackson.core/jackson-core]]]
                 [javax.mail/mail "1.4"]
                 [org.bituf/clj-stringtemplate "0.2"]
                 [org.cyverse/common-cli "2.8.0"]
                 [compojure "1.5.0"]
                 [me.raynes/fs "1.4.6"]
                 [slingshot "0.12.2"]]
  :eastwood {:exclude-namespaces [:test-paths]
             :linters [:wrong-arity :wrong-ns-form :wrong-pre-post :wrong-tag :misplaced-docstrings]}
  :plugins [[jonase/eastwood "0.2.3"]
            [test2junit "1.1.3"]]
  :profiles {:dev {:resource-paths ["dev-resources"]}}
  :aot [iplant-email.core]
  :main iplant-email.core)
