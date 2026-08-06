/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.engine.test.acceptance;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.pinset.DatasetRule;
import org.eclipse.epsilon.pinset.PinsetModule;

public class PinsetTests {

	protected EmfModel loadModel(String name, String modelFile, String metamodelUri, boolean read, boolean store) throws Exception {
		EmfModel model = new EmfModel();
		model.setCachingEnabled(false);
		model.setName(name);
		model.setModelFile(getFullPath(modelFile));
		model.setMetamodelUri(metamodelUri);
		model.setReadOnLoad(read);
		model.setStoredOnDisposal(store);
		model.load();
		return model;
	}

	protected final File getFile(String fileName) throws IOException {
		return FileUtil.getFileStandalone(fileName, getClass());
	}

	protected final String getFullPath(String path) throws IOException {
		return getFullPath(path, getClass());
	}

	protected static final String getFullPath(String path, Class<?> currentClass) throws IOException {
		return FileUtil.getFileStandalone(path, currentClass).getAbsolutePath();
	}

	protected static final void registerMetamodel(String path, Class<?> currentClass) throws Exception {
		EmfUtil.register(URI.createFileURI(getFullPath(path, currentClass)), EPackage.Registry.INSTANCE);
	}

	protected void assertEquivalent(PinsetModule module) throws Exception {

		for (DatasetRule datasetRule : module.getDatasetRules()) {
			String generatedFile = module.getFileName(datasetRule);
			String expectedDataset = FileUtil.getFileContents(
					getFile(String.format("expected/%s", generatedFile)));
			String resultDataset = datasetRule.getDataset().toString();
			resultDataset = resultDataset.replace("\r\n", "\n");
			expectedDataset = expectedDataset.replace("\r\n", "\n");
			assertEquals(generatedFile, expectedDataset, resultDataset);
			datasetRule.dispose();
		}

	}
}
