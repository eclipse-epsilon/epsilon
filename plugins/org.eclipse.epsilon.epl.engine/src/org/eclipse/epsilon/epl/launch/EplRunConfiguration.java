/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.epl.launch;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.IEplModule;
import org.eclipse.epsilon.epl.concurrent.EplModuleParallel;
import org.eclipse.epsilon.epl.concurrent.EplModuleParallelPatterns;
import org.eclipse.epsilon.epl.execute.context.concurrent.EplContextParallel;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;
import org.eclipse.epsilon.erl.launch.ErlRunConfiguration;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EplRunConfiguration extends ErlRunConfiguration {

	public static class Builder<R extends EplRunConfiguration, B extends Builder<R, B>> extends ErlRunConfiguration.Builder<R, B> {
		@Override
		protected IEplModule createModule() {
			if (isSequential()) return new EplModule();
			EplContextParallel context = new EplContextParallel(parallelism);
			return isParallel() ?
				new EplModuleParallelPatterns(context) :
				new EplModuleParallel(context);
		}
		
		protected Builder() {
			super();
		}
		protected Builder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
	}
	
	
	public static Builder<? extends EplRunConfiguration, ?> Builder() {
		return new Builder<>(EplRunConfiguration.class);
	}
	
	public EplRunConfiguration(ErlRunConfiguration.Builder<? extends EplRunConfiguration, ?> builder) {
		super(builder);
	}
	
	public EplRunConfiguration(EplRunConfiguration other) {
		super(other);
	}
	
	@Override
	protected PatternMatchModel execute() throws EolRuntimeException {
		return (PatternMatchModel) super.execute();
	}
	
	@Override
	public PatternMatchModel getResult() {
		return (PatternMatchModel) super.getResult();
	}
	
	@Override
	public IEplModule getModule() {
		return (IEplModule) super.getModule();
	}
}