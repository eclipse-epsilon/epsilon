/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.dt.editor.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleContentProvider;
import org.eclipse.epsilon.pinset.DatasetRule;
import org.eclipse.epsilon.pinset.PinsetModule;

/**
 * PinsetModuleContentProvider.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetModuleContentProvider extends EolModuleContentProvider {

	@Override
	public ModuleElement getFocusedModuleElement(ModuleElement moduleElement) {

		if (moduleElement instanceof DatasetRule) {
			return ((DatasetRule) moduleElement).getParameter();
		}

		return super.getFocusedModuleElement(moduleElement);
	}

	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		
		if (moduleElement.getClass() == PinsetModule.class) {
			PinsetModule module = (PinsetModule) moduleElement;
			List<ModuleElement> visible = new ArrayList<>();
			visible.addAll(module.getImports());
			visible.addAll(module.getDeclaredModelDeclarations());
			visible.addAll(module.getDatasetRules());
			visible.addAll(module.getDeclaredOperations());
			return visible;
		}
		else {
			return super.getVisibleChildren(moduleElement);
		}
	}

}
