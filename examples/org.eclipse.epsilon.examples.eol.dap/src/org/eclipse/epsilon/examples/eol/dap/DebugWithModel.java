/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.examples.eol.dap;

import java.io.File;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;

public class DebugWithModel {

	public static void main(String[] args) throws Exception {
		EolModule module = new EolModule();
		module.parse(new File("epsilon/04-inspectObject.eol"));

		EmfModel model = new EmfModel();
		model.setModelFile("epsilon/models/person.model");
		model.setMetamodelFile("epsilon/models/person.ecore");
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		module.getContext().getModelRepository().addModel(model);

		EpsilonDebugServer server = new EpsilonDebugServer(module, 4040);
		server.run();
	}
}
