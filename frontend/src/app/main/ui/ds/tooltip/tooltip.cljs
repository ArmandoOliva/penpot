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

(defn calculate-tooltip-coords [tooltip trigger position offset]
  (let [{:keys [top left right bottom width height]} (dom/get-bounding-rect trigger)
        trigger-top    top
        trigger-left   left
        trigger-right  right
        trigger-bottom bottom
        trigger-width  width
        trigger-height height

        {:keys [width height] :as rect} (dom/get-bounding-rect tooltip)
        tooltip-width  width
        tooltip-height height
        offset (or offset 8)

        coord     (case position
                    :bottom
                    {:top (str (+ trigger-bottom offset) "px")
                     :left (str (- (+ trigger-left (/ trigger-width 2)) (/ tooltip-width 2)) "px")}

                    :left
                    {:top (str (- (+ trigger-top (/ trigger-height 2)) (/ tooltip-height 2)) "px")
                     :left (str (- (+ trigger-left offset) width) "px")}

                    :right
                    {:top (str (- (+ trigger-top (/ trigger-height 2)) (/ tooltip-height 2)) "px")
                     :left (str (+ trigger-right offset) "px")}

                    {:top (str (- trigger-top offset height) "px")
                     :left (str (- (+ trigger-left (/ trigger-width 2)) (/ tooltip-width 2)) "px")})]

    (do
      (dom/set-css-property! tooltip "top" (:top coord))
      (dom/set-css-property! tooltip "left" (:left coord)))))
  
  (def fallback-order {:top [:top :bottom :right :left]
                       :bottom [:bottom :top :right :left]
                       :left [:left :right :top :bottom]
                       :right [:right :left :top :bottom]})
  
  (defn remove-tooltip-position [tooltip]
    (dom/remove-attribute! tooltip "top")
    (dom/remove-attribute! tooltip "bottom")
    (dom/remove-attribute! tooltip "left")
    (dom/remove-attribute! tooltip "right"))

  (defn use-tooltip-trigger-hook [tooltip-id position offset]
    (let [on-show
          (fn [event]
            (when-let [tooltip (dom/get-element tooltip-id)]
              (let [trigger (dom/event->target event)
                    all-placements (if position 
                                     (fallback-order position)
                                     (fallback-order :top))]
                
                (.showPopover tooltip)
                (loop [[current-placement & remaining-placements] all-placements]
                  (when current-placement
                    (calculate-tooltip-coords tooltip trigger current-placement offset)
                    (when (dom/is-element-outside? tooltip)
                      (recur remaining-placements)))))))

          on-hide (fn [] (when-let [tooltip (dom/get-element tooltip-id)]
                          (remove-tooltip-position tooltip)
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
