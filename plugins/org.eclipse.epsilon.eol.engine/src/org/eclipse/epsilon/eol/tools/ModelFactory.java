/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.tools;

import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.models.IModel;

public class ModelFactory extends AbstractTool {

	public ModelFactory() {
		super();
	}
	
	public IModel createModel(String name, String clazz) throws EolRuntimeException {
		IModel model = null;

		try {
			
			model = (IModel) Thread.currentThread().getContextClassLoader().loadClass(clazz).getDeclaredConstructor().newInstance();
			model.setName(name);
			context.getModelRepository().addModel(model);
		} 
		catch (Exception e) {
			throw new EolInternalException(e);
		} 
		
		return model;
	}
}
