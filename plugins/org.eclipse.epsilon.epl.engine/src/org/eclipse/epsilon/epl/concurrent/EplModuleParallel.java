/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.epl.concurrent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.execute.context.concurrent.EplContextParallel;
import org.eclipse.epsilon.epl.execute.context.concurrent.IEplContextParallel;

/**
 * Only useful for parallel first-order operations.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EplModuleParallel extends EplModule {
	
	public EplModuleParallel() {
		this(null);
	}

	public EplModuleParallel(IEplContextParallel context) {
		super(context == null ? new EplContextParallel() : context);
	}

	@Override
	public IEplContextParallel getContext() {
		return (IEplContextParallel) super.getContext();
	}
	
	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("epl", EplModuleParallel.class);
		return importConfiguration;
	}
	
	protected static final Set<String> CONFIG_PROPERTIES = new HashSet<>(2);
	static {
		CONFIG_PROPERTIES.add(IEolContextParallel.NUM_THREADS_CONFIG);
	}
	
	/**
	 * WARNING: This method should only be called by the DT plugin for initialisation purposes,
	 * as the context will be reset!
	 */
	@Override
	public void configure(Map<String, ?> properties) throws IllegalArgumentException {
		super.configure(properties);
		setContext(IEolContextParallel.configureContext(
			properties,
			EplContextParallel::new,
			getContext()
		));
	}
	
	@Override
	public Set<String> getConfigurationProperties() {
		return CONFIG_PROPERTIES;
	}
}
