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
import org.eclipse.epsilon.common.dt.editor.contentassist.TemplateWithImage;
import org.eclipse.epsilon.eol.dt.EolPlugin;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.swt.graphics.Image;

public class EolEditorStaticOperationTemplateContributor implements IAbstractModuleEditorTemplateContributor {

	private List<Template> templates;
	
	Image operationImage = EolPlugin.getDefault().createImage("icons/operation.gif");
	
	@Override
	public List<Template> getTemplates() {
		if (templates == null) {
			templates = new ArrayList<>();
			templates.add(new TemplateWithImage("select(iterator|condition)", "select items from a collection", "", "select(${iterator}|${condition})",false, operationImage));
			templates.add(new TemplateWithImage("selectOne(iterator|condition)", "selects one item from a collection", "", "selectOne(${iterator}|${condition})",false, operationImage));
			templates.add(new TemplateWithImage("forAll(iterator|condition)", "verify that condition holds for all items in a collection", "", "forAll(${iterator}|${condition})",false, operationImage));
			templates.add(new TemplateWithImage("exists(iterator|condition)", "checks if there exists an item in the collection that satisfies the condition", "", "exists(${iterator}|${condition})",false, operationImage));
			templates.add(new TemplateWithImage("println()", "print to the output stream", "", "println()",false, operationImage));
		}
		return templates;
	}

}
