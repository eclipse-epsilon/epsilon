/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.editor;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.dt.editor.outline.EgxModuleContentProvider;
import org.eclipse.epsilon.egl.dt.editor.outline.EgxModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;

public class EgxEditor extends EolEditor {
	
	public EgxEditor() {
		this.addTemplateContributor(new EgxEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		List<String> keywords = new ArrayList<>(13);
		keywords.add("transform");
		keywords.add("rule");
		keywords.add("guard");
		keywords.add("pre");
		keywords.add("post");
		keywords.add("target");
		keywords.add("extends");
		keywords.add("parameters");
		keywords.add("template");
		keywords.add("overwrite");
		keywords.add("protectRegions");
		keywords.add("merge");
		keywords.add("append");
		keywords.add("patch");
		keywords.add("formatter");
		keywords.addAll(super.getKeywords());
		return keywords;
	}
	
	
	@Override
	public List<String> getBuiltinVariables() {	
		ArrayList<String> builtIn = new ArrayList<>();
		builtIn.add("generated");
		builtIn.addAll(super.getBuiltinVariables());
		return builtIn;
	}

	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EgxModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EgxModuleContentProvider();
	}
	
	@Override
	public IEgxModule createModule() {
		return new EgxModule();
	}
}
