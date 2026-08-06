/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.profiling.dt;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ProfilerViewAction extends Action {
	
	protected ProfilerView profilerView;
	
	public ProfilerViewAction(ProfilerView profilerView) {
		this.profilerView = profilerView;
		setImageDescriptor(PlatformUI.getWorkbench().
					getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
	}

	public ProfilerViewAction(ProfilerView profilerView, String text, int style) {
		super(text, style);
		this.profilerView = profilerView;
		setImageDescriptor(PlatformUI.getWorkbench().
					getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));

	}
	
}
