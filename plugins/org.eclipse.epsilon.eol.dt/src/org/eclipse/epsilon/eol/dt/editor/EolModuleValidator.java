/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dt.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleValidator;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.eol.EolModule;

public class EolModuleValidator extends AbstractModuleValidator {
	
	@Override
	public List<ModuleMarker> validate(IModule module) {
		if (!appliesTo(module)) return Collections.emptyList();
		
		ArrayList<ModuleMarker> markers = new ArrayList<>();
		/*
		// Fix for bug #393988
		AST firstHelper = null;
		
		for (AST ast : module.getAst().getChildren()) {
			if (ast.getType() != EolParser.HELPERMETHOD && ast.getType() != EolParser.BLOCK && ast.getType() != EolParser.ANNOTATIONBLOCK) {
				if (firstHelper != null) {
					markers.add(new ModuleMarker(null, ast.getRegion(), "All statements after the first operation will be ignored at runtime.", Severity.Warning));
				}
			}
			else if (ast.getType() == EolParser.HELPERMETHOD) {
				if (firstHelper == null) firstHelper = ast;
			}
		}*/
		
		return markers;
	}
	
	protected boolean appliesTo(IModule module) {
		return module.getClass() == EolModule.class;
	}
}
