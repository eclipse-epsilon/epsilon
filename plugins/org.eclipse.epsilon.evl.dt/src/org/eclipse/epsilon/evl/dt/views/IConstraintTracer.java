/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.dt.views;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;

public interface IConstraintTracer {
	
	void traceConstraint(UnsatisfiedConstraint constraint, ILaunchConfiguration configuration);

}
