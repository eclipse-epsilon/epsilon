/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.editor.contentassist;

import org.eclipse.jface.text.templates.GlobalTemplateVariables;
import org.eclipse.jface.text.templates.TemplateContextType;

public class AbstractModuleEditorContentType extends TemplateContextType {

	public static AbstractModuleEditorContentType INSTANCE = new AbstractModuleEditorContentType("","");
	
	public AbstractModuleEditorContentType(String id, String name) {
		super(id, name);
		addGlobalResolvers();
	}

	public AbstractModuleEditorContentType(String id) {
		super(id);
		addGlobalResolvers();
	}

	private void addGlobalResolvers() {
		
		addResolver(new GlobalTemplateVariables.Cursor());
		addResolver(new GlobalTemplateVariables.WordSelection());
		addResolver(new GlobalTemplateVariables.LineSelection());
		addResolver(new GlobalTemplateVariables.Dollar());
		addResolver(new GlobalTemplateVariables.Date());
		addResolver(new GlobalTemplateVariables.Year());
		addResolver(new GlobalTemplateVariables.Time());
		addResolver(new GlobalTemplateVariables.User());
		addResolver(new PreceedingWordTemplateVariableResolver());
	}

}
