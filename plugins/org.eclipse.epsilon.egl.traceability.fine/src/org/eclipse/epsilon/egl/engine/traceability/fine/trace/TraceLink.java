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
import java.util.LinkedList;
import java.util.List;

public class TraceLink {
	
	//TODO: Make these protected in 3.0
	public ModelLocation source;
	public TextLocation destination;

	public TraceLink(ModelLocation source, TextLocation destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public ModelLocation getSource() {
		return source;
	}
	
	public TextLocation getDestination() {
		return destination;
	}
	
	public void setSource(ModelLocation source) {
		this.source = source;
	}
	
	public void setDestination(TextLocation destination) {
		this.destination = destination;
	}
	
	public Collection<? extends Object> getAllContents() {
		final List<Object> allContents = new LinkedList<>();
		allContents.add(this);
		allContents.addAll(source.getAllContents());
		allContents.addAll(destination.getAllContents());
		return allContents;
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TraceLink))
			return false;
		
		final TraceLink other = (TraceLink)object;
		
		return source.equals(other.source) &&
		       destination.equals(other.destination);
	}

	@Override
	public int hashCode() {
		return source.hashCode() + destination.hashCode();
	}
	
	@Override
	public String toString() {
		return "<TraceLink source:" + source + ", destination:" + destination + ">"; 
	}
}
