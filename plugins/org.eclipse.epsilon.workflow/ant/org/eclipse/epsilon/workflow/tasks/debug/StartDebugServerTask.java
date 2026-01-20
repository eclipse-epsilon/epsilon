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

public class StartDebugServerTask extends EpsilonTask {

	private String host = null;
	private int port = 0;

	@Override
	public void executeImpl() throws BuildException {
		DebugServerSession session = getDebugSession();
		if (session != null && session.getServer() != null) {
			log("Debug server has already been started", Project.MSG_WARN);
		} else {
			session = new DebugServerSession(host, port);
			setDebugSession(session);

			try {
				session.start();

				if (host == null) {
					log(String.format(
						"Debug server listening on port %d",
						session.getServer().getPort()
					), Project.MSG_INFO);
				} else {
					log(String.format(
						"Debug server listening on %s:%d",
						session.getServer().getHost(),
						session.getServer().getPort()
					), Project.MSG_INFO);
				}
			} catch (InterruptedException e) {
				throw new BuildException(e);
			}
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
