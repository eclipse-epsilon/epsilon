/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt;

import org.eclipse.epsilon.coverage.model.CoverageSession;

public interface ICoverageSessionListener {
	
	/**
	 * Called when the current session changes (session may be null)
	 */
	public void sessionChanged(CoverageSession session);
}
