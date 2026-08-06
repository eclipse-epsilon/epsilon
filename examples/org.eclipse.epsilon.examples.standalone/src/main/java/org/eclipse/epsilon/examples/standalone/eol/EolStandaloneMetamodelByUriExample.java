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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class EolStandaloneMetamodelByUriExample {

	public static void main(String[] args) throws Exception {
		EmfUtil.register(
			URI.createURI(EolStandaloneMetamodelByUriExample.class.getResource("/metamodels/Tree.ecore").toString()),
			EPackage.Registry.INSTANCE);

		EolModule module = new EolModule();
		module.parse(EolStandaloneMetamodelByUriExample.class.getResource("/eol/Demo.eol"));

		EmfModel model = new EmfModel();
		model.setName("Model");
		model.setModelFile("models/Tree.xmi");
		model.setMetamodelUri("TreeDsl");
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		module.getContext().getModelRepository().addModel(model);

		try {
			module.getContext().getFrameStack().put(
				Variable.createReadOnlyVariable("Thread", Thread.class)
			);
			module.execute();
		} finally {
			module.getContext().getModelRepository().dispose();
			module.getContext().dispose();
		}
	}

}
