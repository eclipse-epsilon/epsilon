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
import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.emc.json.JsonModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.junit.Test;

/**
 * Methods for directly setting the content of a JSON model from a string,
 * without loading it from a file or from a URI.
 */
public class JSONSetContentTests {

	@Test
	public void setContent() {
		try (JsonModel model = new JsonModel()) {
			model.setName("M");
			model.setJsonContent("{\"hello\": \"world\"}");
			EolEvaluator evaluator = new EolEvaluator(model);

			assertTrue(model.isLoaded());
			assertEquals("world", evaluator.evaluate("M.root.hello"));
		}
	}

}
