/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.dt.editor.outline;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.erl.dt.editor.outline.ErlModuleContentProvider;

public class EgxModuleContentProvider extends ErlModuleContentProvider {
	
	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		
		if (moduleElement instanceof IEgxModule) {
			IEgxModule module = (IEgxModule) moduleElement;
			List<ModuleElement> visible = new ArrayList<>();
			visible = new ArrayList<>();
			visible.addAll(module.getImports());
			visible.addAll(module.getDeclaredModelDeclarations());
			visible.addAll(module.getDeclaredPre());
			visible.addAll(module.getDeclaredGenerationRules());
			visible.addAll(module.getDeclaredPost());
			visible.addAll(module.getDeclaredOperations());
			return visible;
		}
		else {
			return super.getVisibleChildren(moduleElement);
		}
	}
	
}
