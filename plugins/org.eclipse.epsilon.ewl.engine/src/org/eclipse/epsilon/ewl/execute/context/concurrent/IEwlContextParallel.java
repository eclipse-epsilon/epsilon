/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ewl.execute.context.concurrent;

import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;
import org.eclipse.epsilon.ewl.IEwlModule;
import org.eclipse.epsilon.ewl.execute.context.IEwlContext;

/**
 * 
 * @author Sina Madani
 * @since 2.0
 */
public interface IEwlContextParallel extends IEwlContext, IEolContextParallel {

	@Override
	default IEwlModule getModule() {
		return IEwlContext.super.getModule();
	}
}
