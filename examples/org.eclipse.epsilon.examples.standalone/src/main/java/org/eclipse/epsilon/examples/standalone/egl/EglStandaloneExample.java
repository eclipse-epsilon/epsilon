/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.egl;

import java.nio.file.Paths;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.examples.standalone.egl.flexmiemfatic.EglFlexmiEmfaticStandaloneExample;

public class EglStandaloneExample {
	
	public static void main(String[] args) throws Exception {
		EglModule module = new EglModule();
		module.parse(EglStandaloneExample.class.getResource("/egl/Demo.egl"));
		if (!module.getParseProblems().isEmpty()) {
			System.out.println("Parsing problems: " + module.getParseProblems());
			System.exit(1);
		}

		EmfModel model = new EmfModel();
		model.setName("Model");
		model.setModelFile("models/Tree.xmi");
		model.setMetamodelFile(Paths.get(
			EglFlexmiEmfaticStandaloneExample.class.getResource("/metamodels/Tree.ecore")
			.toURI()).toFile().getAbsolutePath()
		);
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		module.getContext().getModelRepository().addModel(model);

		try {
			Object result = module.execute();
			System.out.println(result);
		} finally {
			module.getContext().getModelRepository().dispose();
			module.getContext().dispose();
		}
	}

}
