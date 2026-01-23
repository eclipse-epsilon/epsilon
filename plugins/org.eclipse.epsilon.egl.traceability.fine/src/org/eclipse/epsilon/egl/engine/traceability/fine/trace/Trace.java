/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.engine.traceability.fine.trace;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Trace {

	//TODO: Make these protected in 3.0
	public List<TextLocation> locations = new LinkedList<>();
	public Set<TraceLink> traceLinks = new LinkedHashSet<>();
	public String destination;
	
	public Set<TraceLink> getTraceLinks() {
		return traceLinks;
	}
	
	public List<TextLocation> getLocations() {
		return locations;
	}

	public Collection<? extends Object> getAllContents() {
		final List<Object> allContents = new LinkedList<>();
		allContents.add(this);
	
		for (TraceLink traceLink : traceLinks) {
			allContents.addAll(traceLink.getAllContents());
		}
		
		for (TextLocation location : locations) {
			allContents.addAll(location.getAllContents());
		}
		
		return allContents;
	}
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String attribute) {
		this.destination = attribute;
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Trace))
			return false;
		
		final Trace other = (Trace)object;
		
		return locations.equals(other.locations) &&
		       traceLinks.equals(other.traceLinks) &&
		       (destination == null ? other.destination == null : destination.equals(other.destination));
	}

	@Override
	public int hashCode() {
		return locations.hashCode() +
		       traceLinks.hashCode() +
		       (destination == null ? 0 : destination.hashCode());
	}
	
	@Override
	public String toString() {
		return "<Trace # of links:" + traceLinks.size() + ", # of locations: " + locations.size() + " destination:" + destination + ">"; 
	}
}
