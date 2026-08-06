/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.editor.autoclose;

public class AutoClosePair {
	
	protected char left;
	protected char right;
	
	public AutoClosePair(char left, char right) {
		this.left = left;
		this.right = right;
	}
	
	public char getLeft() {
		return left;
	}
	
	public char getRight() {
		return right;
	}
	
	public boolean isSame() {
		return getLeft() == getRight();
	}
	
	public boolean isDifferent() {
		return !isSame();
	}
	
}
