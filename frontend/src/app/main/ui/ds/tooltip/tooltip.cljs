;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.
;;
;; Copyright (c) KALEIDOS INC

(ns app.main.ui.ds.tooltip.tooltip
  (:require-macros
   [app.common.data.macros :as dm]
   [app.main.style :as stl])
  (:require
   [app.common.data :as d]
   [app.util.dom :as dom]
   [rumext.v2 :as mf]))

(defn- calculate-tooltip-coords [tooltip trigger position offset]
  (let [{trigger-top    :top
         trigger-left   :left
         trigger-right  :right
         trigger-bottom :bottom
         trigger-width  :width
         trigger-height :height} (dom/get-bounding-rect trigger)

        {tooltip-width  :width
         tooltip-height :height} (dom/get-bounding-rect tooltip)

        offset (d/nilv offset 8)]
    
    (case position
      :bottom
      (do
        (dom/set-css-property! tooltip "top" (dm/str (+ trigger-bottom offset) "px"))
        (dom/set-css-property! tooltip "left" (dm/str (- (+ trigger-left (/ trigger-width 2)) (/ tooltip-width 2)) "px")))

      :left
      (do
        (dom/set-css-property! tooltip "top" (dm/str (- (+ trigger-top (/ trigger-height 2)) (/ tooltip-height 2)) "px"))
        (dom/set-css-property! tooltip "left" (dm/str (- trigger-left offset tooltip-width) "px")))

      :right
      (do
        (dom/set-css-property! tooltip "top" (dm/str (- (+ trigger-top (/ trigger-height 2)) (/ tooltip-height 2)) "px"))
        (dom/set-css-property! tooltip "left" (dm/str (+ trigger-right offset) "px")))

      (do
        (dom/set-css-property! tooltip "top" (dm/str (- trigger-top offset tooltip-height) "px"))
        (dom/set-css-property! tooltip "left" (dm/str (- (+ trigger-left (/ trigger-width 2)) (/ tooltip-width 2)) "px"))))))

  ;; Preguntar a Natacha
(defn- get-fallback-order [position]
  (case position
    "top" [:top :bottom :right :left]
    "bottom" [:bottom :top :right :left]
    "left" [:left :right :top :bottom]
    "right" [:right :left :top :bottom]))

(def ^:private schema:tooltip
  [:map
   [:class {:optional true} :string]
   [:id :string]
   [:offset {:optional true} :int]
   [:position {:optional true}
    [:maybe [:enum "top" "bottom" "left" "right"]]]])

(mf/defc tooltip*
  {::mf/props :obj
   ::mf/schema schema:tooltip}
  [{:keys [class id children content position offset] :rest props}]
  (let [on-show
        (mf/use-fn
         (mf/deps id)
         (fn [event]
          (when-let [tooltip (dom/get-element id)]
            (let [trigger (dom/event->target event)
                  all-placements (if position
                                   (get-fallback-order position)
                                   (get-fallback-order "top"))]

              (.showPopover tooltip)
              (loop [[current-placement & remaining-placements] all-placements]
                (when current-placement
                  (calculate-tooltip-coords tooltip trigger current-placement offset)

                  (when (dom/is-element-outside? tooltip)
                    (recur remaining-placements))))))))

        on-hide (mf/use-fn
                 (mf/deps id)
                 (fn [] (when-let [tooltip (dom/get-element id)]
                         (dom/unset-css-property! tooltip "top")
                         (dom/unset-css-property! tooltip "bottom")
                         (dom/unset-css-property! tooltip "left")
                         (dom/unset-css-property! tooltip "right")
                         (.hidePopover tooltip))))
        
        class (d/append-class class (stl/css :tooltip))
        props (mf/spread-props props {:on-mouse-enter on-show
                                      :on-mouse-leave on-hide
                                      :on-focus on-show
                                      :on-blur on-hide
                                      :class (stl/css :tooltip-trigger)
                                      :aria-describedby id})]
    [:> "div" props
     children
     [:span {:class class
             :id id
             :popover "auto"
             :role "tooltip"}
      (if (fn? content)
        (content)
        content)]]))


