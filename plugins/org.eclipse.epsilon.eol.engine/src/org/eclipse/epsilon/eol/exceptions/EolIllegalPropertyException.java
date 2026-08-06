/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.exceptions;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EolIllegalPropertyException extends EolRuntimeException {
	
	protected String property;
	protected Object object;
	
	/**
	 * 
	 * @param object
	 * @param property
	 * @param context
	 * @since 1.6
	 */
	public EolIllegalPropertyException(Object object, String property, IEolContext context) {
		this(object, property, null, context);
	}
	
	public EolIllegalPropertyException(Object object, String property, ModuleElement ast, IEolContext context) {
		super();
		this.ast = ast;
		this.object = object;
		this.property = property;
		if ((this.context = context) != null && ast == null) {
			ExecutorFactory ef = context.getExecutorFactory();
			if (ef != null) {
				this.ast = ef.getActiveModuleElement();
			}
		}
		
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	@Override
	public String getReason() {
		return "Property '" + property + "' not found in object " + (context == null ? object : context.getPrettyPrinterManager().toString(object));
	}
}
