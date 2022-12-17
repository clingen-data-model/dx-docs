(ns dx-docs.events
  (:require [re-frame.core :as re-frame]
            [dx-docs.manifest :as manifest]
            [clojure.walk :as walk]
            [nextjournal.markdown :as md]
            [nextjournal.markdown.transform :as md.transform])
  (:import [goog.net XhrIo]))

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

(re-frame/reg-fx
 ::get-document
 (fn [args]
   (let [[doc-name doc-attrs] (first args)]
     (js/console.log "Getting document " (:source doc-attrs))
     (XhrIo/send (:source doc-attrs)
                 (fn [^js response]
                   (re-frame/dispatch [::recieve-markdown
                                       doc-name
                                       (.getResponseText (.-target response))]))))))

(defn annotate-entities [json-schema schema-name schema-attrs]
  (let [entities (or (:$defs json-schema) (:definitions json-schema))]
    (assoc
     json-schema
     :dx-docs/entities
     (zipmap (keys entities)
             (map #(assoc %
                          :dx-docs/schema-label (:label schema-attrs)
                          :dx-docs/schema-name schema-name)
                  (vals entities))))))

(re-frame/reg-fx
 ::get-schema
 (fn [args]
   (let [[schema-name schema-attrs] (first args)]
     (js/console.log "Getting schema " (str schema-name))
     #_(cljs.pprint/pprint schema-attrs)
     #_(js/console.log "Getting schema " (:schema-uri schema-attrs))
     (XhrIo/send
      (:schema-uri schema-attrs)
      (fn [^js response]
        (re-frame/dispatch
         [::recieve-schema
          schema-name
          (-> (.-target response)
              .getResponseJson
              (js->clj :keywordize-keys true)
              (annotate-entities schema-name schema-attrs))]))))))

(re-frame/reg-event-db
 ::recieve-markdown
 (fn [db [_ schema-name result]]
   (js/console.log "recieve-markdown " schema-name)
   #_(cljs.pprint/pprint (add-bulma-style (md/->hiccup result)))
   (-> db
       (assoc-in [:documents schema-name :markdown] result)
       (assoc-in [:documents schema-name :hiccup]
                 (add-bulma-style (md/->hiccup result))))))

(re-frame/reg-event-db
 ::failed-request
 (fn [db [_ result]]
   (js/console.log "failed request")
   (assoc db :docs/page result)))

(re-frame/reg-event-fx
 ::load-documents
 (fn [db _]
   {:fx (mapv (fn [doc] [::get-document [doc]]) manifest/documents)
    :db (assoc db :documents manifest/documents)}))

(re-frame/reg-event-db
 ::recieve-schema
 (fn [db [_ name spec]]
   (js/console.log "recieve schema")
   (-> db
       (assoc-in [:schemas name :spec] spec)
       (update :entities merge (:dx-docs/entities spec)))))

(re-frame/reg-event-fx
 ::fetch-schema
 (fn [_ [_ val]]
   (js/console.log "fetch markdown " val)
   {:fx [[::get-schema [(:schema-url manifest/schemas) ::recieve-schema]]]}))

(re-frame/reg-event-fx
 ::load-schemas
 (fn [db _]
   {:fx (mapv (fn [schema] [::get-schema [schema ::recieve-schema]])
              manifest/schemas)
    :db (assoc db :schemas manifest/schemas)}))

(re-frame/reg-event-db
 ::expand-schema
 (fn [db [_ schema-name]]
   (js/console.log (str "expand schema " schema-name))
   (assoc db :page/expanded-schema schema-name)))

(re-frame/reg-event-db
 ::expand-class
 (fn [db [_ class-name]]
   (js/console.log (str "expand class " class-name))
   (assoc db :page/expanded-class class-name)))

(re-frame/reg-event-db
 ::set-active-page
 (fn [db [_ active-page]]
   (js/console.log (str "set page " active-page))
   (assoc db :active-page active-page)))

(re-frame/reg-event-db
 ::set-active
 (fn [db [_ entity]]
   (js/console.log (str "set active " entity))
   (assoc db :active entity)))
