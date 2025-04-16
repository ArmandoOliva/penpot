// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at http://mozilla.org/MPL/2.0/.
//
// Copyright (c) KALEIDOS INC

import * as React from "react";
import Components from "@target/components";

const { Tooltip } = Components;

export default {
  title: "Tooltip",
  component: Tooltip,
  args: { children: "Lorem ipsum", id: "popover-example", },
  render: ({ ...args }) => <Tooltip {...args} />,
};

// El default ahora mismo no se ve, porque el popover no se ha triggereado.
export const Default = {};

export const Hover = {
  render: ({}) => (
    <>
      <button popovertarget="popover-example">haz hover aquí</button>
      <Tooltip id="popover-example">Este es content del Tooltip</Tooltip>
    </>
  ),
};
