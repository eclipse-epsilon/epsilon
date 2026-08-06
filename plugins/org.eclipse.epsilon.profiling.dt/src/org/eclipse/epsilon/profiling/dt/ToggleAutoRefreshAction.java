/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.profiling.dt;

public class ToggleAutoRefreshAction extends ProfilerViewAction {

	public ToggleAutoRefreshAction(ProfilerView profilerView) {
		super(profilerView, "Auto refresh", AS_CHECK_BOX);
		setImageDescriptor(Activator.getImageDescriptor("icons/autorefresh.gif"));
	}
	
	@Override
	public void run() {
		profilerView.setAutoRefresh(!profilerView.isAutoRefresh());
	}
}
