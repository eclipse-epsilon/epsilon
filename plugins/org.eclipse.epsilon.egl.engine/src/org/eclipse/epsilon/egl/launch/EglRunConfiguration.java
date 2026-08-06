/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.launch;

import org.eclipse.epsilon.eol.launch.EolRunConfiguration;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.epsilon.egl.*;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;

/**
 * 
 * @author Sina Madani
 * @since 2.1
 */
public class EglRunConfiguration extends EolRunConfiguration {

	public static class Builder<R extends EglRunConfiguration, B extends Builder<R, B>> extends EolRunConfiguration.Builder<R, B> {
		
		public Path outputRoot;
		public boolean persistOutput = true;
		
		protected Builder() {
			super();
		}
		protected Builder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
		
		@Override
		protected IEglModule createModule() {
			if (isSequential()) return new EglModule();
			throw new IllegalStateException("Parallel EGL is not supported (yet)");
		}
	}
	
	public static Builder<? extends EglRunConfiguration, ?> Builder() {
		return new Builder<>(EglRunConfiguration.class);
	}
	
	public final URI outputRoot;
	public final boolean persistOutput;
	
	public EglRunConfiguration(Builder<? extends EolRunConfiguration, ?> builder) {
		super(builder);
		Path output = builder.outputRoot;
		if (output == null) {
			output = (outputFile != null ? outputFile : script).getParent();
		}
		this.outputRoot = output.toUri();
		this.persistOutput = builder.persistOutput;
	}

	public EglRunConfiguration(EglRunConfiguration other) {
		super(other);
		this.outputRoot = other.outputRoot;
		this.persistOutput = other.persistOutput;
	}
	
	protected EglTemplateFactory getDefaultTemplateFactory() throws EglRuntimeException {
		EglTemplateFactory templateFactory;
		if (!persistOutput) {
			templateFactory = new EglTemplateFactory();
		}
		else {
			templateFactory = new EglFileGeneratingTemplateFactory(Paths.get(outputRoot));
		}
		templateFactory.setTemplateRoot(script.getParent().toUri().toString());
		return templateFactory;
	}
	
	@Override
	public void reset() throws Exception {
		getModule().reset();
		super.reset();
	}
	
	@Override
	public IEglModule getModule() {
		return (IEglModule) super.getModule();
	}
}
