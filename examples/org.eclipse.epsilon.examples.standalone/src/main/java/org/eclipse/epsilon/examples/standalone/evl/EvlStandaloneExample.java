/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.evl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.launch.EvlRunConfiguration;

/**
 * This example demonstrates using the 
 * Epsilon Validation Language, the model validation language
 * of Epsilon, in a stand-alone manner
 * 
 * @author Sina Madani
 * @author Dimitrios Kolovos
 */
public class EvlStandaloneExample {

	public static void main(String... args) throws Exception {
		Path root = Paths.get(EvlStandaloneExample.class.getResource("/").toURI());
		
		StringProperties modelProperties = StringProperties.Builder()
			.withProperty(EmfModel.PROPERTY_NAME, "Model")
			.withProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
				EvlStandaloneExample.class.getResource("/metamodels/Tree.ecore").toString()
			)
			.withProperty(EmfModel.PROPERTY_MODEL_URI,
				Paths.get("models", "Tree.xmi").toUri().toString()
			)
			.withProperty(EmfModel.PROPERTY_CACHED, true)
			.withProperty(EmfModel.PROPERTY_CONCURRENT, true)
			.build();
		
		EvlRunConfiguration runConfig = EvlRunConfiguration.Builder()
			.withScript(root.resolve("evl/Demo.evl"))
			.withModel(new EmfModel(), modelProperties)
			.withParameter("greeting", "Hello from ")
			.withProfiling()
			.withResults()
			.withParallelism()
			.build();

		try {
			runConfig.run();

			/*
			 * EvlRunConfiguration#run() will print output, but we could
			 * alternatively loop through the unsatisfied constraints.
			 *
			 * We'd have to do this if using an EvlModule instead of an
			 * EvlRunConfiguration.
			 */
			Collection<UnsatisfiedConstraint> unsatisfied = runConfig.getResult();
			for (UnsatisfiedConstraint c : unsatisfied) {
				System.out.println(String.format("%s '%s' unsatisfied for %s: %s",
					c.getConstraint().isCritique() ? "Critique" : "Constraint",
					c.getConstraint().getName(),
					c.getInstance(),
					c.getMessage()
				));
			}
		} finally {
			runConfig.dispose();
		}
	}
}
