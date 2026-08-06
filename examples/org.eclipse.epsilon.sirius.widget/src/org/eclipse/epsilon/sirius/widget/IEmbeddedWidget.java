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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface IEmbeddedWidget { 
	
	public void setVariableManager(IVariableManager variableManager);
	
	public void setEditingContextAdapter(EditingContextAdapter editingContextAdapter);
	
	public void load();
	
	public void save(String text);
	
	public void createControl(Composite parent);
	
	public Control getControl();
	
	public String getText();
	
	public void setEnabled(boolean enabled);
	
	public String getLanguageName();
	
	public void dispose();

}
