/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.emf.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

public interface IEvlMarkerResolver {
	
	public boolean canResolve(IMarker marker);
	
	public EObject resolve(IMarker marker);
	
	public String getAbsoluteElementId(IMarker marker);
	
	public String getMessage(IMarker marker);
	
	public EditingDomain getEditingDomain(IMarker marker);
	
	public void run(IMarker marker, EvlMarkerResolution resolution);
	
}
