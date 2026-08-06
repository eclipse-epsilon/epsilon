/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.actions;

import org.eclipse.epsilon.picto.PictoPlugin;
import org.eclipse.epsilon.picto.ViewRenderer;
import org.eclipse.epsilon.picto.ViewRenderer.ZoomType;
import org.eclipse.jface.action.Action;

public class ZoomAction extends Action {
		
	protected ZoomType type;
	protected ViewRenderer viewRenderer;
		
	public ZoomAction(ZoomType type, ViewRenderer viewRenderer) {
		this.type = type;
		this.viewRenderer = viewRenderer;
		if (type == ZoomType.IN) {
			setText("Zoom In");
			setImageDescriptor(PictoPlugin.getDefault().getImageDescriptor("icons/zoomin.gif"));
		}
		else if (type == ZoomType.OUT){
			setText("Zoom Out");
			setImageDescriptor(PictoPlugin.getDefault().getImageDescriptor("icons/zoomout.gif"));	
		}
		else {
			setText("Reset");
			setImageDescriptor(PictoPlugin.getDefault().getImageDescriptor("icons/zoomactual.gif"));		
		}
	}
	
	@Override
	public void run() {
		viewRenderer.zoom(type);
	}
}