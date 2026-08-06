/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt;

/**
 * The main plugin class to be used in the desktop.
 */
public class EpsilonCommonsPlugin extends AbstractEpsilonUIPlugin {
	
	public static final String PLUGIN_ID = "org.eclipse.epsilon.common.dt";

	public static EpsilonCommonsPlugin getDefault() {
		return (EpsilonCommonsPlugin) plugins.get(EpsilonCommonsPlugin.class);
	}

}
