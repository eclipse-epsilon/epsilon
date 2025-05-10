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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.egl.launch.EgxRunConfiguration;

/**
 * This example demonstrates using the Epsilon Generation Language, the M2T language of Epsilon, in a stand-alone manner 
 * 
 * @author Sina Madani
 * @author Dimitrios Kolovos
 */
public class EgxRunConfigurationExample {
	
	public static void main(String[] args) throws Exception {
		Path root = Paths.get(EgxRunConfigurationExample.class.getResource("/").toURI());
		
		StringProperties modelProperties = new StringProperties();
		modelProperties.setProperty(EmfModel.PROPERTY_NAME, "Model");
		modelProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
			root.resolve("metamodels/Tree.ecore").toAbsolutePath().toUri().toString()
		);
		modelProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
			Paths.get("models", "Tree.xmi").toUri().toString()
		);
		modelProperties.setProperty(EmfModel.PROPERTY_CACHED, "true");
		modelProperties.setProperty(EmfModel.PROPERTY_CONCURRENT, "true");
		
		EgxRunConfiguration runConfig = EgxRunConfiguration.Builder()
			.withScript(root.resolve("egx/demo.egx"))
			.withModel(new EmfModel(), modelProperties)
			.withParameter("eglTemplateFileName", "tree.egl")
			.withOutputRoot(new File("egx-gen").getAbsolutePath())
			.withProfiling()
			.build();
		
		runConfig.run();
	}

}
