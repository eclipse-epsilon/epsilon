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

import java.util.Objects;

public class ProtectedRegion extends Content<OutputFile> {

	private final String id;
	private final boolean enabled;
	private final int offset;
	
	protected ProtectedRegion(OutputFile parent, String id, boolean enabled, int offset) {
		super(parent);
		Objects.requireNonNull(parent, "parent cannot be null");
		Objects.requireNonNull(id, "id cannot be null");
		this.id      = id;
		this.enabled = enabled;
		this.offset  = offset;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public String getId() {
		return id;
	}

	public int getOffset() {
		return offset;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProtectedRegion)) return false;
		
		final ProtectedRegion that = (ProtectedRegion)o;
		
		return id.equals(that.id)      &&
		       enabled == that.enabled &&
		       offset  == that.offset;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result += 37 * result + id.hashCode();
		result += 37 * result + (enabled ? 1 : 0);
		result += 37 * result + offset;
		return result;
	}
	
	@Override
	public String toString() {
		return id + "[" + (enabled ? "on" : "off") + "]" + "@" + offset;
	}
}
