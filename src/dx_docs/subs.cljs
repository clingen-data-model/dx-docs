(ns dx-docs.subs
  (:require [re-frame.core :as re-frame]
            [clojure.walk :as walk]
            [nextjournal.markdown :as md]
            [nextjournal.markdown.transform :as md.transform]
            [clojure.string :as str]))

(def bulma-style-substitutions
  {:h1 :h1.title.is-1
   :h2 :h1.title.is-2
   :h3 :h1.title.is-3
   :h4 :h1.title.is-4
   :h5 :h1.title.is-5
   :h6 :h1.title.is-6
   :p :p.is-family-secondary.block})

(defn add-bulma-style [hiccup]
  (walk/postwalk
   (fn [x]
     (if (vector? x)
       (let [tag (first x)]
         (assoc x 0 (bulma-style-substitutions tag tag)))
       x))
   hiccup))

(re-frame/reg-sub
 ::markdown
 (fn [db]
   (-> db
       :docs/page
       md/->hiccup
       add-bulma-style)))

(re-frame/reg-sub
 ::schemas
 (fn [db] (:schemas db)))

(re-frame/reg-sub
 ::documents
 (fn [db] (:documents db)))

(re-frame/reg-sub
 ::document-hiccup
 (fn [db [_ document]]
   (get-in db [:documents document :hiccup])))

(re-frame/reg-sub
 ::expanded-schema
 (fn [db] (:page/expanded-schema db)))

(re-frame/reg-sub
 ::expanded-class
 (fn [db] (:page/expanded-class db)))

(re-frame/reg-sub
 ::entities
 (fn [db] (:entities db)))

(re-frame/reg-sub
 ::active-page
 (fn [db] (:active-page db)))
