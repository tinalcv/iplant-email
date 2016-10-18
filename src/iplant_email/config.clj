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

(cc/defprop-optstr amqp-uri
  "The URI to use for AMQP connections."
  [props config-valid configs]
  "iplant-email.amqp.uri" "amqp://guest:guest@rabbit:5672/")

(cc/defprop-optstr exchange-name
  "The name of the exchange to connect to on the AMQP broker."
  [props config-valid configs]
  "iplant-email.amqp.exchange.name" "de")

(cc/defprop-optboolean exchange-durable?
  "Whether or not the exchange is durable."
  [props config-valid configs]
  "iplant-email.amqp.exchange.durable" true)

(cc/defprop-optboolean exchange-auto-delete?
  "Wether or not the exchange is automatically deleted."
  [props config-valid configs]
  "iplant-email.amqp.exchange.auto-delete" false)

(cc/defprop-optstr queue-name
  "The name of the queue to connect to on the exchange."
  [props config-valid configs]
  "iplant-email.amqp.queue.name" "events.iplant-email.queue")

(cc/defprop-optboolean queue-durable?
  "Whether or not the queue is durable."
  [props config-valid configs]
  "iplant-email.amqp.queue.durable" true)

(cc/defprop-optboolean queue-auto-delete?
  "Whether or not the queue is automatically deleted."
  [props config-valid configs]
  "iplant-email.amqp.queue.auto-delete" false)

(defn- validate-config
  []
  (when-not (cc/validate-config configs config-valid)
    (throw+ {:error_code ce/ERR_CONFIG_INVALID})))

(defn load-config-from-file
  [cfg-path]
  (cc/load-config-from-file cfg-path props)
  (cc/log-config props)
  (validate-config))
