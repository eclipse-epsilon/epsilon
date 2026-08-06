/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Saheed Popoola - initial API and implementation
 *     Horacio Hoyos - aditional functionality
 ******************************************************************************/
package org.eclipse.epsilon.emg.dt;

import org.eclipse.epsilon.epl.dt.EplPlugin;

public class EmgPlugin extends EplPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.epsilon.emg.dt";
		
	public static EmgPlugin getDefault() {
	     return (EmgPlugin) plugins.get(EmgPlugin.class);
	}
	
	public static int getRandomSeed() {
		return (int) (System.currentTimeMillis() + System.identityHashCode(EmgPlugin.class));
	}
	
}
