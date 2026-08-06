/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.eol.dt.debug;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;

public class EolEditorAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof AbstractModuleEditor) {
			return (T) new EolBreakpointAdapter();
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[]{IToggleBreakpointsTarget.class};
	}
}
 
