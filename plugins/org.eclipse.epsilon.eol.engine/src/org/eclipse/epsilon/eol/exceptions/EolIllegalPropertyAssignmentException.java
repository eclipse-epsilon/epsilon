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

public class EolIllegalPropertyAssignmentException extends EolRuntimeException {

	public String property;
	
	/**
	 * 
	 * @param property
	 * @param context
	 * @since 1.6
	 */
	public EolIllegalPropertyAssignmentException(String property, IEolContext context) {
		this.property = property;
		if ((this.context = context) != null) {
			ExecutorFactory ef = context.getExecutorFactory();
			if (ef != null) this.ast = ef.getActiveModuleElement();
		}
	}
	
	public EolIllegalPropertyAssignmentException(String property, ModuleElement ast) {
		this.property = property;
		this.ast = ast;
	}
	
	@Override
	public String getReason(){
		return "Invalid assignment to property '" + property + "'";
	}
	
}
