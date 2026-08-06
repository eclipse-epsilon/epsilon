/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.editor.contentassist;

import java.util.List;

import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;

public class AbstractModuleEditorCompletionProcessor extends TemplateCompletionProcessor {
	
	IRegion region = null;
	ITextViewer viewer = null;
	AbstractModuleEditor editor = null;
	
	public AbstractModuleEditorCompletionProcessor(AbstractModuleEditor editor) {
		this.editor = editor;
	}
	
	@Override
	protected TemplateContextType getContextType(ITextViewer viewer,
			IRegion region) {
		this.region = region;
		this.viewer = viewer;
		return AbstractModuleEditorContentType.INSTANCE;
	}
	
	protected int getIndent() {
		int indent = 0;
		try {
			int lineNumber = viewer.getDocument().getLineOfOffset(
					region.getOffset());
			
			IRegion lineInfo = viewer.getDocument().getLineInformation(lineNumber);
			
			String line = viewer.getDocument().get(lineInfo.getOffset(), region.getOffset() - lineInfo.getOffset());
			
			for (char c : line.toCharArray()) {
				if (c == '\t') {
					indent ++;
				}
				else {
					break;
				}
			}
		}
		catch (Exception ex) {
			// log exception
		}
		return indent;
	}
	
	Image templateImage = EpsilonCommonsPlugin.getDefault().createImage("icons/template.gif");
	@Override
	protected Image getImage(Template template) {
		if (template instanceof TemplateWithImage) {
			return ((TemplateWithImage) template).getImage();
		}
		return templateImage;
	}

	@Override
	protected Template[] getTemplates(String contextTypeId) {
		
		List<Template> templates = editor.getTemplates();
		Template[] indented = new Template[templates.size()];
		
		int loopCount = 0;
		for (Template template : templates) {
			indented[loopCount] = indentTemplate(template);
			loopCount++;
		}
		return indented;
	}
	
	protected Template indentTemplate(Template original) {
		if (original instanceof TemplateWithImage) {
			return new TemplateWithImage(original.getName(), original.getDescription(), original.getContextTypeId(), addIndent(original.getPattern(),getIndent()), original.isAutoInsertable(), ((TemplateWithImage) original).getImage());
		}
		return new Template(original.getName(), original.getDescription(), original.getContextTypeId(), addIndent(original.getPattern(),getIndent()), original.isAutoInsertable());
	}
	
	protected String addIndent(String text, int indent) {
		
		String tabs = "";
		for (int i=0;i<indent;i++) {
			tabs = tabs + "\t";
		}
		
		int loopCount = 0;
		String indented = "";
		String[] parts = text.split("\r\n");
		for (String line : parts) {
			if (loopCount == 0) {
				indented = line;
			}
			else {
				indented = indented + tabs + line;
			}
			if (loopCount < parts.length - 1) {
				indented = indented + "\r\n";
			}
			loopCount ++;
		}
		
		return indented;
	}
	
}
