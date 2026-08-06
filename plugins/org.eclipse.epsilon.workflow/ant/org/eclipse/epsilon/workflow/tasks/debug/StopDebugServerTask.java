/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.debug;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;

public class StopDebugServerTask extends EpsilonTask {

	@Override
	public void executeImpl() throws BuildException {
		DebugServerSession session = getDebugSession();
		if (session == null || session.getServer() == null) {
			throw new BuildException("Debug server has not been started yet");
		} else {
			session.shutdown();
			log("Debug server has been shut down", Project.MSG_INFO);
		}
	}

}
