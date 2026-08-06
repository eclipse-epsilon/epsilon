/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.execute.context.concurrent;

import org.eclipse.epsilon.erl.execute.context.concurrent.IErlContextParallel;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public interface IEvlContextParallel extends IEvlContext, IErlContextParallel {

	@Override
	default void setShortCircuit(boolean shortCircuit) {
		if (shortCircuit) {
			getErrorStream().println("WARNING: short-circuiting not supported by "+getClass().getName());
		}
	}
	
	@Override
	default boolean isShortCircuiting() {
		return false;
	}
}
