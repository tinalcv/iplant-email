(ns iplant-email.core-test
  (:use [clojure.test]
        [iplant-email.core])
  (:require [iplant-email.config :as config]))

(deftest test-default-configs
  (require 'iplant-email.config :reload)
  (config/load-config-from-file "dev-resources/mostly-defaults.properties")
  (testing "default configuration settings"
    (is (= (config/listen-port) 60000))
    (is (= (config/smtp-host) "local-exim"))
    (is (= (config/smtp-from-addr) "noreply@example.org"))))
