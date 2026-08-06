/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.concurrent;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.execute.context.concurrent.EtlContextParallel;
import org.eclipse.epsilon.etl.execute.context.concurrent.IEtlContextParallel;

/**
 * No-op ETL module, useful for taking advantage of parallel operations only.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EtlModuleParallel extends EtlModule {

	public EtlModuleParallel() {
		this(null);
	}

	public EtlModuleParallel(IEtlContextParallel context) {
		super(context != null ? context : new EtlContextParallel());
	}

	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		//getContext().setTransformationStrategy(new ParallelElementsTransformationStrategy());
	}
	
	@Override
	public IEtlContextParallel getContext() {
		return (IEtlContextParallel) super.getContext();
	}
}
