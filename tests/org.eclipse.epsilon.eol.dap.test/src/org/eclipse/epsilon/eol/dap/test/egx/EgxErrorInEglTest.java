/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.egx;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class EgxErrorInEglTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "24-orchestrationError.egx");
	private static final File EGL_FILE = new File(BASE_RESOURCE_FOLDER, "24-personError.egl");

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Override
	protected void setupModule() throws Exception {
		this.module = new EgxModule();
		module.parse(SCRIPT_FILE);
		module.getContext().getFrameStack().put("tempFolder", tempFolder.newFolder());

		final List<Object> instances = Collections.singletonList(new Person("John", "Smith"));
		final List<Class<?>> types = Collections.singletonList(Person.class);
		final JavaModel model = new JavaModel(instances, types);
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void errorReportedOnce() throws Exception {
		attach();
		assertProgramFailed();

		String stderr = getStderr();
		int count = 0;
		final Matcher matcher = Pattern.compile("Undefined").matcher(stderr);
		while (matcher.find()) {
			++count;
		}
		assertEquals("The undefined variable error should be reported exactly once", 1, count);
	}

}
