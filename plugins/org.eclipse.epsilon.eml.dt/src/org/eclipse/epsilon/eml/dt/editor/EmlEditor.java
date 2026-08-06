/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.dt.editor;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eml.IEmlModule;
import org.eclipse.epsilon.eml.dt.editor.outline.EmlModuleContentProvider;
import org.eclipse.epsilon.eml.dt.editor.outline.EmlModuleElementLabelProvider;
import org.eclipse.epsilon.etl.dt.editor.EtlEditor;

public class EmlEditor extends EtlEditor {

	public EmlEditor() {
		addTemplateContributor(new EmlEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		List<String> emlKeywords = new ArrayList<>();
		emlKeywords.add("merge");
		emlKeywords.add("mid");
		emlKeywords.add("with");
		emlKeywords.add("into");
		emlKeywords.addAll(super.getKeywords());
		return emlKeywords;
	}
	
	@Override
	public List<String> getBuiltinVariables() {
		ArrayList<String> emlBuiltin = new ArrayList<>();
		emlBuiltin.add("matchTrace");
		emlBuiltin.add("mergeTrace");
		emlBuiltin.add("transTrace");
		emlBuiltin.addAll(super.getBuiltinVariables());
		return emlBuiltin;
	}
	
	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EmlModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EmlModuleContentProvider();
	} 
	
	@Override
	public IEmlModule createModule() {
		return new EmlModule();
	}
}
