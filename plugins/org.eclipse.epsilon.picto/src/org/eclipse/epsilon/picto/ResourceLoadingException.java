/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto;

@SuppressWarnings("serial")
public class ResourceLoadingException extends Exception {

	public ResourceLoadingException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String getMessage() {
		return "";
	}
	
}
