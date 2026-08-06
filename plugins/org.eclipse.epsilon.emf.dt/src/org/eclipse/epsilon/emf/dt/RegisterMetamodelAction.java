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

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class RegisterMetamodelAction implements IObjectActionDelegate{
	
	protected ISelection selection;
	
	public RegisterMetamodelAction() {
		super();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	
	public void run(IAction action){
		
		Iterator<?> it = ((IStructuredSelection) selection).iterator();
		while (it.hasNext()) {
			IFile file = (IFile) it.next();
			//String fileName = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString() + file.getFullPath().toOSString();
			String fileName = file.getFullPath().toOSString();
			//String fileName = file.getRawLocation().toOSString();
			try {
				EmfRegistryManager.getInstance().addMetamodel(fileName);
				LogUtil.logInfo("Metamodel " + fileName + " registered successfully");
			}
			catch (Exception ex) {
				LogUtil.log("Metamodel " + fileName + " could not be registered", ex);
			}
		}
		/*
		IFile file = (IFile)((IStructuredSelection) selection).getFirstElement();
		//String fileName = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString() + file.getFullPath().toOSString();
		String fileName = file.getFullPath().toOSString();
		//String fileName = file.getRawLocation().toOSString();
		EmfRegistryManager.getInstance().addMetamodel(fileName);
		EmfUtil.register(URI.createPlatformResourceURI(fileName, true), EPackage.Registry.INSTANCE);
		*/
	}
	
}
