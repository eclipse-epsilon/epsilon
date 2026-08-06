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

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;
import org.eclipse.epsilon.ewl.IEwlModule;

/**
 * 
 * @author Sina Madani
 * @since 2.0
 */
public class EwlContextParallel extends EolContextParallel implements IEwlContextParallel {

	public EwlContextParallel() {
		super();
	}

	public EwlContextParallel(int parallelism) {
		super(parallelism);
	}

	public EwlContextParallel(IEolContext other) {
		super(other);
	}

	@Override
	public IEwlModule getModule() {
		return (IEwlModule) super.getModule();
	}
}
