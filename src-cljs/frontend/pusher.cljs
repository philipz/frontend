(ns frontend.pusher
  (:require [cljs.core.async :as async :refer [>! <! alts! chan sliding-buffer close!]]
            [frontend.async :refer [put!]]
            [clojure.string :as string]
            [goog.dom.DomHelper]
            [goog.events]
            [om.core :as om :include-macros true]
            [frontend.utils :as utils :include-macros true]
            [frontend.utils.vcs-url :as vcs-url]
            [secretary.core :as sec])

  (:require-macros [cljs.core.async.macros :as am :refer [go go-loop alt!]]))

(defn new-pusher-instance [config]
  (aset (aget js/window "Pusher") "channel_auth_endpoint" "/auth/pusher")
  (let [config (merge {:encrypted true
                       :auth {:params {:CSRFToken (utils/csrf-token)}}
                       :authEndpoint "/auth/pusher"}
                      config)]
    (js/Pusher. (:key config) (clj->js (dissoc config :key)))))

(defn user-channel [user]
  (str "private-" (:login user)))

(defn build-channel-from-parts [{:keys [project-name build-num]}]
  (string/replace (str "private-" project-name "@" build-num) "/" "@"))

(defn build-channel [build]
  (build-channel-from-parts {:project-name (vcs-url/project-name (:vcs_url build))
                             :build-num (:build_num build)}))

(def build-messages [:build/new-action
                     :build/update-action
                     :build/append-action
                     :build/update
                     :build/add-messages])

;; TODO: use the same event names on the backend as we do on the frontend
(def event-translations
  {:build/new-action "newAction"
   :build/update-action "updateAction"
   :build/append-action "appendAction"
   :build/update "updateObservables"
   :build/add-messages "maybeAddMessages"
   ;; this is kind of special, it can call any function on the old window.VM
   ;; luckily, it only calls refreshBuildState
   :refresh "call"})

(defn subscribe
  "Subscribes to channel and binds to events. Takes a pusher-instance,
  a channel-name, a list of messages to subscribe to and a websocket channel.
  Will put data from the pusher events onto the websocket
  channel with the message. Returns the channel."
  [pusher-instance channel-name ws-ch & {:keys [messages context]}]
  (let [channel (.subscribe pusher-instance channel-name)]
    (doseq [message messages
            :let [pusher-event (get event-translations message)]]
      (.bind channel pusher-event #(put! ws-ch [message {:data %
                                                         :channel-name channel-name
                                                         :context context}])))
    (.bind channel "pusher:subscription_error"
           #(put! ws-ch [:subscription-error {:channel-name channel-name
                                              :status %}]))
    channel))

(defn unsubscribe [pusher-instance channel-name]
  (.unsubscribe pusher-instance channel-name))

(defn subscribed-channels [pusher-instance]
  (-> pusher-instance (aget "channels") (aget "channels") js-keys set))
