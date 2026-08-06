/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.editor.outline;

import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.dt.EglPlugin;
import org.eclipse.epsilon.erl.dt.editor.outline.ErlModuleElementLabelProvider;
import org.eclipse.swt.graphics.Image;

public class EgxModuleElementLabelProvider extends ErlModuleElementLabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof GenerationRule) {
			return EglPlugin.getDefault().createImage("icons/generationrule.gif");
		} 
		else {
			return super.getImage(element);
		}
	}
	
	
}
