/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.graphml.tests;

import static org.junit.Assert.assertEquals;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.graphml.GraphmlImporter;
import org.eclipse.epsilon.emc.muddle.Muddle;
import org.eclipse.epsilon.emc.muddle.MuddleModel;
import org.eclipse.epsilon.eol.EolEvaluator;

public class MuddleTests {
	
	protected MuddleModel loadMuddleModel(String filename) throws Exception {
		GraphmlImporter importer = new GraphmlImporter();
		String fileUri = FileUtil.getFileStandalone("models/" + filename, GraphmlTestSuite.class).toURI().toString();
		Muddle muddle = importer.importGraph(fileUri);
		MuddleModel model = new MuddleModel();
		model.setMuddle(muddle);
		return model;
	}
	
	protected void assertEval(String expression, Object result, String modelFileName) throws Exception {
		MuddleModel model = loadMuddleModel(modelFileName);
		EolEvaluator evaluator = new EolEvaluator(model);
		assertEquals(result, evaluator.evaluate(expression));
	}

}
