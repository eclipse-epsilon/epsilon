/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.dt;

import org.eclipse.epsilon.common.dt.AbstractEpsilonUIPlugin;

/**
 * PinsetPlugin.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetPlugin extends AbstractEpsilonUIPlugin {

	public static PinsetPlugin getDefault() {
		return (PinsetPlugin) plugins.get(PinsetPlugin.class);
	}

}
