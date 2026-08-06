/*********************************************************************
 * Copyright (c) 2022 Antonio García-Domínguez.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eunit.junit.test.suites;

import java.io.File;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eunit.junit.EUnitTestRunner;
import org.eclipse.epsilon.eunit.junit.IEUnitSuite;
import org.junit.runner.RunWith;

@RunWith(EUnitTestRunner.class)
public class ImportEUnitSuite implements IEUnitSuite {

	@Override
	public OperationContributor getOperationContributor() {
		return null;
	}

	@Override
	public URI getModuleURI() throws Exception {
		File fResources = new File(
			"../org.eclipse.epsilon.eunit.junit.test/src/org/eclipse/epsilon/eunit/junit/test/resources/");
		return new File(fResources, "callImportedFromData.eunit").toURI();
	}

	@Override
	public List<IModel> prepareModels() throws Exception {
		return Collections.emptyList();
	}

}
