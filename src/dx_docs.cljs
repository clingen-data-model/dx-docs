(ns dx-docs
  (:require [reagent.core :as reagent]
            [reagent.dom :as rdom]
            [re-frame.core :as re-frame]
            [dx-docs.views :as views]
            [dx-docs.events :as events]
            [dx-docs.routes :as routes]))

(enable-console-print!)

(defn ^:dev/after-load mount-root []
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [routes/router-component] root-el)))

(re-frame/reg-event-db
  ::initialize-db
  (fn [db _]
    {:current-route nil
     :entities {}
     :active-page :getting-started}))

(defn ^:export init []
  (re-frame/dispatch-sync [::initialize-db])
  (re-frame/dispatch [::events/load-schemas])
  (re-frame/dispatch [::events/load-documents])
  (re-frame/clear-subscription-cache!)
  (routes/init-routes!)
  (mount-root))


