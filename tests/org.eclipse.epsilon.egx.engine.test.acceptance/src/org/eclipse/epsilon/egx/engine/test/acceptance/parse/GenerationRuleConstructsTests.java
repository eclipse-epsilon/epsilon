/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.parse;

import static org.junit.Assert.assertEquals;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EgxModule;
import org.junit.Test;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public final class GenerationRuleConstructsTests {
	
	@Test
	public void assertParseProblems() throws Exception {
		EgxModule module = new EgxModule();
		module.parse(FileUtil.getFileStandalone("InvalidGenerationRule.egx", GenerationRuleConstructsTests.class));
		assertEquals(5, module.getParseProblems().size());
	}
}
