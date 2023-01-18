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

(defn ref-link [ref]
  (let [ref-name (keyword (re-find #"\w+$" ref))]
    [:a
     {:on-click (fn [] (re-frame/dispatch [::events/set-active
                                           {:type :entity
                                            :entity ref-name}]))}
     ref-name]))

(defn property-type [property]
  (cond
    (= "array" (:type property)) [:div [:li "array"] (property-type (:items property))]
    (:type property) ^{:key (:type property)} [:li (:type property)]
    (:$ref property) ^{:key (:$ref property)} [:li (ref-link
                                                    (:$ref property))]
    (:oneOf property) [:div (map property-type (:oneOf property))]))

(defmethod render :entity [active]
  (let [entity @(re-frame/subscribe [::subs/entity (:entity active)])]
    [:div
     [:h3.title.is-3 (:entity active)]
     [:p.subtitle.is-5 (:description entity)]
     (for [[prop-name prop-attrs] (:properties entity)]
       ^{:key prop-name}
       [:div.columns
        [:div.column.is-one-third
         [:h6.title.is-6 prop-name]
         [:ul
          (when (:dx-docs/required prop-attrs)
            [:li "required"])
          (property-type prop-attrs)]]
        [:div.column (:description prop-attrs)]])]))

(defn entity []
  (let [active @(re-frame/subscribe [::subs/active])
        entity @(re-frame/subscribe [::subs/entity (:entity active)])]
    [:div
     [:h3.title.is-3 (:entity active)]
     [:p.subtitle.is-5 (:description entity)]
     (for [[prop-name prop-attrs] (:properties entity)]
       ^{:key prop-name}
       [:div.columns
        [:div.column.is-one-third
         [:h6.title.is-6 prop-name]
         [:ul
          (when (:dx-docs/required prop-attrs)
            [:li "required"])
          (property-type prop-attrs)]]
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

(defn home []
  [:section.hero
   [:div.hero-body
    [:p.title "ClinGen Data Exchange"]]])

(defn document []
  #_[:h3.title.is-3 "document"]
  (if-let [active @(re-frame/subscribe [::subs/active])]
    @(re-frame/subscribe [::subs/document-hiccup (:entity active)])
    [:h3.title.is-3 "document not found"]))

(defn profile []
  (if-let [active @(re-frame/subscribe [::subs/active])]
    @(re-frame/subscribe [::subs/document-hiccup (:entity active)])
    [:h3.title.is-3 "profile"]))

(defn main-panel []
  [:section.section
   [:div.columns
    [:div.column.is-narrow (menu)]
    [:div.column (render @(re-frame/subscribe [::subs/active]))]]])

