/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.traceability;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public abstract class Container extends Content<Template> {

	private final String name;
	private final URI    uri;
	
	protected final Collection<Object> contents = new LinkedList<>();
	
	protected Container(Template parent, String name, URI uri) {
		super(parent);
		Objects.requireNonNull(name, "name cannot be null");
		this.name = name;
		this.uri  = uri;
	}
	
	public String getName() {
		return name;
	}
	
	public URI getURI() {
		return uri;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void add(Object child) {
		if (child == this) return;
		if (child instanceof Content) {
			((Content) child).setParent(this);
		}
		contents.add(child);
	}
	
	public Collection<?> getChildren() {
		return Collections.unmodifiableCollection(contents);
	}
	
	public boolean hasChildren() {
		return !getChildren().isEmpty();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Container)) return false;
		
		final Container that = (Container) o;
		return
			Objects.equals(this.name, that.name) &&
			Objects.equals(this.uri, that.uri) &&
			Objects.equals(this.contents, that.contents);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, uri, contents);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(name);
		if (!contents.isEmpty()) {
			sb.append(' ').append(contents);
		}
		return sb.toString();
	}
}
