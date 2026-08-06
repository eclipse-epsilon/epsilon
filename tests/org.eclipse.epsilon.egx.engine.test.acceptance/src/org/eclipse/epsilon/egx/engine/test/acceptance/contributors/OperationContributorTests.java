/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.contributors;

import java.io.File;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egx.engine.test.acceptance.EgxAcceptanceTestUtil;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * 
 * @author Sina Madani, Alfonso de la Vega
 * @since 1.6
 */
@RunWith(Parameterized.class)
public class OperationContributorTests {

	static File transformationEGX;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Load dependency
		FileUtil.getFileStandalone("rule.egl", OperationContributorTests.class);
		transformationEGX = FileUtil.getFileStandalone("transformation.egx", OperationContributorTests.class);
	}
	
	@Parameter
	public IEgxModule module;
	
	@Parameters
	public static Iterable<? extends IEgxModule> modules() throws Exception {
		return EolAcceptanceTestUtil.unwrapModules(EgxAcceptanceTestUtil.modules(true));
	}
	
	@Before
	public void setUp() throws Exception {
		module.getContext().setTemplateFactory(new EglFileGeneratingTemplateFactory(
			FileUtil.getFileStandalone("", getClass()).toPath())
		);
	}
	
	@Test
	public void testSimpleContributor() throws Exception {
		module.getContext().getOperationContributorRegistry().add(new SimpleOperatorContributor());
		module.parse(transformationEGX);
		module.execute();
	}
	
}
