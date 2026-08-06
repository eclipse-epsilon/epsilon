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

public class ToggleSortChildrenTargetsAction extends ProfilerViewAction {

	public ToggleSortChildrenTargetsAction(ProfilerView profilerView) {
		super(profilerView, "Sort children by execution time", AS_CHECK_BOX);
		setImageDescriptor(Activator.getImageDescriptor("icons/sortbytime.gif"));
	}

	@Override
	public void run() {
		profilerView.setSortChildrenTargetsByTime(!profilerView.isSortChildrenTargetsByTime());
		profilerView.refresh();
	}
	
}
