/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.coverage.CoverageRecorder;
import org.eclipse.epsilon.coverage.dt.CoverageDtPlugin;
import org.eclipse.epsilon.coverage.dt.CoverageSessionManager;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegateListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * Records coverage information for launches in the "coverage" mode and
 * publishes it to the {@link CoverageSessionManager}
 * when the execution completes (successfully or not).
 */
public class CoverageLaunchListener implements EpsilonLaunchConfigurationDelegateListener {
	
	protected CoverageRecorder recorder;
	
	@Override
	public void aboutToParse(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor, IEolModule module) throws CoreException {}
	
	@Override
	public void aboutToExecute(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor, IEolModule module) throws Exception {
		if (CoverageDtPlugin.COVERAGE_MODE.equals(mode)) {
			recorder = CoverageRecorder.attach(module);
		}
	}
	
	@Override
	public void executed(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor, IEolModule module, Object result) throws Exception {
		publish(configuration, module);
	}
	
	@Override
	public void executionFailed(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor, IEolModule module, Exception exception) throws Exception {
		publish(configuration, module);
	}
	
	protected void publish(ILaunchConfiguration configuration, IEolModule module) throws CoreException {
		if (recorder == null) return;
		recorder.detachFrom(module);
		
		CoverageSession session = recorder.createCoverageSession(configuration.getName());
		session.setLaunchConfiguration(configuration.getName());
		recorder = null;
		
		CoverageSessionManager.getInstance().setSession(session);
		
		Display.getDefault().asyncExec(() -> {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(CoverageDtPlugin.VIEW_ID);
			}
			catch (Exception ex) {
				LogUtil.log(ex);
			}
		});
	}
}
