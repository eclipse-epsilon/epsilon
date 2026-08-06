/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.control;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public interface IExecutionListener {

	public void aboutToExecute(ModuleElement ast, IEolContext context);
	
	/**
	 * @param result the result of evaluating ast, calculated during execution
	 */
	public void finishedExecuting(ModuleElement ast, Object result, IEolContext context);
	
	public void finishedExecutingWithException(ModuleElement ast, EolRuntimeException exception, IEolContext context);
	
}
