/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.types;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.models.IModel;

public abstract class AbstractType implements ISimulinkModelElement {

	protected Object object;
	protected MatlabEngine engine;
	
	public AbstractType() {}
	public AbstractType(MatlabEngine engine) {
		this.engine = engine;
	}
	
	protected abstract void init();
	protected abstract Object getObject();
	
	@Override
	public IModel getOwningModel() {
		return null;
	}

	@Override
	public MatlabEngine getEngine() {
		return engine;
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList(getType());
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		return true; 
	}

	@Override
	public String getType() {
		return getClass().getName();
	}

	@Override
	public Object getHandle() {
		return getObject();
	}

}
