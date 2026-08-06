/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.launching;

import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;

public interface FlockLaunchConfigurationAttributes extends EolLaunchConfigurationAttributes{
	
	public static final String ORIGINAL_MODEL = "originalModel";
	public static final String MIGRATED_MODEL = "migratedModel";	
}
