/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.dt.launching.tabs;

import org.eclipse.epsilon.ecl.dt.launching.tabs.EclSourceConfigurationTab;
import org.eclipse.epsilon.eml.dt.launching.EmlLaunchConfigurationAttributes;

public class EmlEclSourceConfigurationTab extends EclSourceConfigurationTab{

	@Override
	public String getSourceAttributeName() {
		return EmlLaunchConfigurationAttributes.ECL_SOURCE;
	}

	@Override
	public String getTitle() {
		return "ECL source";
	}
	
	
	
}
