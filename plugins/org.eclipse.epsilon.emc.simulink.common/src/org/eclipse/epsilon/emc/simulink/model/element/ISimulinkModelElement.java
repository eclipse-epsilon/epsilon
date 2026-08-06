/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.model.element;

import java.util.Collection;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.models.IModelElement;

public interface ISimulinkModelElement extends IModelElement {

	public Object getProperty(String property) throws EolRuntimeException ;

	public void setProperty(String property, Object value) throws EolRuntimeException;

	public MatlabEngine getEngine();

	public boolean equals(Object other);
	
	public Collection<String> getAllTypeNamesOf();
	
	public boolean deleteElementInModel() throws EolRuntimeException;

	public String getType(); 

	Object getHandle();

}
