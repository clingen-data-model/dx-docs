(ns dx-docs.template
  (:require [reitit.frontend.easy :as rfe]
            [re-frame.core :as re-frame]
            [dx-docs.manifest :as manifest]
            [dx-docs.subs :as subs]))

(defn navbar []
  (let [navbar-burger-active false]
    [:nav.navbar
     {:role "navigation" :aria-label "main navigation"}
     [:div.navbar-brand
      [:a.navbar-item.has-text-link.logo.is-size-4
       {:href (rfe/href :home)}
       [:img {:src "images/clingen-logo-vp.svg"
              :width "30%"
              :height "auto"}]
       
       "clingen data exchange"]
      [:a.navbar-burger {:role "button"
                         :aria-label "menu"
                         :aria-expanded "false"
                         :class navbar-burger-active
                         :on-click #(re-frame/dispatch [:common/toggle-navbar-burger])}
       [:span {:aria-hidden "true"}]
       [:span {:aria-hidden "true"}]
       [:span {:aria-hidden "true"}]]]
     [:div.navbar-menu
      {:class navbar-burger-active}
      [:div.navbar-start]
      [:div.navbar-end
       (for [[profile profile-attrs] manifest/profiles]
         ^{:key profile}
            [:a.navbar-item
             {:href (rfe/href :profile {:name profile})}
             (:label profile-attrs)])]]]))



(defn root-menu []
  [:aside.menu
   [:p.menu-label
    [:a.navbar-item
     {:href (rfe/href :home)}
     "home"]]
   [:p.menu-label "profiles"]
   [:ul.menu-list
    (for [[profile profile-attrs] manifest/profiles]
      ^{:key profile} [:li
                       [:a
                        {:href (rfe/href :profile {:name profile})}
                        (:label profile-attrs)]])]])

(defn profile-menu [profile-name]
  (let [active @(re-frame/subscribe [:dx-docs.subs/active])
        profile (get manifest/profiles profile-name)
        entities @(re-frame/subscribe [:dx-docs.subs/profile-entities profile])]
    [:aside.menu
     [:p.menu-label "background"]
     [:ul.menu-list
      (for [doc (:documents profile)]
        ^{:key doc}
        [:li
         [:a
          {:href (rfe/href :document {:name doc})}
          (get-in manifest/documents [doc :label])]])]
     [:p.menu-label "entities"]
     [:ul.menu-list
      (for [[entity-name entity-attrs] entities]
        ^{:key entity-name}
        [:li
         [:a
          {:href (rfe/href :entity {:name entity-name})}
          entity-name]])]]))

(defn menu []
  (if-let [current-profile @(re-frame/subscribe [:dx-docs.subs/current-profile])]
    (profile-menu current-profile)
    (root-menu)))

(defn root-component []
  (let [current-route @(re-frame/subscribe
                        [:dx-docs.routes/current-route])]
    [:section.section
     (navbar)
     [:div.columns
      [:div.column.is-narrow (menu)]
      [:div.column
       (when current-route
         [(-> current-route :data :view)])]]]))
