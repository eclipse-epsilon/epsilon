/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.dt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.jface.text.templates.Template;

public class EvlEditorStaticTemplateContributor implements IAbstractModuleEditorTemplateContributor {
	
	List<Template> templates;
	
	@Override
	public List<Template> getTemplates() {
		if (templates == null) {
			templates = new ArrayList<>(3);
			templates.add(new Template("context", "context", "", 
					"context ${classname} {\r\n" + 
					"\t ${cursor}\r\n" + 
					"}",false));
			
			templates.add(new Template("constraint", "constraint", "", 
						"constraint ${name} {\r\n" + 
						"\tcheck : ${cursor}\r\n" + 
						"\tmessage : \r\n" +
						"}",false));
			
			templates.add(new Template("fix", "fix", "", 
					"fix {\r\n" + 
					"\ttitle : ${cursor}\r\n" + 
					"\tdo {\r\n" +
					"\t\t\r\n" +
					"\t}\r\n" +
					"}",false));
			
		}
		return templates;
	}
}
