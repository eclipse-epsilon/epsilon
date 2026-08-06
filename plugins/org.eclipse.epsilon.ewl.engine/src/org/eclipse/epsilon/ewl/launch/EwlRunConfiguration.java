/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ewl.launch;

import org.eclipse.epsilon.eol.launch.EolRunConfiguration;
import org.eclipse.epsilon.ewl.*;
import org.eclipse.epsilon.ewl.execute.context.concurrent.EwlContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 2.0
 */
public class EwlRunConfiguration extends EolRunConfiguration {

	public static class Builder<R extends EwlRunConfiguration, B extends Builder<R, B>> extends EolRunConfiguration.Builder<R, B> {
		protected Builder() {
			super();
		}
		protected Builder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
		
		@Override
		protected IEwlModule createModule() {
			if (isSequential()) return new EwlModule();
			EwlContextParallel context = new EwlContextParallel(parallelism);
			return new EwlModule(context);
		}
	}
	
	public static Builder<? extends EwlRunConfiguration, ?> Builder() {
		return new Builder<>(EwlRunConfiguration.class);
	}
	
	public EwlRunConfiguration(Builder<? extends EolRunConfiguration, ?> builder) {
		super(builder);
	}

	public EwlRunConfiguration(EwlRunConfiguration other) {
		super(other);
	}

	@Override
	public IEwlModule getModule() {
		return (IEwlModule) super.getModule();
	}
}
