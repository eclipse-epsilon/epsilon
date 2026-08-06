/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.dt.editor;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.dt.editor.outline.EtlModuleContentProvider;
import org.eclipse.epsilon.etl.dt.editor.outline.EtlModuleElementLabelProvider;

public class EtlEditor extends EolEditor{
	
	public EtlEditor() {
		this.addTemplateContributor(new EtlEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		List<String> keywords = new ArrayList<>();
		keywords.add("transform");
		keywords.add("auto");
		keywords.add("guard");
		keywords.add("pre");
		keywords.add("post");
		keywords.add("to");
		keywords.add("extends");
		keywords.add("rule");
		keywords.add("abstract");
		keywords.addAll(super.getKeywords());
		return keywords;
	}
	
	@Override
	public List<String> getBuiltinVariables() {
		ArrayList<String> builtIn = new ArrayList<>();
		builtIn.add("transTrace");
		builtIn.addAll(super.getBuiltinVariables());
		return builtIn;
	}

	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EtlModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EtlModuleContentProvider();
	}
	
	@Override
	public IEtlModule createModule() {
		return new EtlModule();
	}
}
