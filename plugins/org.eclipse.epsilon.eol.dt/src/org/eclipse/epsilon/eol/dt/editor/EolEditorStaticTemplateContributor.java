/*******************************************************************************
 * Copyright (c) 2012 The University of York.
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

import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.jface.text.templates.Template;

public class EolEditorStaticTemplateContributor implements IAbstractModuleEditorTemplateContributor {

	private List<Template> templates;

	@Override
	public List<Template> getTemplates() {
		if (templates == null) {
			templates = new ArrayList<>();
			templates.add(new Template("function", "function with return type", "", "function ${context} ${name} () : ${returntype} {\r\n\t${cursor}\r\n}",false));
			templates.add(new Template("operation", "operation with return type", "", "operation ${context} ${name} () : ${returntype} {\r\n\t${cursor}\r\n}",false));
			templates.add(new Template("for", "iterate over collection", "", "for (${iterator} in ${collection}) { \r\n\t${cursor}\r\n}",true));
			templates.add(new Template("forp", "iterate over collection and print something", "", "for (${iterator} in ${collection}) { \r\n\t(${cursor}).println();\r\n}",true));
			templates.add(new Template("while", "while loop with condition", "", "while (${condition}) {\r\n\t${cursor}\r\n}",false));
			templates.add(new Template("if", "if branch", "", "if (${condition}) {\r\n\t${cursor}\r\n}",false));
			templates.add(new Template("if else", "if branch with else part", "", "if (${condition}) {\r\n\t${cursor}\r\n}\r\nelse {\r\n\t${cursor}\r\n}",false));
			templates.add(new Template("assert", "assertion", "", "assert(${condition}, ${message});",false));
			templates.add(new Template("assertError", "assertion of error", "", "assertError(${expression}, ${message});",false));
			templates.add(new Template("import", "import", "", "import \"${filename}\";",false));
			templates.add(new Template("var", "variable declaration", "", "var ${name} : ${type} = ${initial_value};",false));				
		}
		return templates;
	}

}
