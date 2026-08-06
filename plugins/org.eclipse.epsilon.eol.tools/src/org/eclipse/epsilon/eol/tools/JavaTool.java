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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JavaTool {
	
	public Object getNull() {
		return null;
	}
		
	//TODO : Add parameter names too
	public List<String> getMethodSignatures(Object o) {
		List<String> signatures = new ArrayList<>();
		
		if (o == null) return signatures;
		
		for (Method m : o.getClass().getMethods()) {
			signatures.add(getSignature(m));
		}
		
		return signatures;
	}
	
	protected String getSignature(Method m) {
		String signature = m.getName() + "(";
		for (Class<?> c : m.getParameterTypes()) {
			signature = signature + c.getName() + ", ";
		}
		signature = signature + ")";
		return signature;
	}
	
	public Class<?> loadClass(String clazz) {
		try {
			return ClassLoader.getSystemClassLoader().loadClass(clazz);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
}
