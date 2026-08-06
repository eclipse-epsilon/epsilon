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

public class AutoCloseAction {
	
	protected AutoClosePair pair = null;
	protected int offset;
	
	public AutoCloseAction(AutoClosePair pair, int offset) {
		this.pair = pair;
		this.offset = offset;  
	}
	
	public boolean matches(AutoClosePair pair, int offset) {
		return getPair() == pair && offset == getOffset() + 1;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public AutoClosePair getPair() {
		return pair;
	}
	
	public void setPair(AutoClosePair pair) {
		this.pair = pair;
	}
	
	public static AutoCloseAction NoAutoCloseAction = new AutoCloseAction(null, -1);
}
