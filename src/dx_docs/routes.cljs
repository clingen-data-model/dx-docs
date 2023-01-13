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
     :link-text "home"}]
   ["document/:name"
    {:name :document
     :view views/document
     :controllers
     [{:parameters {:path [:name]}
       :start (fn [params]
                (js/console.log "start document")
                (cljs.pprint/pprint params)
                (re-frame/dispatch
                 [:dx-docs.events/set-active
                       {:type :page
                        :entity (keyword (get-in params [:path :name]))}]))}]}]])

(def router
  (rf/router routes))

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [::navigated new-match])))

(defn menu []
  [:aside.menu
   [:p.menu-label "docs"]
   [:ul.menu-list
    [:li [:a
          {:href (rfe/href :document {:name :clinvar})}
          #_{:on-click (fn [] (re-frame/dispatch
                             [:dx-docs.events/set-active
                              {:type :page
                               :entity :clinvar}]))}
          "ClinVar Getting Started"]]
    [:li [:a
          {:on-click
           (fn [] (re-frame/dispatch
                   [:dx-docs.events/set-active
                    {:type :page
                     :entity :policy}]))}
          "ClinVar Policies"]]]
   [:p.menu-label "schemas"]
   [:ul.menu-list
    [:li [:a
          {:on-click
           (fn [] (re-frame/dispatch
                   [:dx-docs.events/set-active
                    {:type :entity-list
                     :entity :clinvar-profile}]))}
          "ClinVar Profile"]]]])

(defn router-component []
  (let [current-route @(re-frame/subscribe [::current-route])]
    [:section.section
     [:div.columns
      [:div.column.is-narrow (menu)]
      [:div.column (when current-route
                     [(-> current-route :data :view)])]]]))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start! router on-navigate {:use-fragment true}))

#_(init-routes!)
