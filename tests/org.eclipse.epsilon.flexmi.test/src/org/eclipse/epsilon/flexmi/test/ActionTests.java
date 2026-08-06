/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.test;

import org.eclipse.epsilon.common.util.FileUtil;
import org.junit.Test;

public class ActionTests extends FlexmiTests {
	
	@Test
	public void testVar() throws Exception {
		FileUtil.getFileStandalone("models/actions/operations.eol", FlexmiTests.class);
		FileUtil.getFileStandalone("models/actions/imported-operations.eol", FlexmiTests.class);
		FileUtil.getFileStandalone("models/actions/templates.flexmi", FlexmiTests.class);
		FileUtil.getFileStandalone("models/actions/template-operations.eol", FlexmiTests.class);
		
		assertEval("EOperation.all.first().name", "createC1", "actions/model-with-actions.flexmi");
		assertEval("EOperation.all.second().name", "createC3", "actions/model-with-actions.flexmi");
		assertEval("EOperation.all.first().eType.name", "C1", "actions/model-with-actions.flexmi");
		assertEval("EOperation.all.second().eType.name", "C3", "actions/model-with-actions.flexmi");
		assertEval("EClass.all.at(5).name", "C4", "actions/model-with-actions.flexmi");
		assertEval("EClass.all.at(6).name", "C5", "actions/model-with-actions.flexmi");
		assertEval("EClass.all.at(6).isAbstract", true, "actions/model-with-actions.flexmi");
		assertEval("EClass.all.at(7).name", "C6", "actions/model-with-actions.flexmi");
		assertEval("EClass.all.at(7).isAbstract", false, "actions/model-with-actions.flexmi");
		
		assertEval("EClass.all.third().name", "C2Clone", "actions/model-with-actions.flexmi");
		assertWarning("Undefined variable", 9, "actions/model-with-actions.flexmi");
	}
	
	@Test
	public void testNameAndAction() throws Exception {
		assertEval("EReference.all.first().name", "cs", "actions/name-and-action.flexmi");
	}
	
}
