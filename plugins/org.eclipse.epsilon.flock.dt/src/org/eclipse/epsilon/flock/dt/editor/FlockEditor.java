/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.editor;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.IFlockModule;
import org.eclipse.epsilon.flock.dt.editor.outline.FlockModuleContentProvider;
import org.eclipse.epsilon.flock.dt.editor.outline.FlockModuleElementLabelProvider;

public class FlockEditor extends EolEditor {
		
	public FlockEditor() {
		this.addTemplateContributor(new FlockEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		final List<String> keywords = new ArrayList<>(super.getKeywords());
		keywords.add("delete");
		keywords.add("retype");
		keywords.add("to");
		keywords.add("migrate");
		keywords.add("when");
		keywords.add("ignoring");
		keywords.add("package");
		keywords.add("pre");
		keywords.add("post");
		return keywords;
	}
	
	@Override
	public List<String> getBuiltinVariables() {
		final List<String> builtIn = new ArrayList<>(super.getBuiltinVariables());
		builtIn.add("original");
		builtIn.add("migrated");
		return builtIn;
	}
	
	@Override
	public IFlockModule createModule() {
		return new FlockModule();
	}
	
	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new FlockModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new FlockModuleContentProvider();
	}
}
