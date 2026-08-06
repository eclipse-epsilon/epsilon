/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.uml.dt;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPlugin;

public class UMLTool {
	
	public UMLPlugin getUMLPlugin() {
		return UMLPlugin.INSTANCE;
	}
	
	public Profile getProfile(String uri) {
		return getProfileFromPathmapURI(UMLPlugin.getEPackageNsURIToProfileLocationMap().get(uri) + "");		
	}
	
	public Profile getProfileFromPathmapURI(String uri) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createURI(uri));
		try {
			resource.load(null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return (Profile) resource.getContents().get(0);

	}
	
}
