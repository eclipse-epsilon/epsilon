/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.recording;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PropertyAccesses implements IPropertyAccesses {

	private List<IPropertyAccess> storage = new LinkedList<>();
	
	public PropertyAccesses(IPropertyAccess... propertyAccesses) {
		storage.addAll(Arrays.asList(propertyAccesses));
	}
	
	@Override
	public Collection<? extends IPropertyAccess> all() {
		// defensive copy
		return new LinkedList<>(storage);
	}
	
	@Override
	public Set<? extends IPropertyAccess> unique() {
		return new HashSet<>(storage);
	}

	public void add(IPropertyAccess propertyAccess) {
		storage.add(propertyAccess);
	}
	
	public boolean isEmpty() {
		return storage.isEmpty();
	}
	
	public void clear() {
		storage.clear();
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof PropertyAccesses)) return false;
		final PropertyAccesses other = (PropertyAccesses)object;
		return Objects.equals(this.storage, other.storage);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(storage);
	}
	
	@Override
	public String toString() {
		return Objects.toString(storage);
	}
}
