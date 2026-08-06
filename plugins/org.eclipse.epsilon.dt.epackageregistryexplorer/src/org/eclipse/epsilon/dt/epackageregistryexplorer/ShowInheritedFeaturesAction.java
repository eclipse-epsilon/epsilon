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

public class ShowInheritedFeaturesAction extends Action {
	
	protected PackageRegistryExplorerView view; 
	
	public ShowInheritedFeaturesAction(PackageRegistryExplorerView view) {
		this.view = view;
		this.setChecked(view.isShowInheritedFeatures());
		this.setText("Show inherited features");
		this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/supertypes.png"));
	}
	
	@Override
	public void run() {
		view.setShowInheritedFeatures(!view.isShowInheritedFeatures());
	}
}
 
