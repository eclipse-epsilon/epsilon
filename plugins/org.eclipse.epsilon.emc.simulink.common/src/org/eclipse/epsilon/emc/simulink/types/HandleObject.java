/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.types;

import org.eclipse.epsilon.emc.simulink.engine.MatlabClassLoader;

public class HandleObject{

	private static final String HANDLE_OBJECT_MATLAB_CLASS = "com.mathworks.matlab.types.HandleObject";
	private static Class<?> handle_object_class;

	private Object handle;

	public static boolean is(Object obj) {
		return (getMatlabClass() == null) ? false : getMatlabClass().isInstance(obj);
	}

	public static Class<?> getMatlabClass() {
		if (handle_object_class == null) {
			try {
				handle_object_class = MatlabClassLoader.getInstance().loadMatlabClass(HANDLE_OBJECT_MATLAB_CLASS);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return handle_object_class;
	}

	public HandleObject(Object handle) {
		if (is(handle)) {
			this.handle = handle;
		}
	}
	
	public Object getHandleObject() {
		return handle;
	}
	
}