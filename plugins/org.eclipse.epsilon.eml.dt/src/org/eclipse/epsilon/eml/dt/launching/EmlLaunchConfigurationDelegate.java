/*******************************************************************************
 * Copyright (c) 2008-2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eml.dt.launching;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;

public class EmlLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {

	/**
	 * The language provided by the plugin. It allows other plugins to contribute
	 * alternate IModule implementation of the language.
	 * @since 1.6
	 */
	public String getLanguage() {
		return "EML";
	}

	class EclEmlModule extends EolModule {
		EclModule eclModule = new EclModule();
		EmlModule emlModule = new EmlModule();

		@Override
		public URI getUri() {
			try {
				return new URI("dummy-ecl-eml-module");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public Object executeImpl() throws EolRuntimeException {
			// Use the ECL model repository for this combo module (to ensure proper disposal)
			getContext().setModelRepository(eclModule.getContext().getModelRepository());

			// Copy over execution listeners to the ECL and EML modules (for debugging)
			final Collection<IExecutionListener> eclListeners = getContext().getExecutorFactory().getExecutionListeners();
			eclListeners.forEach(l -> eclModule.getContext().getExecutorFactory().addExecutionListener(l));
			eclListeners.forEach(l -> emlModule.getContext().getExecutorFactory().addExecutionListener(l));

			// Execute the ECL module first
			context.getExecutorFactory().execute(eclModule, context);

			// Bring over the results from the ECL module
			emlModule.getContext().setMatchTrace(eclModule.getContext().getMatchTrace().getReduced());
			emlModule.getContext().setModelRepository(eclModule.getContext().getModelRepository());

			return emlModule.execute();
		}
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor)
			throws CoreException {
		super.launch(configuration, mode, launch, progressMonitor, new EclEmlModule(),
			null, EolLaunchConfigurationAttributes.SOURCE, true, true);
	}

	@Override
	protected void setupModule(ILaunchConfiguration configuration, ILaunch launch, IProgressMonitor progressMonitor, IEolModule module, boolean setup) throws Exception {
		// Setup context for combo module (to allow printing errors from the EML/ECL modules to the Eclipse console)
		super.setupModule(configuration, launch, progressMonitor, module, false);

		// Load the models for ECL
		final boolean loadModelsECL = true;
		super.setupModule(configuration, launch, progressMonitor, ((EclEmlModule) module).eclModule, loadModelsECL);

		// Don't load them for EML (we'll reuse the model repository from ECL in EML)
		final boolean loadModelsEML = false;
		super.setupModule(configuration, launch, progressMonitor, ((EclEmlModule) module).emlModule, loadModelsEML);
	}

	@Override
	protected boolean parse(IModule module, String sourceAttribute, ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor) throws CoreException {
		// Parse the nested ECL module as well
		return super.parse(((EclEmlModule)module).eclModule, EmlLaunchConfigurationAttributes.ECL_SOURCE, configuration, mode, launch, progressMonitor)
			&& super.parse(((EclEmlModule)module).emlModule, sourceAttribute, configuration, mode, launch, progressMonitor);
	}
	
	@Override
	public IEolModule getDefaultModule(ILaunchConfiguration configuration) {
		try {
			return ModuleImplementationExtension.defaultImplementation(getLanguage()).createModule();
		} catch (CoreException e) {
		}
		return null;
	}
	
}
