(ns dx-docs.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::document-hiccup
 (fn [db [_ document]]
   (get-in db [:documents document :hiccup])))

(re-frame/reg-sub
 ::entities
 (fn [db] (:entities db)))

(re-frame/reg-sub
 ::entity
 (fn [db [_ entity]] (get-in db [:entities entity])))

(re-frame/reg-sub
 ::active
 (fn [db] (:active db)))
