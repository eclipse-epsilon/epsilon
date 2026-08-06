/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.dt.editor.outline;

import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleElementLabelProvider;
import org.eclipse.epsilon.ewl.dom.Wizard;
import org.eclipse.epsilon.ewl.dt.EwlPlugin;
import org.eclipse.swt.graphics.Image;

public class EwlModuleElementLabelProvider extends EolModuleElementLabelProvider{

	@Override
	public Image getImage(Object element) {
		
		if (element instanceof Wizard){
			return EwlPlugin.getDefault().createImage("icons/wizard.png");
		}
		/*else if (element instanceof EvlConstraintContext) {
			return EwlPlugin.getDefault().createImage("icons/context.gif");
		} 
		else if (element instanceof EvlConstraint) {
			if (((EwlConstraint) element).isCritique()) {
				return EwlPlugin.getDefault().createImage("icons/critique.gif");
			}
			else {
				return EwlPlugin.getDefault().createImage("icons/error.gif");
			}
		} */
		else {
			return super.getImage(element);
		}
	}

}
