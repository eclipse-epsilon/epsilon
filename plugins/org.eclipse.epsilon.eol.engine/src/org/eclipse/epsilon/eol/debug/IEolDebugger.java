/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.eol.debug;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.control.ExecutionController;

public interface IEolDebugger extends ExecutionController {

	BreakpointResult verifyBreakpoint(BreakpointRequest request);

	void setTarget(IEpsilonDebugTarget target);

	IEpsilonDebugTarget getTarget();

	void step();

	void stepOver();

	void stepReturn();

	boolean isStepping();

	ModuleElement getStopAfterModuleElement();

	Integer getStopAfterFrameStackSizeDropsBelow();

}