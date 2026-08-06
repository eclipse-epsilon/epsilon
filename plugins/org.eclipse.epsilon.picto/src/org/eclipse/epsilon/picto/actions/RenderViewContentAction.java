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

import java.util.List;

import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;
import org.eclipse.jface.action.Action;

public class RenderViewContentAction extends Action {
		
	protected ViewContent viewContent;
	protected PictoView pictoView;
	protected List<ViewContent> viewContents;
	
	public RenderViewContentAction(ViewContent viewContent, List<ViewContent> viewContents, PictoView pictoView) {
		super(viewContent.getLabel() + " Source", AS_CHECK_BOX);
		this.viewContent = viewContent;
		this.pictoView = pictoView;
		this.viewContents = viewContents;
		this.setChecked(viewContent.isActive());
	}
	
	@Override
	public void run() {
		viewContent.setActive(true);
		this.setChecked(viewContent.isActive());
		for (ViewContent other : viewContents) {
			if (other != viewContent) {
				other.setActive(false);
			}
		}
		pictoView.getViewRenderer().display(viewContent.getSourceContent(pictoView).getText());
	}
	
}