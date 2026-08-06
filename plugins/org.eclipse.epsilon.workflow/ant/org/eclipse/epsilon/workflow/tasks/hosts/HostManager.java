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

import java.util.ArrayList;
import java.util.List;

public class HostManager {
	
	protected static Host host = null;
	protected static final ArrayList<Host> supportedHosts = new ArrayList<>();
	
	public static Host getHost() {
		if (host == null) {
			host = createHost();
		}
		return host;
	}
	
	public static void setHost(Host host) {
		HostManager.host = host;
	}
	
	protected static Host createHost() {
		try {
			Host eclipseHost = (Host)
				Class.forName(HostManager.class.getPackage().getName() + ".EclipseHost")
					.getConstructor().newInstance();
			supportedHosts.add(eclipseHost);
		}
		catch (Throwable t) {
			// Means we're not running in Eclipse
		}
		
		for (Host host : supportedHosts) {
			if (host.isRunning()) return host;
		}
		
		return new DefaultHost();
	}
	
	public static List<Host> getSupportedHosts() {
		return supportedHosts;
	}
	
}
