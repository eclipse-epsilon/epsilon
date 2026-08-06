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
import org.eclipse.epsilon.picto.PictoPlugin;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

public class LayersMenuAction extends Action implements IMenuCreator {
	
	protected PictoView pictoView;
	
	public LayersMenuAction(PictoView pictoView) {
		super("Layers", AS_DROP_DOWN_MENU);
		setImageDescriptor(PictoPlugin.getDefault().getImageDescriptor("icons/layer.gif"));
		setMenuCreator(this);
		this.pictoView = pictoView;
	}

	@Override
	public void dispose() {}

	@Override
	public Menu getMenu(Control parent) {
		
		if (pictoView.getActiveView() == null) return null;
		
		Menu layersMenu = new Menu(parent);
		
		for (Layer layer : pictoView.getActiveView().getLayers()) {
			ActionContributionItem item= new ActionContributionItem(new SetLayerActiveAction(layer, pictoView));
			item.fill(layersMenu, -1);
		}
		
		return layersMenu;
	}

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}
}