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

public class ToggleShowAggregatedWorkAction extends ProfilerViewAction {

	public ToggleShowAggregatedWorkAction(ProfilerView profilerView) {
		super(profilerView, "Show aggregated execution time", AS_CHECK_BOX);
		setImageDescriptor(Activator.getImageDescriptor("icons/aggregatedtime.gif"));
		setChecked(true);
	}

	@Override
	public void run() {
		profilerView.setShowAggregatedWork(!profilerView.isShowAggregatedWork());
		profilerView.refresh();
	}
	
}
