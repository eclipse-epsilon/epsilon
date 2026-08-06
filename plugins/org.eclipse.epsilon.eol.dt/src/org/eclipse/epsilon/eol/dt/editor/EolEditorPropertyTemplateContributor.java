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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.dt.editor.IModuleParseListener;
import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.epsilon.common.dt.editor.contentassist.TemplateWithImage;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.dt.EolPlugin;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.swt.graphics.Image;

public class EolEditorPropertyTemplateContributor implements IAbstractModuleEditorTemplateContributor, IModuleParseListener {
	
	protected List<Template> templates = new ArrayList<>();
	protected Set<String> properties = new HashSet<>();
	Image propertyImage = EolPlugin.getDefault().createImage("icons/property.gif");
	
	@Override
	public List<Template> getTemplates() {
		return templates;
	}

	@Override
	public void moduleParsed(AbstractModuleEditor editor, IModule module) {
		templates.clear();
		properties.clear();
		findProperties(module);
		for (String property : properties) {
			templates.add(new TemplateWithImage(property, "property", "", property, false, propertyImage));
		}
	}
	
	protected void findProperties(ModuleElement root) {
		if (root instanceof PropertyCallExpression) {
			properties.add(((PropertyCallExpression) root).getNameExpression().getName());
		}
		for (ModuleElement child : root.getChildren()) {
			findProperties(child);
		}
		
	}
	
}
