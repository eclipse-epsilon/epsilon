/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.locators;

public interface IModelElementLocator {
	
	public final static String EXTENSION_POINT = "org.eclipse.epsilon.common.dt.modelElementLocator";
	
	public boolean canLocate(Object o);
	
	public void locate(Object o);
	
}
