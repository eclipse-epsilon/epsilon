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

import java.util.List;

import org.eclipse.epsilon.eol.execute.context.IEolContext;

public interface ITool {
	
	public void setContext(IEolContext context);
	public IEolContext getContext();
	public void initialize(List<Object> parameters);
}
