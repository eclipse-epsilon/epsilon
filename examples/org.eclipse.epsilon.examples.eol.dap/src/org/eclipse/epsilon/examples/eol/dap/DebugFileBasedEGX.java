package org.eclipse.epsilon.examples.eol.dap;

import java.io.File;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;

public class DebugFileBasedEGX {

	public static void main(String[] args) throws Exception {
		EgxModule module = new EgxModule();
		module.parse(new File(args[0]));

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
