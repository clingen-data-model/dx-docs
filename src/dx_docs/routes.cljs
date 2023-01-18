(ns dx-docs.routes
  (:require [re-frame.core :as re-frame]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.frontend.controllers :as rfc]
            [dx-docs.views :as views]))

;; Init derived from example:
;; https://github.com/metosin/reitit/blob/master/examples/frontend-re-frame/src/cljs/frontend_re_frame/core.cljs

(re-frame/reg-fx :push-state
  (fn [route]
    (apply rfe/push-state route)))

(re-frame/reg-event-db ::navigated
  (fn [db [_ new-match]]
    (let [old-match   (:current-route db)
          controllers (rfc/apply-controllers (:controllers old-match) new-match)]
      (assoc db :current-route (assoc new-match :controllers controllers)))))

(re-frame/reg-sub ::current-route
  (fn [db]
    (:current-route db)))

(def routes
  ["/"
   [""
    {:name :home
     :view views/home
     :link-text "home"
     :controllers
     [{:start (fn [params]
                (re-frame/dispatch
                   [:dx-docs.events/set-current-profile nil]))}]}]
   ["profile/:name"
    {:name :profile
     :view views/profile
     :controllers
     [{:parameters {:path [:name]}
       :start (fn [params]
                (js/console.log "start document")
                (let [profile (keyword (get-in params [:path :name]))]
                  (re-frame/dispatch
                   [:dx-docs.events/set-active
                    {:type :page
                     :entity profile}])
                  (re-frame/dispatch
                   [:dx-docs.events/set-current-profile profile])))}]}]
   ["document/:name"
    {:name :document
     :view views/document
     :controllers
     [{:parameters {:path [:name]}
       :start (fn [params]
                (js/console.log "start document")
                (re-frame/dispatch
                 [:dx-docs.events/set-active
                       {:type :page
                        :entity (keyword (get-in params [:path :name]))}]))}]}]
   ["entity/:name"
    {:name :entity
     :view views/entity
     :controllers
     [{:parameters {:path [:name]}
       :start (fn [params]
                (js/console.log "start entity")
                (re-frame/dispatch
                 [:dx-docs.events/set-active
                  {:type :entity
                   :entity (keyword (get-in params [:path :name]))}]))}]}]])

(def router
  (rf/router routes))

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [::navigated new-match])))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start! router on-navigate {:use-fragment true}))
