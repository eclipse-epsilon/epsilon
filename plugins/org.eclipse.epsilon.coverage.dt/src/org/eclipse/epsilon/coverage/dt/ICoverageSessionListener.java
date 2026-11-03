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
	 * Called when sessions are added or removed
	 */
	public void sessionsChanged();
	
	/**
	 * Called when the active session changes (session may be null)
	 */
	public void activeSessionChanged(CoverageSession session);
	
	/**
	 * Called when editor highlighting is switched on or off
	 */
	public default void highlightingChanged(boolean enabled) {}
}
