/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.edl.dt.editor.outline;

import org.eclipse.epsilon.edl.ProcessRule;
import org.eclipse.epsilon.edl.dt.EdlPlugin;
import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleElementLabelProvider;
import org.eclipse.swt.graphics.Image;

public class EdlModuleElementLabelProvider extends EolModuleElementLabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof ProcessRule) {
			return EdlPlugin.getDefault().createImage("icons/process-rule.png");
		} else {
			return super.getImage(element);
		}
	}
	
	@Override
	public String getText(Object element) {
		if (element instanceof ProcessRule) {
			ProcessRule processRule = (ProcessRule) element;
			return processRule.getParameter().getName() + " : " + 
				processRule.getParameter().getTypeName();
		}
		return super.getText(element);
	}
}
