(ns frontend.intercom
  (:require [frontend.utils :as utils :include-macros true]))

(defn raise-dialog [ch]
  (try
    (js/Intercom "show")
    (catch :default e
      (utils/notify-error ch "Uh-oh, our Help system isn't available. Please email us instead, at sayhi@circleci.com")
      (utils/merror e))))

(defn user-link []
  (let [path (.. js/window -location -pathname)
        re (js/RegExp. "/gh/([^/]+/[^/]+)")]
    (when-let [match (.match path re)]
      (str "https://www.intercom.io/apps/vnk4oztr/users"
           "?utf8=%E2%9C%93"
           "&filters%5B0%5D%5Battr%5D=custom_data.pr-followed"
           "&filters%5B0%5D%5Bcomparison%5D=contains&filters%5B0%5D%5Bvalue%5D="
           (second path)))))

(defn track [event & [metadata]]
  (utils/swallow-errors (js/Intercom "trackEvent" (name event) (clj->js metadata))))

(def update-period-ms
  "Default frequency for polling Intercom for new messages"
  (* 60 1000))

(def update-interval-id
  "Keep track of the result of any calls to setInterval for polling Intercom"
  (atom nil))

(defn start-polling
  "Beging polling Intercom for new messages."
  [period-ms]
  (swap! update-interval-id
         (fn [interval-id]
          (js/clearInterval interval-id)
          (js/setInterval #(js/Intercom "update")
                          period-ms))))
