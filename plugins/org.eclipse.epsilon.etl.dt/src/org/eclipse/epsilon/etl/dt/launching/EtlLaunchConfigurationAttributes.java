/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.dt.launching;

import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;

public interface EtlLaunchConfigurationAttributes extends EolLaunchConfigurationAttributes{
	
	public static final String SOURCE_MODEL = "sourceModel";
	public static final String TARGET_MODEL = "targetModel";
	public static final String TRANSFORMATION_STRATEGY = "transformationStrategy";
	
}
