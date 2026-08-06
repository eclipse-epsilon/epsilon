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

import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.dt.editor.IModuleParseListener;
import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.epsilon.common.dt.editor.contentassist.TemplateWithImage;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.dt.EolPlugin;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.swt.graphics.Image;

public class EolEditorBuiltinTypesTemplateContributor implements IAbstractModuleEditorTemplateContributor, IModuleParseListener {

	protected List<Template> templates = null;
	protected AbstractModuleEditor editor = null;
	
	Image typeImage = EolPlugin.getDefault().createImage("icons/type.gif");
	
	@Override
	public List<Template> getTemplates() {
		
		if (editor == null) return new ArrayList<>();
		
		if (templates == null) {
			templates = new ArrayList<>();
			for (String type : editor.getTypes()) {
				templates.add(new TemplateWithImage(type, "built-in type", "", type, false, typeImage));
			}
		}
		return templates;
	}

	@Override
	public void moduleParsed(AbstractModuleEditor editor, IModule module) {
		this.editor = editor;
	}

}
