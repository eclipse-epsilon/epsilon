/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.edl.dt.editor.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.edl.EdlModule;
import org.eclipse.epsilon.edl.ProcessRule;
import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleContentProvider;

public class EdlModuleContentProvider extends EolModuleContentProvider {
	
	@Override
	public ModuleElement getFocusedModuleElement(ModuleElement moduleElement) {
		
		if (moduleElement instanceof ProcessRule) {
			return ((ProcessRule) moduleElement).getParameter();
		}
		
		return super.getFocusedModuleElement(moduleElement);
	}
	
	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		
		if (moduleElement.getClass() == EdlModule.class) {
			List<ModuleElement> visible = new ArrayList<>();
			EdlModule module = (EdlModule) moduleElement;
			visible.addAll(module.getImports());
			visible.addAll(module.getDeclaredModelDeclarations());
			visible.addAll(module.getDeclaredProcessRules());
			visible.addAll(module.getDeclaredOperations());
			return visible;
		}
		else {
			return super.getVisibleChildren(moduleElement);
		}
		
	}
	
}
