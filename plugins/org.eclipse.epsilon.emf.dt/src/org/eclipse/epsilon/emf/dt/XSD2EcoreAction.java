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

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;

public class XSD2EcoreAction implements IObjectActionDelegate{
	
	protected ISelection selection;
	
	public XSD2EcoreAction() {
		super();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	
	public void run(IAction action){
		
		XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
        ResourceSet resourceSet = new ResourceSetImpl();
        Collection<EObject> eCorePackages = xsdEcoreBuilder.generate(URI.createFileURI(getSelectedFile().getLocation().toOSString()));

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        Resource resource = resourceSet.createResource(URI.createFileURI(getSelectedFile().getLocation().toOSString() + ".ecore"));

        for (Iterator<EObject> iter = eCorePackages.iterator(); iter.hasNext();) {
            EPackage element = (EPackage) iter.next();
            resource.getContents().add(element);
        }

        try {
            resource.save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finished");
	}
	
	public IFile getSelectedFile() {
		return (IFile) ((IStructuredSelection) selection).getFirstElement();
	}
	
}
