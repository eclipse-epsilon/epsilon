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

import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ForwardAction extends Action {
	
	protected PictoView pictoView = null;
	
	public ForwardAction(PictoView pictoView) {
		setText("Forward");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		this.pictoView = pictoView;
	}
	
	@Override
	public void run() {
		if (pictoView.getViewTreeSelectionHistory().canGoForward()) pictoView.getViewTreeSelectionHistory().goForward();
	}
}