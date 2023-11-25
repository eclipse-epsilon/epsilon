/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.json.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.emc.json.JsonModel;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JSONModelCreationTests {

	private static final String TEST_BASE_FOLDER = JSONModelTestSuite.RESOURCES_BASE_FOLDER + "/writeTests";

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Parameters
	public static Collection<Object[]> data() {
		File baseFolder = new File(TEST_BASE_FOLDER);
		File[] eolFiles = baseFolder.listFiles(
			(dir, name) -> name.endsWith(".eol")
		);

		List<Object[]> data = new ArrayList<>(eolFiles.length);
		for (File eolFile : eolFiles) {
			data.add(new Object[] {
				eolFile,
				new File(baseFolder, eolFile.getName().replace(".eol", ".json"))
			});
		}

		return data;
	}

	private File eolFile;
	private File expectedJsonFile;

	public JSONModelCreationTests(File eolFile, File expectedJsonFile) {
		this.eolFile = eolFile;
		this.expectedJsonFile = expectedJsonFile;
	}

	@Test
	public void runTest() throws Exception {
		JsonModel model = new JsonModel();
		model.setName("M");
		model.setReadOnLoad(false);
		model.setStoredOnDisposal(true);
		model.setFile(tempFolder.newFile());
		model.load();

		EolModule eol = new EolModule();
		eol.getContext().getModelRepository().addModel(model);
		eol.parse(eolFile);
		eol.execute();
		model.dispose();

		try (JsonModel producedModel = new JsonModel(); JsonModel expectedModel = new JsonModel()) {
			producedModel.setName("Produced");
			producedModel.setFile(model.getFile());
			producedModel.setReadOnLoad(true);
			producedModel.setStoredOnDisposal(false);
			producedModel.load();

			expectedModel.setName("Expected");
			expectedModel.setFile(expectedJsonFile);
			expectedModel.setReadOnLoad(true);
			expectedModel.setStoredOnDisposal(false);
			expectedModel.load();

			assertEquals(expectedModel.getRoot(), producedModel.getRoot());
		}
	}

}
