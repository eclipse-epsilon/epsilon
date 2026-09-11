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
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.workflow.tasks.debug.DebugServerSession;

public interface Host {
	
	public boolean isRunning();
	
	public void initialise();
	
	public void addNativeTypeDelegates(IEolModule module);
	
	/**
	 * Registers any {@link org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor}s
	 * contributed through the <code>org.eclipse.epsilon.common.dt.operationContributor</code>
	 * extension point with the operation contributor registry of the given module's context.
	 * 
	 * <p>The default implementation is a no-op, so that hosts running outside an Eclipse
	 * Platform (e.g. the {@link DefaultHost} used from plain JUnit tests) are unaffected.
	 * The {@link EclipseHost} overrides this to discover the extension point when running
	 * inside a headless or interactive Eclipse Platform, mirroring the behaviour that the
	 * interactive Development Tools launch path already provides.</p>
	 * 
	 * @since 2.7
	 */
	default void addOperationContributors(IEolModule module) {
		// No-op by default: only hosts running inside an Eclipse Platform can
		// discover extension-point-based operation contributors.
	}
	
	public void addStopCapabilities(Project project, IEolModule module);

	public boolean supportsDebugging();
	
	public Object debug(IEolModule module, File file, DebugServerSession debugSession) throws Exception;
	
	public void configureUserInput(IEolModule module, boolean isGui);

	public IModel createModel(String type) throws BuildException;

	public <T> List<T> getExtensionsOfType(Class<T> klazz) throws Exception;

	default void setDebugPort(int port) {
		// do nothing
	}
}
