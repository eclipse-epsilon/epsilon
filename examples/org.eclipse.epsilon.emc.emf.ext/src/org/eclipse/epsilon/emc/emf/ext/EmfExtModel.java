/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.ext;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;

public class EmfExtModel extends EmfModel {
	
	EmfExtPropertyGetter propertyGetter = new EmfExtPropertyGetter();
	
	@Override
	public IPropertyGetter getPropertyGetter() {
		return propertyGetter;
	}
	
	@Override
	public boolean knowsAboutProperty(Object instance, String property) {
		return super.knowsAboutProperty(instance, property) || property.endsWith("_");
	}
	
	public static void main(String[] args) throws Exception {
		
		EolModule module = new EolModule();
		module.parse("EPackage.all.first().name_.println();");
		
		EmfExtModel model = new EmfExtModel();
		model.setModelFileUri(URI.createFileURI(new File("tree.ecore").getAbsolutePath()));
		model.setMetamodelUri(EcorePackage.eINSTANCE.getNsURI());
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		
		module.getContext().getModelRepository().addModel(model);
		module.execute();
		
	}
	
}
