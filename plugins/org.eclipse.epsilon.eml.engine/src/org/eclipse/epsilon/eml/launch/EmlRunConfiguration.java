/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eml.launch;

import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eml.IEmlModule;
import org.eclipse.epsilon.etl.launch.EtlRunConfiguration;

/**
 * 
 * @author Sina Madani
 * @since 2.0
 */
public class EmlRunConfiguration extends EtlRunConfiguration {

	public static class Builder<R extends EmlRunConfiguration, B extends Builder<R, B>> extends EtlRunConfiguration.Builder<R, B> {

		protected Builder() {
			super();
		}
		protected Builder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
		
		@Override
		protected IEmlModule createModule() {
			return new EmlModule();
		}
	}
	
	public static Builder<? extends EmlRunConfiguration, ?> Builder() {
		return new Builder<>(EmlRunConfiguration.class);
	}
	
	public EmlRunConfiguration(Builder<? extends EtlRunConfiguration, ?> builder) {
		super(builder);
	}
	
	public EmlRunConfiguration(EmlRunConfiguration other) {
		super(other);
	}

	@Override
	public IEmlModule getModule() {
		return (IEmlModule) super.getModule();
	}
}
