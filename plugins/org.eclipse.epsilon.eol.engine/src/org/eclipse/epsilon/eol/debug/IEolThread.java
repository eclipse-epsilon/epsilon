/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.debug;

import org.eclipse.epsilon.eol.execute.context.FrameStack;

public interface IEolThread {

	/**
	 * Returns a unique ID for this thread, within this debugger. This
	 * ID should remain the same throughout the entire execution of the
	 * program.
	 */
	int getId();

	/**
	 * Returns a human-readable name for this thread.
	 */
	String getName();

	/**
	 * Returns the frame stack for this thread.
	 */
	FrameStack getFrameStack();

}
