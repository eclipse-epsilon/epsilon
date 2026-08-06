/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ecl.concurrent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.execute.context.concurrent.*;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;

/**
 * A no-op parallel module, useful only for extending and setting
 * threads in parallelMatches.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public abstract class EclModuleParallel extends EclModule {

	public EclModuleParallel() {
		this(null);
	}
	
	public EclModuleParallel(IEclContextParallel context) {
		super(context != null ? context : new EclContextParallel());
	}
	
	@Override
	public IEclContextParallel getContext() {
		return (IEclContextParallel) super.getContext();
	}
	
	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("ecl", EclModuleParallelAnnotation.class);
		return importConfiguration;
	}
	
	
	protected static final Set<String> CONFIG_PROPERTIES = new HashSet<>(2);
	static {
		CONFIG_PROPERTIES.add(IEolContextParallel.NUM_THREADS_CONFIG);
	}
	
	@Override
	public Set<String> getConfigurationProperties() {
		return CONFIG_PROPERTIES;
	}
	
	/**
	 * WARNING: This method should only be called by the DT plugin for initialization purposes,
	 * as the context will be reset!
	 */
	@Override
	public void configure(Map<String, ?> properties) throws IllegalArgumentException {
		super.configure(properties);
		setContext(IEolContextParallel.configureContext(properties, EclContextParallel::new, getContext()));
	}
}
