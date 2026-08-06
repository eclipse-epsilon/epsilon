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

public final class Position implements Cloneable, java.io.Serializable {
	
	/**
	 * @since 1.6
	 */
	private static final long serialVersionUID = 6556794941272597738L;
	
	protected int line, column;
	
	public Position() {}
	
	public Position(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public int getLine() {
		return line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public boolean isBefore(Position position) {
		return getLine() < position.getLine() ||
			(getLine() == position.getLine() && getColumn() < position.getColumn());
	}
	
	public boolean isAfter(Position position) {
		return !isBefore(position);
	}
	
	@Override
	public String toString() {
		return line + ":" + column;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	protected Position clone() {
		Position clone;
		try {
			clone = (Position) super.clone();
		}
		catch (CloneNotSupportedException cnsx) {
			throw new UnsupportedOperationException(cnsx);
		}
		clone.line = this.line;
		clone.column = this.column;
		return clone;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(line, column);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Position))
			return false;
		
		Position pos = (Position) other;
		return
			this.column == pos.column &&
			this.line == pos.line;
	}
}
