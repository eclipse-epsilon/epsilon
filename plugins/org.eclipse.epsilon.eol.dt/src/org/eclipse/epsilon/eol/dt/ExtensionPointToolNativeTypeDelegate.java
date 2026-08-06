/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt;

import java.util.HashMap;
import java.util.List;

import org.eclipse.epsilon.common.dt.launching.extensions.ToolExtension;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.types.AbstractToolNativeTypeDelegate;

public class ExtensionPointToolNativeTypeDelegate extends AbstractToolNativeTypeDelegate{
	
	protected HashMap<String, ToolExtension> tools = new HashMap<>();
	
	public ExtensionPointToolNativeTypeDelegate() {
		for (ToolExtension extension : ToolExtension.getInstances()) {
			tools.put(extension.getClazz(), extension);
		}
	}
	
	@Override
	public Object createInstance(String clazz, List<Object> parameters) throws EolRuntimeException {
		return tools.get(clazz).createTool(parameters);
	}

	@Override
	public boolean knowsAbout(String clazz) {
		return tools.containsKey(clazz);
	}

}
