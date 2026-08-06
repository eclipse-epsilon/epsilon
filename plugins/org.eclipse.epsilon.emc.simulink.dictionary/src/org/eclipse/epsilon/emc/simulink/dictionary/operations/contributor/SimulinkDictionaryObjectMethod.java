/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.dictionary.operations.contributor;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.emc.simulink.dictionary.model.element.ISimulinkDictionaryModelElement;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.java.ObjectMethod;

public class SimulinkDictionaryObjectMethod extends ObjectMethod {
	
	protected String name;
	protected MatlabEngine engine;
	
	public SimulinkDictionaryObjectMethod(MatlabEngine engine, Object object, String name) {
		this.engine = engine;
		this.name = name;
		assert object instanceof ISimulinkDictionaryModelElement;
		this.object = object;
	}

	@Override
	public Object execute(Object[] parameters, ModuleElement ast) throws EolRuntimeException {
		try {
			if (parameters ==null || parameters.length==0) {
				return engine.fevalWithResult(name, ((ISimulinkDictionaryModelElement) object).getHandle());
			} else {
				Object[] params = new Object[parameters.length+1];
				params[0] = ((ISimulinkDictionaryModelElement) object).getHandle();
				for (int i=0; i<parameters.length; i++) {
					params[i+1] = parameters[i];
				}
				return engine.fevalWithResult(name, params);
			}
			
		} catch (MatlabException e) {
			if (e.isTooManyOutput()) {
					try{
						if (parameters ==null || parameters.length==0) {
						engine.feval(0, name, ((ISimulinkDictionaryModelElement) object).getHandle());
						return null;
					} else {
						Object[] params = new Object[parameters.length+1];
						params[0] = ((ISimulinkDictionaryModelElement) object).getHandle();
						for (int i=0; i<parameters.length; i++) {
							params[i+1] = parameters[i];
						}
						engine.feval(0, name, params);
						return null;
					}
				} catch (MatlabException ex) {
					throw e.toEolRuntimeException(ast);
				}
			} 
			throw e.toEolRuntimeException(ast);
		}
	}

}