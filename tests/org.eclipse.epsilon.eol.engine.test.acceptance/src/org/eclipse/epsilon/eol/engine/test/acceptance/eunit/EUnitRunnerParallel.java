/*********************************************************************
 * Copyright (c) 2019-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.eunit;

import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;
import org.eclipse.epsilon.eunit.EUnitModule;

/**
 * EUnitRunner which uses parallel EOL.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EUnitRunnerParallel extends EUnitRunner {

	public EUnitRunnerParallel(Class<?> clazz) {
		super(clazz);
	}
	
	@Override
	protected EUnitModule newModule() {
		return new EUnitModule(new EolContextParallel());
	}
}
