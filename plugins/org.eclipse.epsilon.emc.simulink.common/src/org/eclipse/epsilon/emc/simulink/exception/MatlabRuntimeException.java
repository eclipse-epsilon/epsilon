/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.exception;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class MatlabRuntimeException extends EolRuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MatlabRuntimeException() {
		super();
	}
	
	public MatlabRuntimeException(String reason){
		super(reason);
	}
	
	public MatlabRuntimeException(String reason, Throwable cause) {
		super(reason, cause);
	}
	
	public MatlabRuntimeException(MatlabException ex) {
		super(ex.getMessage());
	}

}
