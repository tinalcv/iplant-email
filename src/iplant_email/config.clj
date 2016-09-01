(ns iplant-email.config
  (:use [slingshot.slingshot :only [throw+]])
  (:require [clojure-commons.props :as props]
            [clojure-commons.config :as cc]
            [clojure-commons.error-codes :as ce]
            [clojure.tools.logging :as log]))

(def ^:private props (ref nil))
(def ^:private config-valid (ref true))
(def ^:private configs (ref []))

(cc/defprop-optint listen-port
  "The port to listen to for incoming connections."
  [props config-valid configs]
  "iplant-email.app.listen-port" 60000)

(cc/defprop-optstr smtp-host
  "The host to connect to when sending messages via SMTP."
  [props config-valid configs]
  "iplant-email.smtp.host" "local-exim")

(cc/defprop-str smtp-from-addr
  "The address to send messages from."
  [props config-valid configs]
  "iplant-email.smtp.from-address")

(defn- validate-config
  []
  (when-not (cc/validate-config configs config-valid)
    (throw+ {:error_code ce/ERR_CONFIG_INVALID})))

(defn load-config-from-file
  [cfg-path]
  (cc/load-config-from-file cfg-path props)
  (cc/log-config props)
  (validate-config))
