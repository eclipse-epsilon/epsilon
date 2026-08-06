/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.dt.editor.outline;

import org.eclipse.epsilon.eml.dom.MergeRule;
import org.eclipse.epsilon.eml.dt.EmlPlugin;
import org.eclipse.epsilon.etl.dt.editor.outline.EtlModuleElementLabelProvider;
import org.eclipse.swt.graphics.Image;

public class EmlModuleElementLabelProvider extends EtlModuleElementLabelProvider {

	public EmlModuleElementLabelProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Image getImage(Object element) {
		
		if (element instanceof MergeRule){
			return EmlPlugin.getDefault().createImage("icons/mergerule.gif");
		}
		
		return super.getImage(element);
	}

}
