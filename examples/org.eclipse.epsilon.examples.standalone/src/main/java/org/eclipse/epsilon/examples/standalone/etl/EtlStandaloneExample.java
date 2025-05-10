/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.etl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.launch.EtlRunConfiguration;
import org.eclipse.epsilon.etl.trace.Transformation;

/**
 * This example demonstrates using the 
 * Epsilon Transformation Language, the M2M language
 * of Epsilon, in a stand-alone manner 
 * 
 * @author Sina Madani
 * @author Dimitrios Kolovos
 */
public class EtlStandaloneExample {
	
	public static void main(String[] args) throws Exception {
		Path root = Paths.get(EtlStandaloneExample.class.getResource("/").toURI());

		final String treeMetamodelFileUri = EtlStandaloneExample.class
			.getResource("/metamodels/Tree.ecore").toURI().toString();

		StringProperties sourceProperties = new StringProperties();
		sourceProperties.setProperty(EmfModel.PROPERTY_NAME, "Source");
		sourceProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, treeMetamodelFileUri);
		sourceProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
			Paths.get("models", "Tree.xmi").toUri().toString()
		);
		
		StringProperties targetProperties = new StringProperties();
		targetProperties.setProperty(EmfModel.PROPERTY_NAME, "Target");
		targetProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, treeMetamodelFileUri);
		targetProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
			Paths.get("models", "Copy.xmi").toUri().toString()
		);
		targetProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "false");
		targetProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, "true");

		EtlModule module = new EtlModule();
		EtlRunConfiguration runConfig = EtlRunConfiguration.Builder()
			.withScript(root.resolve("etl/Demo.etl"))
			.withModel(new EmfModel(), sourceProperties)
			.withModel(new EmfModel(), targetProperties)
			.withParameter("parameterPassedFromJava", "Hello from pre")
			.withProfiling()
			.withModule(module)
			.build();

		try {
			runConfig.run();

			// Optional: list traces (done for illustration)
			listTraces(module);
		} finally {
			runConfig.dispose();
		}
	}

	protected static void listTraces(EtlModule module) {
		// Generic version
		/*
		for (Transformation tx : module.getContext().getTransformationTrace().getTransformations()) {
			System.out.println(tx.getSource() + " produced " + tx.getTargets());
		}
		*/

		// Specific to Tree (nicer output)
		for (Transformation tx : module.getContext().getTransformationTrace().getTransformations()) {
		    EObject eob = (EObject) tx.getSource();
		    EStructuralFeature sfLabel = eob.eClass().getEStructuralFeature("label");
		    String label = (String) eob.eGet(sfLabel);

		    for (Object txTarget : tx.getTargets()) {
		        EObject eobTarget = (EObject) txTarget;
		        String targetLabel = (String) eobTarget.eGet(sfLabel);
		        System.out.println("Tree " + label + " produced Tree " + targetLabel);
		    }
		}		
	}
}
