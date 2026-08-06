/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.dt.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.dt.editor.outline.EvlModuleContentProvider;
import org.eclipse.epsilon.evl.dt.editor.outline.EvlModuleElementLabelProvider;

public class EvlEditor extends EolEditor {
	
	public EvlEditor() {
		this.addTemplateContributor(new EvlEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		List<String>
			superKeywords = super.getKeywords(),
			keywords = new ArrayList<>(superKeywords.size()+16);
		
		keywords.add("context");
		keywords.add("constraint");
		keywords.add("guard");
		keywords.add("pre");
		keywords.add("post");
		keywords.add("assumes");
		keywords.add("critique");
		keywords.add("message");
		keywords.add("title");
		keywords.add("do");
		keywords.add("check");
		keywords.add("fix");
		keywords.add("typeOf");
		keywords.add("kindOf");
		keywords.add("high");
		keywords.add("medium");
		keywords.add("low");

		keywords.addAll(superKeywords);
		
		return keywords;
	}
	
	@Override
	public List<String> getBuiltinVariables() {
		Collection<String> superBuiltIns = super.getBuiltinVariables();
		ArrayList<String> builtIn = new ArrayList<>(superBuiltIns.size()+2);
		builtIn.add("constraintTrace");
		builtIn.add("extras");
		builtIn.addAll(superBuiltIns);
		return builtIn;
	}

	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EvlModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EvlModuleContentProvider();
	}
	
	@Override
	public IEvlModule createModule() {
		return new EvlModule();
	}
}
