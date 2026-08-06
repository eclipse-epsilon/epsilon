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

import org.eclipse.epsilon.profiling.Profiler;


public class ResetProfilerAction extends ProfilerViewAction {

	public ResetProfilerAction(ProfilerView profilerView) {
		super(profilerView);
		setText("Reset the profiler");
		setImageDescriptor(Activator.getImageDescriptor("icons/reset.gif"));
	}

	@Override
	public void run() {
		Profiler.INSTANCE.reset();
		profilerView.refresh(); 
	}
	
}
