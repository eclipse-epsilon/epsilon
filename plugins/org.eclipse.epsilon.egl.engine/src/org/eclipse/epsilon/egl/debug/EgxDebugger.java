/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.debug;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.eol.debug.BreakpointRequest;
import org.eclipse.epsilon.eol.debug.BreakpointResult;
import org.eclipse.epsilon.eol.debug.BreakpointState;
import org.eclipse.epsilon.eol.debug.EolDebugger;

public class EgxDebugger extends EolDebugger {
	
	@Override
	protected boolean isStructuralBlock(ModuleElement ast) {
		return super.isStructuralBlock(ast) || ast instanceof GenerationRule;
	}

	@Override
	public BreakpointResult verifyBreakpoint(BreakpointRequest request) {
		BreakpointResult result = super.verifyBreakpoint(request);
		if (result.getState() == BreakpointState.VERIFIED) {
			return result;
		}

		/*
		 * We really do not know if the given breakpoint will be triggered or not: it
		 * will depend on whether it points to an EGL script executed by the EGX rules.
		 */
		return BreakpointResult.pending(request);
	}
	
}
