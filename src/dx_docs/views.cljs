(ns dx-docs.views
  (:require [dx-docs.events :as events]
            [dx-docs.subs :as subs]
            [dx-docs.manifest :as manifest]
            [re-frame.core :as re-frame]))

(defmulti render :type)

(defmethod render :default [_]
  [:div
   [:h3.title.is-3 "model entities"]
   [:ul
    (for [[entity-name entity-attrs] @(re-frame/subscribe [::subs/entities])]
      ^{:key entity-name}
      [:li
       [:a
        {:on-click (fn [] (re-frame/dispatch
                           [::events/set-active
                            {:type :entity
                             :entity  entity-name}]))}
        entity-name] " (" (:dx-docs/schema-label entity-attrs) ")"])]])

(defmethod render :page [entity]
  [:section.section
   @(re-frame/subscribe [::subs/document-hiccup (:entity entity)])])

(defmethod render :entity [active]
  (let [entity @(re-frame/subscribe [::subs/entity (:entity active)])]
    [:div
     [:h1.title.is-1 (:entity active)]
     (for [[prop-name prop-attrs] (:properties entity)]
       [:div.columns
        [:div.column prop-name]
        [:div.column (:description prop-attrs)]])]))

(defn menu []
  [:aside.menu
   [:p.menu-label "docs"]
   [:ul.menu-list
    [:li [:a
          {:on-click (fn [] (re-frame/dispatch
                             [::events/set-active
                              {:type :page
                               :entity :clinvar}]))}
          "ClinVar Getting Started"]]
    [:li [:a
          {:on-click
           (fn [] (re-frame/dispatch
                   [::events/set-active
                    {:type :page
                     :entity :policy}]))}
          "ClinVar Policies"]]]
   [:p.menu-label "schemas"]
   [:ul.menu-list
    [:li [:a
          {:on-click
           (fn [] (re-frame/dispatch
                   [::events/set-active
                    {:type :entity-list
                     :entity :clinvar-profile}]))}
          "ClinVar Profile"]]]])

(defn main-panel []
  [:section.section
   [:div.columns
    [:div.column.is-narrow (menu)]
    [:div.column (render @(re-frame/subscribe [::subs/active]))]]])
