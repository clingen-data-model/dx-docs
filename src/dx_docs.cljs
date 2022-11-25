(ns dx-docs
  (:require [reagent.core :as reagent]
            [reagent.dom :as rdom]
            [re-frame.core :as re-frame]
            [dx-docs.views :as views]
            [dx-docs.events :as events]))

(enable-console-print!)

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(re-frame/reg-event-db
  ::initialize-db
  (fn [db _]
    {:entities {}
     :active-page :getting-started}))

(defn ^:export init []
  (re-frame/dispatch-sync [::initialize-db])
  (re-frame/dispatch [::events/load-schemas])
  (re-frame/dispatch [::events/load-documents])
  (mount-root))


