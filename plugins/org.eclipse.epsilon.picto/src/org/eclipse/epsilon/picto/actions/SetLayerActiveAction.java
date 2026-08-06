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

import org.eclipse.epsilon.picto.Layer;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.jface.action.Action;

class SetLayerActiveAction extends Action {
		
	protected Layer layer;
	protected PictoView pictoView;
	
	public SetLayerActiveAction(Layer layer, PictoView pictoView) {
		super(layer.getTitle(), AS_CHECK_BOX);
		this.setChecked(layer.isActive());
		this.layer = layer;
		this.pictoView = pictoView;
	}
	
	@Override
	public void run() {
		layer.setActive(!layer.isActive());
		this.setChecked(layer.isActive());
		pictoView.getActiveView().clearCache();
		
		try {
			pictoView.renderView(pictoView.getActiveView());
		} catch (Exception ex) {
			pictoView.getViewRenderer().display(ex);
		}
	}
	
}