/*********************************************************************
* Copyright (c) 2021 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.sirius.widget;

import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;

public abstract class AbstractEmbeddedWidget implements IEmbeddedWidget {
	
	protected IVariableManager variableManager;
	protected EditingContextAdapter editingContextAdapter;
	
	@Override
	public void setVariableManager(IVariableManager variableManager) {
		this.variableManager = variableManager;	
	}
	
	@Override
	public void setEditingContextAdapter(EditingContextAdapter editingContextAdapter) {
		this.editingContextAdapter = editingContextAdapter;
	}

}
