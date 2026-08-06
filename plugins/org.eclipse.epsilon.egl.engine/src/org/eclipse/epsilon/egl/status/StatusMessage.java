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

import java.util.Objects;

public class StatusMessage {

	private final String message;
	
	public StatusMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(message);
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof StatusMessage))
			return false;
		
		return Objects.equals(this.message, ((StatusMessage)other).message);
	}
}
