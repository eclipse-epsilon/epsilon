/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.common.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.epsilon.emc.simulink.model.IGenericSimulinkModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestName;

public abstract class AbstractCommonSimulinkTest {

	protected static final String TEST_TRACES = "=> TEST: %s.%s";
	protected static final String LN_BR = System.getProperty("line.separator");
	
	protected static final String LN = " 'LINE BREAK'.println(); ";

	@ClassRule
	public static AssumeMatlabInstalled installation = new AssumeMatlabInstalled();

	protected String eol;
	protected String eolResourceFile;
	protected File modelFile;

	protected boolean activeCache = false;

	@Rule
	public TestName name = new TestName();

	@BeforeClass
	public static void engineLogLevel() {}

	@Before
	public void logTestName() {
		System.out.println(String.format(TEST_TRACES, this.getClass().getSimpleName(), name.getMethodName()));
	}

	@After
	public void execute() {
		setupEOL();
		try {
			run(eol, modelFile, activeCache);
		} catch (EolRuntimeException re) {
			re.printStackTrace();
			fail("Caught EOL Runtime Exeption");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			eol = null;
			modelFile = null;
			eolResourceFile = null;
		}

	}

	protected void setupEOL() {
		if (eol == null) {
			if (eolResourceFile != null) {
				eolResourceFile = FileUtils.getResource(eolResourceFile);
			} else {
				eolResourceFile = FileUtils.getScript(getClass().getSimpleName() + "/" + name.getMethodName() + ".eol");
			}
			
			try {
				File eolAsFile = new File(eolResourceFile);
				List<String> lines = Files.readAllLines(Paths.get(eolAsFile.getAbsolutePath()));
				eol = String.join(LN_BR, lines);
			} catch (Exception e) {}
		}
		
	}

	public void run(String eol, File file) throws Exception {
		run(eol, file, false);
	}

	public void run(String eol, File file, boolean activeCaching) throws Exception {
		if (eol != null) {
			IGenericSimulinkModel model = null;
			try {
				EolModule module = new EolModule();
				try {
					module.parse(eol);
				} catch (Exception e) {
					System.err.println("Could not parse EOL");
					throw e;
				}

				model = loadSimulinkModel(file, activeCaching);
				System.out.println("   >>Model: " + model.getFile().getName());
				System.out.println("   >> EOL: " + LN_BR + eol);
				
				module.getContext().getModelRepository().addModel(model);
				module.execute();
			} catch (Exception ex) {
				throw ex;
			} finally {
				if (model != null) {
					dispose(model);
				}
			}
		}
	}

	public abstract IGenericSimulinkModel loadSimulinkModel(File file, boolean activeCaching) throws Exception;
	
	public abstract IGenericSimulinkModel getModel();
	
	public abstract void dispose(IGenericSimulinkModel model);

}
