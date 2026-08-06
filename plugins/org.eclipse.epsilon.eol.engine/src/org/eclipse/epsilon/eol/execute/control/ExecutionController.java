/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.control;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public interface ExecutionController {
	
	public void control(ModuleElement ast, IEolContext context);

	public void done(ModuleElement ast, IEolContext context);
	
	public boolean isTerminated();
	
	public void report(IEolContext context);
	
	public void dispose();
}
