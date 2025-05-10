/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.egl.flexmiemfatic;

import java.nio.file.Paths;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class EglFlexmiEmfaticStandaloneExample {

	public static void main(String[] args) throws Exception {
		// Make Flexmi and Emfatic known to EMF as we will use them
		// to express the input model and metamodel of the transformation
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());
		
		// Parse the EGL template
		EglModule module = new EglModule();
		module.parse(EglFlexmiEmfaticStandaloneExample.class.getResource("/egl/flexmiemfatic/project2html.egl"));

		// Configure its source model
		EmfModel model = new EmfModel();
		model.setModelFile("models/psl.flexmi");
		model.setMetamodelFile(Paths.get(
			EglFlexmiEmfaticStandaloneExample.class.getResource("/metamodels/psl.emf")
			.toURI()).toFile().getAbsolutePath()
		);
		model.setName("M");
		model.load();
		module.getContext().getModelRepository().addModel(model);

		// Execute the EGL template and print its output
		try {
			System.out.println(module.execute());
		} finally {
			module.getContext().getModelRepository().dispose();
			module.getContext().dispose();
		}
	}

}
