/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.profiling.dt;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegateListener;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.profiling.Profiler;
import org.eclipse.epsilon.profiling.ProfilingExecutionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class ProfilingLaunchConfigurationListener implements EpsilonLaunchConfigurationDelegateListener {
	
	protected boolean profilingEnabled = false;
	protected boolean resetProfiler = false;
	protected boolean fineGrainedProfiling = false;
	protected boolean profileModelLoading = false;
	

	@Override
	public void aboutToParse(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module) throws CoreException {
		
		profilingEnabled = configuration.getAttribute(ProfilingConfigurationTab.PROFILING_ENABLED, false);
		resetProfiler = configuration.getAttribute(ProfilingConfigurationTab.RESET_PROFILER, false);
		fineGrainedProfiling = configuration.getAttribute(ProfilingConfigurationTab.FINE_GRAINED_PROFILING, false);
		profileModelLoading = configuration.getAttribute(ProfilingConfigurationTab.PROFILE_MODEL_LOADING, profilingEnabled);
		
		if (profilingEnabled && profileModelLoading) {
			if (resetProfiler) {
				Profiler.INSTANCE.reset();
			}
			Profiler.INSTANCE.start("Model loading");
		}
		
		Profiler.INSTANCE.setContext(module.getContext());
	}

	@Override
	public void aboutToExecute(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module) throws Exception {
		
		
		if (profilingEnabled) {
			if (profileModelLoading) {
				Profiler.INSTANCE.stop();
			}
			if (resetProfiler && !profileModelLoading) {
				Profiler.INSTANCE.reset();
			}
			
			IEolContext context = module.getContext();
			Profiler.INSTANCE.setContext(context);
			context.setProfilingEnabled(true);
			
			Profiler.INSTANCE.start(configuration.getAttribute(EolLaunchConfigurationAttributes.SOURCE, ""));
			
			if (fineGrainedProfiling) {
				context.getExecutorFactory().addExecutionListener(new ProfilingExecutionListener());
			}
		}
	}

	@Override
	public void executed(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module, Object result) throws Exception {
		
		if (profilingEnabled) {
			
			Profiler.INSTANCE.stop(configuration.getAttribute(EolLaunchConfigurationAttributes.SOURCE, ""));
			
			Profiler.INSTANCE.refresh();
			
			Display.getDefault().asyncExec(() -> {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.epsilon.profiling.dt.ProfilerView");
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		}
	}

}
