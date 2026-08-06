/*******************************************************************************
 * Copyright (c) 2012 The University of York.
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

import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.jface.text.templates.Template;

public class EmlEditorStaticTemplateContributor implements IAbstractModuleEditorTemplateContributor {
	
	List<Template> templates = null;
	public List<Template> getTemplates() {
		if (templates == null) {
			templates = new ArrayList<>();
			templates.add(new Template("merge", "merge rule", "", "rule ${rulename} \r\n\tmerge l : ${leftmodel}!${lefttype}\r\n\twith r : ${rightmodel}!${righttype}\r\n\tinto t : ${targetmodel}!${targettype} {\r\n\t${cursor}\r\n}",false));
			templates.add(new Template("common merge", "merge rule", "", "rule ${Type} \r\n\tmerge l : Left!${Type}\r\n\twith r : Right!${Type}\r\n\tinto t : Target!${Type} {\r\n\t${cursor}\r\n}",false));
		}
		return templates;
	}
}
