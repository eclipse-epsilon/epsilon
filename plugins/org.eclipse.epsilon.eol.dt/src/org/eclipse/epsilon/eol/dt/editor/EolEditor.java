/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.editor;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleContentProvider;
import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleElementLabelProvider;

public class EolEditor extends AbstractModuleEditor {

	public static final String ID = "org.eclipse.epsilon.eol.dt.editor.EolEditor";
	
	public EolEditor() {
		this.addTemplateContributor(new EolEditorStaticTemplateContributor());
		this.addTemplateContributor(new EolEditorStaticOperationTemplateContributor());
		
		EolEditorOperationTemplateContributor operationTemplateContributor = new EolEditorOperationTemplateContributor();
		addTemplateContributor(operationTemplateContributor);
		addModuleParsedListener(operationTemplateContributor);
		EolEditorPropertyTemplateContributor propertyTemplateContributor = new EolEditorPropertyTemplateContributor();
		addTemplateContributor(propertyTemplateContributor);
		addModuleParsedListener(propertyTemplateContributor);
		EolEditorBuiltinTypesTemplateContributor builtinTypesTemplateContributor = new EolEditorBuiltinTypesTemplateContributor();
		addTemplateContributor(builtinTypesTemplateContributor);
		addModuleParsedListener(builtinTypesTemplateContributor);
		EolEditorTokenTemplateContributor tokenTemplateContributor = new EolEditorTokenTemplateContributor();
		addTemplateContributor(tokenTemplateContributor);
		addModuleParsedListener(tokenTemplateContributor);		
	}
	
	@Override
	public List<String> getKeywords() {
		ArrayList<String> keywords = new ArrayList<>(33);

		keywords.add("import");
		keywords.add("driver");
		keywords.add("alias");
		keywords.add("if");
		keywords.add("switch");
		keywords.add("case");
		keywords.add("default");
		keywords.add("operation");
		keywords.add("function");
		keywords.add("new");
		keywords.add("else");
		keywords.add("for");
		keywords.add("var");
		keywords.add("return");
		keywords.add("async");
		keywords.add("break");
		keywords.add("breakAll");
		keywords.add("and");
		keywords.add("or");
		keywords.add("not");
		keywords.add("xor");
		keywords.add("implies");
		keywords.add("ext");
		keywords.add("in");
		keywords.add("continue");
		keywords.add("while");
		keywords.add("throw");
		keywords.add("delete");
		keywords.add("transaction");
		keywords.add("abort");
		keywords.add("model");
		keywords.add("group");
		keywords.add("as");
		
		return keywords;
	}

	@Override
	public List<String> getBuiltinVariables() {
		ArrayList<String> builtIn = new ArrayList<>(7);
		
		builtIn.add("hasMore");
		builtIn.add("loopCount");
		builtIn.add("self");
		builtIn.add("true");
		builtIn.add("false");
		builtIn.add("null");

		return builtIn;
	}

	@Override
	public IModule createModule() {
		return new EolModule();
	}

	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EolModuleElementLabelProvider();
	}

	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EolModuleContentProvider();
	}
	
	@Override
	protected boolean supportsHyperlinks() {
		return true;
	}

	@Override
	protected boolean supportsDirtyTextParsing() {
		return true;
	}
}
