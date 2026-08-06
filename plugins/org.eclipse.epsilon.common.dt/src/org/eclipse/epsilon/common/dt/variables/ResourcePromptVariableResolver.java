/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.variables;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IDynamicVariableResolver;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class ResourcePromptVariableResolver implements IDynamicVariableResolver {

	@Override
	public String resolveValue(IDynamicVariable variable, String argument) throws CoreException {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		final StringWrapper location = new StringWrapper();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				location.value =  
					BrowseWorkspaceUtil.browseFile
					(null, "Select a resource", "Select a resource", "", null).
					getLocation().toString();
			}
		});
		return location.value;
	}
	
	class StringWrapper {
		public String value = "";
	}
}
