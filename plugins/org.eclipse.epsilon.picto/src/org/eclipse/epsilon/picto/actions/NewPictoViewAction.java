/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.actions;

import org.eclipse.epsilon.picto.PictoPlugin;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class NewPictoViewAction extends Action {

	protected boolean running = false;

	public NewPictoViewAction() {
		this.setText("New Picto View");
		this.setImageDescriptor(PictoPlugin.getDefault().getImageDescriptor("icons/newPictoView.png"));
	}

	@Override
	public void run() {

		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			activePage.showView(PictoView.ID, Math.random() * 1000 + "", IWorkbenchPage.VIEW_ACTIVATE);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}