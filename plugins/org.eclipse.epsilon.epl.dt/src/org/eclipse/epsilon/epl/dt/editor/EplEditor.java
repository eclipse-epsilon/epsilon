/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.dt.editor;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.IEplModule;
import org.eclipse.epsilon.epl.dt.editor.outline.EplModuleContentProvider;
import org.eclipse.epsilon.epl.dt.editor.outline.EplModuleElementLabelProvider;

public class EplEditor extends EolEditor {
	
	public EplEditor() {
		this.addTemplateContributor(new EplEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		List<String> keywords = new ArrayList<>(12);
		keywords.add("pre");
		keywords.add("post");
		keywords.add("pattern");
		keywords.add("match");
		keywords.add("guard");
		keywords.add("do");
		keywords.add("onmatch");
		keywords.add("nomatch");
		keywords.add("from");
		keywords.add("no");
		keywords.add("optional");
		keywords.add("active");
		keywords.addAll(super.getKeywords());
		return keywords;
	}

	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EplModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EplModuleContentProvider();
	}
	
	@Override
	public IEplModule createModule() {
		return new EplModule();
	}
}
