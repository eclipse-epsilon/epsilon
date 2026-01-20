/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.examples.standalone.egl;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.m0.EmfM0Model;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.examples.standalone.EpsilonStandaloneExample;
import org.eclipse.epsilon.examples.standalone.egl.flexmiemfatic.EglFlexmiEmfaticStandaloneExample;

/**
 * This example demonstrates using the 
 * Epsilon Generation Language, the M2T language
 * of Epsilon, in a stand-alone manner 
 * @author Dimitrios Kolovos
 */
public class EglStandaloneExample extends EpsilonStandaloneExample {
	
	public static void main(String[] args) throws Exception {
		EglModule module = new EglModule();
		module.parse(EglStandaloneExample.class.getResource("Demo.egl"));
		if (!module.getParseProblems().isEmpty()) {
			System.out.println("Parsing problems: " + module.getParseProblems());
			System.exit(1);
		}

		EmfModel model = new EmfModel();
		model.setName("Model");
		model.setModelFile(null);
		model.setModelFile(Paths.get(EglFlexmiEmfaticStandaloneExample.class.getResource("psl.flexmi").toURI()).toFile().getAbsolutePath());
		model.setMetamodelFile(Paths.get(EglFlexmiEmfaticStandaloneExample.class.getResource("psl.emf").toURI()).toFile().getAbsolutePath());


		new EglStandaloneExample().execute();
	}
	
	@Override
	public IEolModule createModule() {
		return new EglTemplateFactoryModuleAdapter(new EglTemplateFactory());
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		models.add(createEmfModel("Model", "models/Tree.xmi", "models/Tree.ecore", true, true));
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "egl/Demo.egl";
	}

	@Override
	public void postProcess() {
		System.out.println(result);
	}

}
