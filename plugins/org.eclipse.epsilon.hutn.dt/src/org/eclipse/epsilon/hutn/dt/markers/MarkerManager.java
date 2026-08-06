/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.dt.markers;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;

public class MarkerManager {

	private static final String MARKER_ID = "org.eclipse.epsilon.hutn.dt.inconsistency";
	
	private final IResource resource;
	
	public MarkerManager(IResource resource) {
		this.resource = resource;
	}
	
	public void replaceErrorMarkers(Collection<ParseProblem> problems) throws CoreException {
		removeMarkers();
		addErrorMarkers(problems);
	}
	
	private void addErrorMarkers(Collection<ParseProblem> problems) throws CoreException {
		for (ParseProblem problem : problems)
			addErrorMarker(problem);
	}
	
	private void addErrorMarker(ParseProblem problem) throws CoreException {
		addErrorMarker(problem.getReason(), problem.getLine());
	}
	
	private void addErrorMarker(String error, int line) throws CoreException {
		final IMarker marker = resource.createMarker(MARKER_ID);
		marker.setAttribute(IMarker.MESSAGE, error);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		marker.setAttribute(IMarker.LINE_NUMBER, line);
	}
	
	public void removeMarkers() throws CoreException {
		resource.deleteMarkers(MARKER_ID, true, IResource.DEPTH_ZERO);
	}
}
