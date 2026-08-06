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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.IEglModule;
import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleContentProvider;

public class EglModuleContentProvider extends EolModuleContentProvider {
	
	@SuppressWarnings("restriction")
	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		
		if (moduleElement instanceof IEglModule) {
			IEglModule module = (IEglModule) moduleElement;
			List<ModuleElement> visible = new ArrayList<>();
			visible.addAll(emptyListIfNull(module.getImports()));
			visible.addAll(emptyListIfNull(module.getDeclaredModelDeclarations()));
			visible.addAll(emptyListIfNull(module.getCurrentTemplate().getModule().getMarkers()));
			visible.addAll(emptyListIfNull(module.getDeclaredOperations()));
			return visible;
		}
		else {
			return super.getVisibleChildren(moduleElement);
		}
	}
	
	// Some of EglModule's collection-returning operations return null 
	// instead of an empty collection if there's a parse error. 
	// This method protects getVisibleChildren from NPEs in such cases.
	protected Collection<? extends ModuleElement> emptyListIfNull(Collection<? extends ModuleElement> elements) {
		return elements == null ? Collections.emptyList() : elements;
	}
	
}
