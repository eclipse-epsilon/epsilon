/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.dt.epackageregistryexplorer;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ShowOppositeReferenceAction extends Action {
	
	protected PackageRegistryExplorerView view; 
	
	public ShowOppositeReferenceAction(PackageRegistryExplorerView view) {
		this.view = view;
		this.setChecked(view.isShowOppositeReference());
		this.setText("Show opposite references");
		this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/opposite.gif"));
	}
	
	@Override
	public void run() {
		view.setShowOppositeReference(!view.isShowOppositeReference());
	}
}
 
