/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.debug;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.pinset.DatasetRule;

/**
 * PinsetDebugger.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetDebugger extends EolDebugger {

	public PinsetDebugger() {
		super();
	}

	@Override
	protected boolean isStructuralBlock(ModuleElement ast) {
		return super.isStructuralBlock(ast) || ast instanceof DatasetRule;
	}

}
