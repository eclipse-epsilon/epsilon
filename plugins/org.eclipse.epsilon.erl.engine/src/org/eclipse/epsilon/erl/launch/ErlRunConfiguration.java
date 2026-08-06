/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.launch;

import org.eclipse.epsilon.eol.launch.EolRunConfiguration;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.erl.IErlModule;
import org.eclipse.epsilon.erl.concurrent.ErlModuleParallel;
import org.eclipse.epsilon.erl.execute.context.concurrent.ErlContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ErlRunConfiguration extends EolRunConfiguration {
	
	public static class Builder<R extends ErlRunConfiguration, B extends Builder<R, B>> extends EolRunConfiguration.Builder<R, B> {
		
		protected Builder() {
			super();
		}
		protected Builder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
		
		@Override
		protected IErlModule createModule() {
			return isParallel() ? new ErlModuleParallel(new ErlContextParallel(parallelism)) : new ErlModule();
		}
	}
	
	public ErlRunConfiguration(Builder<? extends ErlRunConfiguration, ?> builder) {
		super(builder);
	}
	
	public ErlRunConfiguration(EolRunConfiguration other) {
		super(other);
	}
	
	@Override
	public IErlModule getModule() {
		return (IErlModule) super.getModule();
	}
}
