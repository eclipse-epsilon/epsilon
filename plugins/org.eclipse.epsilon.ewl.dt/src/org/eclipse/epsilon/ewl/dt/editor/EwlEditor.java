/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.dt.editor;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.ewl.EwlModule;
import org.eclipse.epsilon.ewl.IEwlModule;
import org.eclipse.epsilon.ewl.dt.editor.outline.EwlModuleContentProvider;
import org.eclipse.epsilon.ewl.dt.editor.outline.EwlModuleElementLabelProvider;

public class EwlEditor extends EolEditor{
		
	public EwlEditor() {
		this.addTemplateContributor(new EwlEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		List<String> keywords = new ArrayList<>();
		keywords.add("wizard");
		keywords.add("guard");
		keywords.add("do");
		keywords.add("title");
		keywords.addAll(super.getKeywords());
		return keywords;
	}
	
	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EwlModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EwlModuleContentProvider();
	}
	
	@Override
	public IEwlModule createModule() {
		return new EwlModule();
	}
}
