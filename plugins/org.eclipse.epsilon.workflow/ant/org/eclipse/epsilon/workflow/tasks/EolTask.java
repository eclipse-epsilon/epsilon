/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;

public class EolTask extends ExecutableModuleTask {
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected IEolModule createDefaultModule() throws InstantiationException, IllegalAccessException {
		return new EolModule();
	}

	@Override
	protected void examine() throws Exception {
		
	}
	
}
