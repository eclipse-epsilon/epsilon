/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.hosts;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.epsilon.eol.models.IModel;

public class DefaultHost implements Host {

	protected int debugPort;

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public void addNativeTypeDelegates(IEolModule module) {
		
	}

	@Override
	public void addStopCapabilities(Project project, IEolModule module) {
		
	}

	@Override
	public void initialise() {
		
	}

	@Override
	public Object debug(IEolModule module, File file) throws Exception {
		EpsilonDebugServer server = new EpsilonDebugServer(module, debugPort);
		server.run();
		return server;
	}

	@Override
	public boolean supportsDebugging() {
		return true;
	}

	@Override
	public void configureUserInput(IEolModule module, boolean isGui) {
		
	}

	@Override
	public IModel createModel(String type) throws BuildException {
		return null;
	}

	@Override
	public <T> List<T> getExtensionsOfType(Class<T> klazz) {
		return Collections.emptyList();
	}

	@Override
	public void setDebugPort(int port) {
		this.debugPort = port;
	}
	
}
