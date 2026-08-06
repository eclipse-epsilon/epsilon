/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.edl.dt.launching;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.edl.EdlModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;

public class EdlLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {
	
	public EdlModule createModule() {
		return new EdlModule();
	}
	
	@Override
	protected EolDebugger createDebugger() {
		return new EdlDebugger();
	}

	@Override
	public IEolModule getDefaultModule(ILaunchConfiguration configuration) {
		return new EdlModule();
	}

	@Override
	protected String getLanguage() {
		return "EDL";
	}
	
}
