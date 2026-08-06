/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ewl.dt.editor.outline;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleContentProvider;
import org.eclipse.epsilon.ewl.IEwlModule;

public class EwlModuleContentProvider extends EolModuleContentProvider {
	
	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		
		if (moduleElement instanceof IEwlModule) {
			IEwlModule module = (IEwlModule) moduleElement;
			List<ModuleElement> visible = new ArrayList<>();
			visible.addAll(module.getImports());
			visible.addAll(module.getDeclaredModelDeclarations());
			visible.addAll(module.getWizards());
			visible.addAll(module.getDeclaredOperations());
			return visible;
		}
		else {
			return super.getVisibleChildren(moduleElement);
		}
	}
	
}
