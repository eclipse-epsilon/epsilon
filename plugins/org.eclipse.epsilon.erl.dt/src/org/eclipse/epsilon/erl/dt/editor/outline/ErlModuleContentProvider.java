/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.dt.editor.outline;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleContentProvider;
import org.eclipse.epsilon.erl.dom.NamedRule;

public class ErlModuleContentProvider extends EolModuleContentProvider {
	
	@Override
	public ModuleElement getFocusedModuleElement(ModuleElement moduleElement) {
		if (moduleElement instanceof NamedRule) {
			NameExpression nameExpression = ((NamedRule) moduleElement).getNameExpression();
			if (nameExpression != null) {
				return nameExpression;
			}
			else {
				StatementBlock dummy = new StatementBlock();
				dummy.setRegion(moduleElement.getRegion().clone());
				dummy.getRegion().setEnd(dummy.getRegion().getStart());
				dummy.setUri(moduleElement.getUri());
				return dummy;
			}
		}
		
		return super.getFocusedModuleElement(moduleElement);
	}
	
}
