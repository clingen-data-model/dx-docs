(ns dx-docs.views
  (:require [dx-docs.events :as events]
            [dx-docs.subs :as subs]
            [dx-docs.manifest :as manifest]
            [re-frame.core :as re-frame]))


(defn class-detail [attrs]
  [:div
   [:p.is-family-secondary (:description attrs)]
   [:ul
    (for [[property-name property-attrs] (:properties attrs)]
      [:li property-name])]])

(defn schema-classes [schema]
  (let [expanded-class @(re-frame/subscribe [::subs/expanded-class])]
    [:div
     (for [[class-name class-attrs] (or (get-in schema [:spec :$defs])
                                        (get-in schema [:spec :definitions]))]
       ^{:key class-name} [:div.pb-0
                           {:on-click
                            (fn []
                              (re-frame/dispatch
                               [::events/expand-class class-name]))}
                           (if (= expanded-class class-name)
                             [:div.pb-2
                              [:div [:b class-name]]
                              (class-detail class-attrs)]
                             class-name)])]))

(defn all-schemas []
  (let [schemas @(re-frame/subscribe [::subs/schemas])
        expanded-schema @(re-frame/subscribe [::subs/expanded-schema])]
    [:section.section
     (doall
      (for [[schema-name attrs] schemas]
        ^{:key schema-name}
        [:div.block
         [:h5.title.is-5
          {:on-click
           (fn [] (re-frame/dispatch
                   [::events/expand-schema schema-name]))}
          (:label attrs)]
         (when (= expanded-schema schema-name)
           (schema-classes attrs))]))]))

(defn schema-entities [schema]
  [:section.section
      [:h3.title.is-3 "definitions"]
      (for [[entity-name entity-props] (:$defs schema)]
        ^{:key entity-name}
        [:div.block
         [:h6.title.is-6 entity-name]
         [:p (:description entity-props)]])])

(defn all-documents []
  (let [documents @(re-frame/subscribe [::subs/documents])]
    [:section.section
     (doall
      (for [[doc-name doc-attrs] documents]
        ^{:key doc-name}
        [:div.block
         [:h5.title.is-5
          (:label doc-attrs)]]))]))

(defn document [document-name]
  [:section.section
   @(re-frame/subscribe [::subs/document-hiccup document-name])])

(defn main-panel []
  (document :clinvar)
  #_(all-documents)
  #_(all-schemas))