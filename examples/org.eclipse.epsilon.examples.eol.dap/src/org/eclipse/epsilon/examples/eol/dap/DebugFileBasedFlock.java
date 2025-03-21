package org.eclipse.epsilon.examples.eol.dap;

import java.io.File;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.epsilon.flock.FlockModule;

public class DebugFileBasedFlock {

	public static void main(String[] args) throws Exception {
		final String flockScript = args[0];
		final String originalModelPath = args[1];
		final String originalMetamodelPath = args[2];
		final String newMetamodelPath = args[3];

		FlockModule module = new FlockModule();
		try {
			module.parse(new File(flockScript));

			EmfModel originalModel = new EmfModel();
			originalModel.setModelFile(originalModelPath);
			originalModel.setMetamodelFile(originalMetamodelPath);
			originalModel.setReadOnLoad(true);
			originalModel.setStoredOnDisposal(false);
			originalModel.setName("Original");
			originalModel.load();
			module.getContext().setOriginalModel(originalModel);

			EmfModel migratedModel = new EmfModel();
			migratedModel.setModelFile(new File("epsilon", "migrated.model").getCanonicalPath());
			migratedModel.setMetamodelFile(newMetamodelPath);
			migratedModel.setReadOnLoad(false);
			migratedModel.setStoredOnDisposal(true);
			migratedModel.setName("Migrated");
			migratedModel.load();
			module.getContext().setMigratedModel(migratedModel);

			EpsilonDebugServer server = new EpsilonDebugServer(module, 4040);
			server.run();
		} finally {
			module.getContext().getModelRepository().dispose();
			module.getContext().dispose();
		}
	}

}
