/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.views;

import org.eclipse.epsilon.egl.dt.EglPlugin;
import org.eclipse.epsilon.egl.traceability.Container;
import org.eclipse.epsilon.egl.traceability.OutputFile;
import org.eclipse.epsilon.egl.traceability.ProtectedRegion;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.egl.traceability.Variable;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TemplateLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof Template)
			return EglPlugin.getDefault().createImage("icons/egl.png");
		
		else if (element instanceof OutputFile)
			return EglPlugin.getDefault().createImage("icons/file.png");
		
		else if (element instanceof ProtectedRegion) {
			final ProtectedRegion pr = (ProtectedRegion)element;
			
			if (pr.isEnabled())
				return EglPlugin.getDefault().createImage("icons/preserved.png");
			
			else
				return EglPlugin.getDefault().createImage("icons/preservedoff.png");
		
		} else if (element instanceof Variable)
			return EglPlugin.getDefault().createImage("icons/variable.png");
		
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Container)
			return ((Container) element).getName();
		
		else if (element instanceof ProtectedRegion)
			return ((ProtectedRegion)element).getId();
		
		else if (element instanceof Variable) {
			final Variable var = (Variable)element;
			
			return var.getName() + " = " + var.getValue();
		}
		
		return super.getText(element);
	}

}
