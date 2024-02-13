/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.concurrent;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.concurrent.EgxContextParallel;
import org.eclipse.epsilon.egl.execute.context.concurrent.IEgxContextParallel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public abstract class EgxModuleParallel extends EgxModule {
	
	public EgxModuleParallel() {
		this((IEgxContextParallel) null);
	}
	
	public EgxModuleParallel(Path outputRoot) throws EglRuntimeException {
		this();
		if (outputRoot != null) {
			setFileGeneratingTemplateFactory(outputRoot);
		}
	}

	public EgxModuleParallel(IEgxContextParallel context) {
		super(context != null ? context : new EgxContextParallel());
	}
	
	@Override
	protected abstract Object processRules() throws EolRuntimeException;
	
	@Override
	public IEgxContextParallel getContext() {
		return (IEgxContextParallel) super.getContext();
	}
	
	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("egx", EgxModuleParallelAnnotation.class);
		return importConfiguration;
	}
	
	protected static final Set<String> CONFIG_PROPERTIES = new HashSet<>(2);
	static {
		CONFIG_PROPERTIES.add(IEolContextParallel.NUM_THREADS_CONFIG);
	}
	
	/**
	 * WARNING: This method should only be called by the DT plugin for initialization purposes,
	 * as the context will be reset!
	 */
	@Override
	public void configure(Map<String, ?> properties) throws IllegalArgumentException {
		super.configure(properties);
		IEgxContextParallel context = getContext();
		setContext(IEolContextParallel.configureContext(
			properties,
			threads -> new EgxContextParallel(context.getTemplateFactory(), threads),
			context
		));
	}
	
	@Override
	public Set<String> getConfigurationProperties() {
		return CONFIG_PROPERTIES;
	}
}
