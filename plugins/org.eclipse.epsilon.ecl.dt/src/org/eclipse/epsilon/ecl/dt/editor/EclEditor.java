/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl.dt.editor;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.ecl.dt.editor.outline.EclModuleContentProvider;
import org.eclipse.epsilon.ecl.dt.editor.outline.EclModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;

public class EclEditor extends EolEditor{
		
	public EclEditor() {
		addTemplateContributor(new EclEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		List<String> eclKeywords = new ArrayList<>();
		eclKeywords.add("match");
		eclKeywords.add("auto");
		eclKeywords.add("do");
		eclKeywords.add("compare");
		eclKeywords.add("guard");
		eclKeywords.add("pre");
		eclKeywords.add("post");
		eclKeywords.add("with");
		eclKeywords.add("extends");
		eclKeywords.add("rule");
		eclKeywords.add("from");
		eclKeywords.add("abstract");
		eclKeywords.addAll(super.getKeywords());
		return eclKeywords;
	}
	
	@Override
	public List<String> getBuiltinVariables() {
		ArrayList<String> eclBuiltin = new ArrayList<>();
		eclBuiltin.add("matchTrace");
		eclBuiltin.add("autoCompare");
		eclBuiltin.add("matchInfo");
		eclBuiltin.addAll(super.getBuiltinVariables());
		return eclBuiltin;
	}
	
	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EclModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EclModuleContentProvider();
	}
	
	@Override
	public IEclModule createModule() {
		return new EclModule();
	}
}
