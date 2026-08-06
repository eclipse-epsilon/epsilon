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

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class ToolFactory {
	
	public static boolean createTool(String name, String clazz, IEolContext context) {
		
		Object tool;
		
		try {
			tool = Class.forName(clazz).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			return false;
		}
		
		if (tool instanceof ITool) {
			((ITool) tool).setContext(context);
		}
		
		context.getFrameStack().put(Variable.createReadOnlyVariable(name, tool));
		return true;
	}
}
