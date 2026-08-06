/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.status;

public class Warning extends StatusMessage {

	public Warning(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return "Warning: " + getMessage();
	}
}
