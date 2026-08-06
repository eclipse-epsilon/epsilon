/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.ExecutionController;

public class EclipseExecutionController implements ExecutionController {
	
	protected Timer timer;
	protected boolean terminated = false;
	
	public EclipseExecutionController(final IProgressMonitor monitor) {
		TimerTask pollMonitorTask = new TimerTask() {
			@Override
			public void run() {
				setTerminated(monitor.isCanceled());
			}
			
		};
		timer = new Timer();
		timer.schedule(pollMonitorTask, 0l, 200l);
	}
	
	@Override
	public void control(ModuleElement ast, IEolContext context) {	}
	
	protected void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}
	
	@Override
	public boolean isTerminated() {
		return terminated;
	}

	@Override
	public void report(IEolContext context) {
		
	}

	@Override
	public void dispose() {
		timer.cancel();
	}

	@Override
	public void done(ModuleElement ast, IEolContext context) {
		// do nothing
	}
	
}
