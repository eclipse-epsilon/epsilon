/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.launching;

import org.eclipse.debug.ui.actions.AbstractLaunchToolbarAction;
import org.eclipse.epsilon.coverage.dt.CoverageDtPlugin;

/**
 * The "Coverage" drop-down of the launch toolbar.
 */
public class CoverageToolbarAction extends AbstractLaunchToolbarAction {
	
	public CoverageToolbarAction() {
		super(CoverageDtPlugin.LAUNCH_GROUP_ID);
	}
}
