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
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.jface.action.Action;

public class LockAction extends Action {
	
	protected PictoView pictoView;
	
	public LockAction(PictoView pictoView) {
		super("Lock", AS_CHECK_BOX);
		setImageDescriptor(PictoPlugin.getDefault().getImageDescriptor("icons/lock.gif"));
		this.pictoView = pictoView;
	}
	
	@Override
	public void run() {
		pictoView.setLocked(!pictoView.isLocked());
	}
}