/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi;

import java.io.IOException;

public class FlexmiParseException extends IOException {
	
	protected int lineNumber = 1;
	
	public FlexmiParseException(Throwable t) {
		super(t);
	}
	
	public FlexmiParseException(Throwable t, int lineNumber) {
		super(t);
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	
	
}
