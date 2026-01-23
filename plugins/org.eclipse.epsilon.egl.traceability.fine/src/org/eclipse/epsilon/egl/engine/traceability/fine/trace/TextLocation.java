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

public class TextLocation {

	//TODO: Make these protected in 3.0
	public Region region;
	public String resource;
	
	public TextLocation(Region region, String resource) {
		this.region = region;
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public Collection<? extends Object> getAllContents() {
		final List<Object> allContents = new LinkedList<>();
		allContents.add(this);
		allContents.add(region);
		return allContents;
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TextLocation))
			return false;
		
		final TextLocation other = (TextLocation)object;
		
		return (resource == null ? other.resource == null : resource.equals(other.resource)) &&
		       region.equals(other.region);
	}

	@Override
	public int hashCode() {
		return (resource == null ? 0 : resource.hashCode()) + region.hashCode();
	}
	
	@Override
	public String toString() {
		final String resourceName = resource == null ? "unknown" : resource;
		return resourceName + "@" + region; 
	}
}
