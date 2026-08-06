/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.extensions.fineGrainedTracePostprocessor;

import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace;

public interface IFineGrainedTracePostprocessor {

	/**
	 * Process the fine-grained trace from the EGL execution context
	 * in some manner.
	 * 
	 * The trace is shared between all clients. Consequently,
	 * implementations of this method must not alter the trace.
	 */
	public void postprocess(Trace trace);

}
