/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.parse;

import java.util.Objects;

public final class Region implements Cloneable, java.io.Serializable {
	
	/**
	 * @since 1.6
	 */
	private static final long serialVersionUID = 7112437532509719424L;
	
	protected Position start;
	protected Position end;
	
	public Region() {
		this(0, 0, 0, 0);
	}
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @since 1.6
	 */
	public Region(Position start, Position end) {
		this.start = start;
		this.end = end;
	}
	
	public Region(int startLine, int startColumn, int endLine, int endColumn) {
		this(new Position(startLine, startColumn), new Position(endLine, endColumn));
	}
	
	public Position getStart() {
		return start;
	}
	
	public Position getEnd() {
		return end;
	}
	
	public void setStart(Position start) {
		this.start = start;
	}
	
	public void setEnd(Position end) {
		this.end = end;
	}
	
	@Override
	public Region clone() {
		Region clone;
		try {
			clone = (Region) super.clone();
		}
		catch (CloneNotSupportedException cnsx) {
			throw new UnsupportedOperationException(cnsx);
		}
		if (this.start != null) clone.start = this.start.clone();
		if (this.end != null) clone.end = this.end.clone();
		return clone;
	}
	
	@Override
	public String toString() {
		return start + "-" + end;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Region))
			return false;
		
		Region region = (Region) other;
		return
			Objects.equals(this.start, region.start) &&
			Objects.equals(this.end, region.end);
	}
}
