/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.perform;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;

public class ModelGenerator {

	protected final Path root = Paths.get("experiments", "query-optimisation");
	protected final IModel model;
	protected final Integer base, modelId;
	
	public ModelGenerator(Integer modelId) {
		this.modelId = modelId;
		this.model = populateModel();
		this.base = 3;
	}
	
	private IModel populateModel() {
		File file = root.resolve("models").resolve(String.format("Model%s.slx", modelId)).toFile();
		if (file.exists()) {
			file.delete();
		}
		
		SimulinkModel model = new SimulinkModel();
		model.setReadOnLoad(false);
		model.setOpenOnLoad(true);
		model.setStoredOnDisposal(true);
		model.setCloseOnDispose(true);
		model.setFile(file);
		model.setName("M");
		return model;
	}
	
	private String readEol() throws IOException{
		File file = root.resolve("scripts").resolve("boilerGenerator.eol").toFile();
		return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
	}
	
	public void generate() throws EolModelLoadingException, IOException {
		model.load();
		EolEvaluator eolEvaluator = new EolEvaluator(model);
		eolEvaluator.setVariable("val", Double.valueOf(Math.pow(base, modelId)).intValue());
		eolEvaluator.execute(readEol());
		model.dispose();
	}
	
	public static void main(String[] args) {
		System.out.println("Starting Generation");
		for (int max = 5, i = 1; i <= max; i++) {
			try {
				new ModelGenerator(i).generate();
			} catch (EolModelLoadingException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		System.out.println("Finished Generation");
	}
	
}