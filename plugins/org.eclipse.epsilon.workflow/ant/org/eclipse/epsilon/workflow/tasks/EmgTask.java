/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import org.eclipse.epsilon.emg.EmgModule;
import org.eclipse.epsilon.epl.IEplModule;

public class EmgTask extends EplTask {
	
	protected Long seed;
	
	@Override
	protected IEplModule createDefaultModule() {
		EmgModule module = new EmgModule();
		if (seed != null) {
			module.setUseSeed(true);
			module.setSeed(seed);
		}
		return module;
	}
	
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	public long getSeed() {
		return seed;
	}
	
}
