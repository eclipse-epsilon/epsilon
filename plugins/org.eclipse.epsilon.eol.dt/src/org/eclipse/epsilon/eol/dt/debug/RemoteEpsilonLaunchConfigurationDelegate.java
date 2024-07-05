/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dt.debug;

import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.lsp4e.debug.debugmodel.TransportStreams;
import org.eclipse.lsp4e.debug.launcher.DSPLaunchDelegate;

@SuppressWarnings("restriction")
public class RemoteEpsilonLaunchConfigurationDelegate extends DSPLaunchDelegate {

	@Override
	protected IDebugTarget createDebugTarget(SubMonitor subMonitor,
			Supplier<TransportStreams> streamsSupplier, ILaunch launch,
			Map<String, Object> dspParameters)
			throws CoreException {
		final EpsilonConsoleDebugTarget target = new EpsilonConsoleDebugTarget(launch, streamsSupplier, dspParameters);
		target.initialize(subMonitor.split(80));
		return target;
	}

}
