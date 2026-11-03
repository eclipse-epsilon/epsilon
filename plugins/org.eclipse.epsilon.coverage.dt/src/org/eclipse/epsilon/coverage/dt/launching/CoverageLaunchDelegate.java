/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.launching;

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.debug.core.ILaunchDelegate;
import org.eclipse.epsilon.coverage.dt.CoverageDtPlugin;

/**
 * Launch delegate for the "coverage" mode of all Epsilon launch configuration types.
 * It delegates to the "run" delegate of the launch configuration's type, passing on
 * the "coverage" mode so that {@link CoverageLaunchListener} records coverage.
 */
public class CoverageLaunchDelegate implements ILaunchConfigurationDelegate2 {
	
	protected ILaunchConfigurationDelegate getRunDelegate(ILaunchConfiguration configuration) throws CoreException {
		Set<String> modes = Collections.singleton(ILaunchManager.RUN_MODE);
		ILaunchDelegate delegate = configuration.getPreferredDelegate(modes);
		if (delegate == null) delegate = configuration.getType().getPreferredDelegate(modes);
		if (delegate == null) {
			ILaunchDelegate[] delegates = configuration.getType().getDelegates(modes);
			if (delegates.length > 0) delegate = delegates[0];
		}
		if (delegate == null) {
			throw new CoreException(new Status(IStatus.ERROR, CoverageDtPlugin.PLUGIN_ID, 
				"No run launcher found for " + configuration.getType().getName()));
		}
		return delegate.getDelegate();
	}
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		getRunDelegate(configuration).launch(configuration, mode, launch, monitor);
	}
	
	@Override
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode) throws CoreException {
		ILaunchConfigurationDelegate delegate = getRunDelegate(configuration);
		return delegate instanceof ILaunchConfigurationDelegate2 ? ((ILaunchConfigurationDelegate2) delegate).getLaunch(configuration, mode) : null;
	}
	
	@Override
	public boolean buildForLaunch(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor) throws CoreException {
		ILaunchConfigurationDelegate delegate = getRunDelegate(configuration);
		return delegate instanceof ILaunchConfigurationDelegate2 ? ((ILaunchConfigurationDelegate2) delegate).buildForLaunch(configuration, mode, monitor) : true;
	}
	
	@Override
	public boolean finalLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor) throws CoreException {
		ILaunchConfigurationDelegate delegate = getRunDelegate(configuration);
		return delegate instanceof ILaunchConfigurationDelegate2 ? ((ILaunchConfigurationDelegate2) delegate).finalLaunchCheck(configuration, mode, monitor) : true;
	}
	
	@Override
	public boolean preLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor) throws CoreException {
		ILaunchConfigurationDelegate delegate = getRunDelegate(configuration);
		return delegate instanceof ILaunchConfigurationDelegate2 ? ((ILaunchConfigurationDelegate2) delegate).preLaunchCheck(configuration, mode, monitor) : true;
	}
}
