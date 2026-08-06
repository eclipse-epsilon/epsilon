/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.dt.launching;

import org.eclipse.epsilon.ecl.dt.launching.EclLaunchConfigurationAttributes;
import org.eclipse.epsilon.etl.dt.launching.EtlLaunchConfigurationAttributes;

public interface EmlLaunchConfigurationAttributes extends EclLaunchConfigurationAttributes, EtlLaunchConfigurationAttributes{
	
	public static final String ECL_SOURCE = "source.ecl";
	//public static final String LEFT_TRANSFORMATION_STRATEGY = "transformationStrategyForLeft";
	//public static final String RIGHT_TRANSFORMATION_STRATEGY = "transformationStrategyForRight";
	//public static final String MERGING_STRATEGY = "mergingStrategy";
	//public static final String MERGED_MODEL = "mergedModel";
	
}
