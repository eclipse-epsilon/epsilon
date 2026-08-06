/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dt.editor.outline;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.dom.StatementBlock;

public class EolModuleContentProvider extends ModuleContentProvider {
	
	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		List<ModuleElement> visible = new ArrayList<>();
		
		if (moduleElement instanceof IEolModule) {
			IEolModule module = (IEolModule) moduleElement;
			visible.addAll(module.getImports());
			visible.addAll(module.getDeclaredModelDeclarations());
			if (module.getMain() != null) {
				visible.add(module.getMain());
			}
			visible.addAll(module.getDeclaredOperations());
		}
		else if (moduleElement instanceof Import) {
			Import imp = (Import) moduleElement;
			visible.addAll(getVisibleChildren(imp.getImportedModule()));
		}
		
		return visible;
	}
	
	@Override
	public ModuleElement getFocusedModuleElement(ModuleElement moduleElement) {
		
		if (moduleElement instanceof StatementBlock) {
			StatementBlock focused = new StatementBlock();
			focused.setRegion(moduleElement.getRegion().clone());
			focused.getRegion().setEnd(focused.getRegion().getStart());
			focused.setUri(moduleElement.getUri());
			return focused;
		}
		else if (moduleElement instanceof Operation) {
			return ((Operation) moduleElement).getNameExpression();
		}
		else if (moduleElement instanceof Import) {
			return ((Import) moduleElement).getPathLiteral();
		}
		else if (moduleElement instanceof ModelDeclaration) {
			return ((ModelDeclaration) moduleElement).getNameExpression();
		}
		
		return null;
	}
	
}
