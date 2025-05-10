/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.eol;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.uml2.uml.UMLPackage;

public class EolStandaloneUmlExample {
	
	public static void main(String[] args) throws Exception {
		EPackage.Registry.INSTANCE.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);

		EolModule module = new EolModule();
		module.parse(EolStandaloneUmlExample.class.getResource("/eol/Uml.eol"));

		EmfModel model = new EmfModel();
		model.setName("Model");
		model.setModelFile("models/example.uml");
		model.setMetamodelUri(UMLPackage.eNS_URI);
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		module.getContext().getModelRepository().addModel(model);

		try {
			module.execute();
		} finally {
			module.getContext().getModelRepository().dispose();
			module.getContext().dispose();
		}
	}

}
