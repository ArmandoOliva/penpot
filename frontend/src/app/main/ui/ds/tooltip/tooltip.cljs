;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.
;;
;; Copyright (c) KALEIDOS INC

(ns app.main.ui.ds.tooltip.tooltip
  (:require-macros
   [app.main.style :as stl])
  (:require
   [app.common.data :as d]
   [app.util.dom :as dom]
   [rumext.v2 :as mf]))

(defn use-tooltip-trigger-hook [tooltip-id]
  (let [on-show (fn[] (when-let [tooltip (dom/get-element tooltip-id)]
                         (.showPopover tooltip)))
        on-hide (fn[] (when-let [tooltip (dom/get-element tooltip-id)]
                         (.hidePopover tooltip)))]
    
    {:on-open-tooltip on-show
     :on-close-tooltip on-hide
     :aria-describedby tooltip-id}))

;; ¿Puede ser focuseado este elemento?
(mf/defc tooltip*
  {::mf/props :obj}
  [{:keys [class id children] :rest props}]
  (let [class (d/append-class class (stl/css :tooltip))
        props (mf/spread-props props {:class class
                                      :id id
                                      :popover "auto"
                                      :role "tooltip"})]
    [:> :div props
     children]))
