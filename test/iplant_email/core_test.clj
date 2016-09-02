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

(deftest test-specified-configs
  (require 'iplant-email.config :reload)
  (config/load-config-from-file "dev-resources/no-defaults.properties")
  (testing "specified configuration settings"
    (is (= (config/listen-port) 65535))
    (is (= (config/smtp-host) "the-host-that-shall-not-be-named"))
    (is (= (config/smtp-from-addr) "nobody@nowhere.org"))))
