/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emf.dt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public abstract class BrowseEPackagesListener implements Listener {
	
	protected boolean multipleSelection = false;
	
	public BrowseEPackagesListener() {
		this(false);
	}
	
	public BrowseEPackagesListener(boolean multipleEPackages) {
		this.multipleSelection = multipleEPackages;
	}
	
	public void handleEvent(Event event) {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog( 
				Display.getDefault().getActiveShell(), 
				new LabelProvider() {

					@Override
					public String getText(Object element) {
						return (String) element;
					}
					
					Image image = null;
					
					@Override
					public Image getImage(Object element) {
						if (image == null) {
							image = EmfUtilPlugin.getImageDescriptor("icons/epackage.png").createImage();
						}
						return image;
					}
					
				});
		
		dialog.setMultipleSelection(multipleSelection);
		dialog.setMessage("Select an EPackage");
		dialog.setTitle("Registered EPackages");
		
		List<String> ePackages = new ArrayList<>();
		ePackages.addAll(EPackage.Registry.INSTANCE.keySet());
		
		dialog.setElements(ePackages.toArray());
		
		if (dialog.open() == Window.OK) {
			if (dialog.getResult().length > 0) {
				for (Object result : dialog.getResult()) {
					selectionChanged(result + "");
				}
			}
		}
	}
	
	public abstract void selectionChanged(String ePackageUri);
		
}
