/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.module;

import java.util.List;


public interface IModuleValidator {
	
	public List<ModuleMarker> validate(IModule module);

	/**
	 * An identifier that indicates the type of marker that 
	 * should be displayed for the markers returned by this 
	 * validator. If no marker type is specified, the client
	 * will decide what type of marker to display for this
	 * validator.
	 */
	public String getMarkerType();
	
}
