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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.json.JsonModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.junit.Test;

public class JSONModelOptionsTests {

	private static final String COMMITS_JSON_PATH = JSONModelTestSuite.RESOURCES_BASE_FOLDER + "/commits.json";

	@Test
	public void needUriOrFile() {
		try (JsonModel model = new JsonModel()) {
			model.setReadOnLoad(true);
			model.load();
			fail("An EolModelLoadingException is expected");
		} catch (EolModelLoadingException ex) {
			// pass!
		}
	}

	@Test
	public void loadFileWithProperty() throws Exception {
		try (JsonModel model = new JsonModel()) {
			StringProperties props = new StringProperties();
			props.put(JsonModel.PROPERTY_FILE, COMMITS_JSON_PATH);
			props.put(JsonModel.PROPERTY_READONLOAD, "true");
			props.put(JsonModel.PROPERTY_STOREONDISPOSAL, "false");

			model.load(props);

			assertNotNull(model.getRoot());
		}
	}

	@Test
	public void loadFileWithFileURI() throws Exception {
		try (JsonModel model = new JsonModel()) {
			StringProperties props = new StringProperties();
			props.put(JsonModel.PROPERTY_URI, new File(COMMITS_JSON_PATH).getAbsoluteFile().toURI().toString());
			props.put(JsonModel.PROPERTY_READONLOAD, "true");
			props.put(JsonModel.PROPERTY_STOREONDISPOSAL, "false");

			model.load(props);

			assertNotNull(model.getRoot());
		}
	}

}
