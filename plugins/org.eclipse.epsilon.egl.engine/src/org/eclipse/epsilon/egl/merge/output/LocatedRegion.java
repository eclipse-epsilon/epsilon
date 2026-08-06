/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.merge.output;


public abstract class LocatedRegion extends Region {
	
	private final String id;
	private final int offset;
	private final String defaultValue;
	protected RegionType type = RegionType.Regular;
	private boolean enabled;
	
	protected LocatedRegion(String id,
	                          int offset) {

		this(id, offset, true);
	}
	
	protected LocatedRegion(String id,
	                          int offset,
	                          boolean enabled) {

		this(id, offset, enabled, "");
	}
	
	protected LocatedRegion(String id,
	                          int offset,
	                          boolean enabled,
	                          String contents) {
		super(contents);
		
		if (id == null)
			throw new NullPointerException("id must not be null");
		
		this.id = id;
		this.offset = offset;
		this.enabled = enabled;
		this.defaultValue = contents;
		this.type = RegionType.Protected;
		
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public String getId() {
		return id;
	}
	
	public int getOffset() {
		return offset;
	}

	
	@Override
	public abstract String toString();
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LocatedRegion)) return false;
		
		LocatedRegion that = (LocatedRegion)o;
		
		return id.equals(that.id)     &&
		       offset  == that.offset &&
		       enabled == that.enabled &&
		       type == that.type;
	}
	
	public RegionType getType() {
		return type;
	}
	
	public void setType(RegionType type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		
		result += 37 * result + id.hashCode();
		result += 37 * result + offset;
		result += 37 * result + (enabled ? 1 : 0);

		return result;
	}
}
