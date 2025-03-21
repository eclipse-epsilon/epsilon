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
import java.util.stream.Collectors;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.dt.editor.IModuleParseListener;
import org.eclipse.epsilon.common.dt.editor.contentassist.IAbstractModuleEditorTemplateContributor;
import org.eclipse.epsilon.common.dt.editor.contentassist.TemplateWithImage;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.dt.EolPlugin;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.swt.graphics.Image;

public class EolEditorOperationTemplateContributor implements IAbstractModuleEditorTemplateContributor, IModuleParseListener {

	protected List<Template> templates = new ArrayList<>();
	
	@Override
	public void moduleParsed(AbstractModuleEditor editor, IModule module) {
		templates.clear();
		if (module == null || !(module instanceof IEolModule)) return;
		for (Operation op : ((IEolModule) module).getOperations()) {
			templates.add(createTemplate(op));
		}
	}

	@Override
	public List<Template> getTemplates() {
		return templates;
	}
	
	Image operationImage = EolPlugin.getDefault().createImage("icons/operation.gif");
	
	protected Template createTemplate(Operation op) {	
		String call = op.getName() + "("
			+ op.getFormalParameters().stream()
			.filter(fp -> fp != null)
			.map(fp -> "${" + fp.getName() + "}")
			.collect(Collectors.joining(", "))
			+ ")";	
		return new TemplateWithImage(op.toString(), "operation", "", call, false, operationImage);
	}
	
}
