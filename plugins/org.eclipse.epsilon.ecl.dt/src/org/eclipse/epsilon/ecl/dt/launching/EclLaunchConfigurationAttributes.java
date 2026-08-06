/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl.dt.launching;

import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;

public interface EclLaunchConfigurationAttributes extends EolLaunchConfigurationAttributes{

	public static final String LEFT_MODEL = "leftModel";
	public static final String RIGHT_MODEL = "rightModel";
	public static final String MATCHING_STRATEGY = "matchingStrategy";
	
}
