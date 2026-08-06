/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ecl.launch;

import org.eclipse.epsilon.ecl.*;
import org.eclipse.epsilon.ecl.concurrent.EclModuleParallelAnnotation;
import org.eclipse.epsilon.ecl.execute.context.concurrent.EclContextParallel;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.launch.ErlRunConfiguration;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EclRunConfiguration extends ErlRunConfiguration {

	public static class Builder<R extends EclRunConfiguration, B extends Builder<R, B>> extends ErlRunConfiguration.Builder<R, B> {
		protected Builder() {
			super();
		}
		protected Builder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
		
		@Override
		protected IEclModule createModule() {
			if (isSequential()) return new EclModule();
			EclContextParallel context = new EclContextParallel(parallelism);
			return new EclModuleParallelAnnotation(context);
		}
	}
	
	public static Builder<? extends EclRunConfiguration, ?> Builder() {
		return new Builder<>(EclRunConfiguration.class);
	}
	
	public EclRunConfiguration(Builder<? extends EclRunConfiguration, ?> builder) {
		super(builder);
	}
	
	public EclRunConfiguration(EclRunConfiguration other) {
		super(other);
	}

	@Override
	public IEclModule getModule() {
		return (IEclModule) super.getModule();
	}
	
	@Override
	public MatchTrace getResult() {
		return (MatchTrace) super.getResult();
	}
	
	@Override
	protected MatchTrace execute() throws EolRuntimeException {
		return (MatchTrace) super.execute();
	}
	
	@Override
	protected void postExecute() throws Exception {
		getResult().toString(getModule().getContext());
		super.postExecute();
	}
}
