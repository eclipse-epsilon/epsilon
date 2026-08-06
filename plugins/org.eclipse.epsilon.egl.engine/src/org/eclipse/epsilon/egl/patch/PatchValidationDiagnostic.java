/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.patch;

/**
 * 
 * @since 1.6
 */
public class PatchValidationDiagnostic {
	
	protected Line line;
	protected String reason;
	
	public PatchValidationDiagnostic(Line line, String reason) {
		super();
		this.line = line;
		this.reason = reason;
	}

	public Line getLine() {
		return line;
	}
	
	public void setLine(Line line) {
		this.line = line;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}
