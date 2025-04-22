// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at http://mozilla.org/MPL/2.0/.
//
// Copyright (c) KALEIDOS INC

import * as React from "react";
import Components from "@target/components";
const { StoryGrid, StoryGridCell, StoryHeader } = Components.storybook;

const { Tooltip } = Components;

export default {
  title: "Tooltip",
  component: Tooltip,
  args: {
    children: "Lorem ipsum",
    id: "popover-example",
    content: "Este es content del Tooltip",
  },
  render: ({ ...args }) => <Tooltip {...args} />,
};

// El default ahora mismo no se ve, porque el popover no se ha triggereado.
export const Default = {};

export const Top = {
  render: ({}) => (
    <Tooltip id="popover-example" content="Este es content del Tooltip">
      <button
        popovertarget="popover-example"
        style={{
          width: "fit-content",
          transform: "translateY(50vh) translateX(50vw)",
        }}
      >
        haz hover aquí
      </button>
    </Tooltip>
  ),
};

export const Bottom = {
  render: ({}) => (
    <Tooltip
      id="popover-example"
      content="Este es content del Tooltip"
      position="bottom"
    >
      <button
        popovertarget="popover-example"
        style={{
          width: "fit-content",
          transform: "translateY(50vh) translateX(50vw)",
        }}
      >
        haz hover aquí
      </button>
    </Tooltip>
  ),
};

export const Left = {
  render: ({}) => (
    <Tooltip
      id="popover-example"
      content="Este es content del Tooltip"
      position="left"
    >
      <button
        popovertarget="popover-example"
        style={{
          width: "fit-content",
          transform: "translateY(50vh) translateX(50vw)",
        }}
      >
        haz hover aquí
      </button>
    </Tooltip>
  ),
};

export const Right = {
  render: ({}) => (
    <Tooltip
      id="popover-example"
      content="Este es content del Tooltip"
      position="right"
    >
      <button
        popovertarget="popover-example"
        style={{
          width: "fit-content",
          transform: "translateY(50vh) translateX(50vw)",
        }}
      >
        haz hover aquí
      </button>
    </Tooltip>
  ),
};

export const corners = {
  render: ({}) => (
    <>
      <StoryGrid size="700">
        <StoryGridCell
          style={{
            display: "grid",
            gap: "0.5rem",
          }}
        >
          <Tooltip
            id="popover-example"
            content="Este es content del Tooltip rojo"
          >
            <button
              popovertarget="popover-example"
              style={{ width: "fit-content", border: "1px solid red" }}
            >
              haz hover aquí
            </button>
          </Tooltip>
        </StoryGridCell>
        <StoryGridCell
          style={{
            color: "var(--color-accent-primary)",
            display: "grid",
            gap: "0.5rem",
          }}
        >
          <Tooltip
            id="popover-example2"
            content="Este es content del Tooltip azul"
          >
            <button
              popovertarget="popover-example2"
              style={{
                width: "fit-content",
                border: "1px solid blue",
              }}
            >
              haz hover aquí
            </button>
          </Tooltip>
        </StoryGridCell>
        <StoryGridCell
        
          style={{
            color: "var(--color-accent-primary)",
            display: "grid",
            gap: "0.5rem",
          }}
        >
          <Tooltip
            id="popover-example4"
            content="Este es content del Tooltip amarillo"
          >
            <button
              popovertarget="popover-example4"
              style={{
                width: "fit-content",
                border: "1px solid yellow",
              }}
            >
              haz hover aquí
            </button>
          </Tooltip>
        </StoryGridCell>
        <StoryGridCell
        
          style={{
            color: "var(--color-accent-primary)",
            display: "grid",
            gap: "0.5rem",
          }}
        >
          <Tooltip
            id="popover-example3"
            content="Este es content del Tooltip verde"
          >
            <button
              popovertarget="popover-example3"
              style={{
                width: "fit-content",
                border: "1px solid green",
              }}
            >
              haz hover aquí
            </button>
          </Tooltip>
        </StoryGridCell>{" "}
      </StoryGrid>
    </>
  ),
};
