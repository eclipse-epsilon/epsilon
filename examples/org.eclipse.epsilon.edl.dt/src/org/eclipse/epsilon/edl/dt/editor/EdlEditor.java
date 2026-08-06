/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.edl.dt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.edl.EdlModule;
import org.eclipse.epsilon.edl.dt.editor.outline.EdlModuleContentProvider;
import org.eclipse.epsilon.edl.dt.editor.outline.EdlModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;

public class EdlEditor extends EolEditor{
	
	public EdlEditor() {
		templateContributors.add(new EdlEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		
		List<String> edlKeywords = new ArrayList<String>();
		edlKeywords.add("process");
		edlKeywords.addAll(super.getKeywords());
		
		return edlKeywords;
	}
	
	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EdlModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EdlModuleContentProvider();
	}
	
	@Override
	public IModule createModule(){
		return new EdlModule();
	}
	
}
