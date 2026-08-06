/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.unit;

import static org.eclipse.epsilon.emc.simulink.common.test.MatlabEngineSetupEnum.ENGINE_JAR;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.epsilon.emc.simulink.common.test.AssumeMatlabInstalled;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


/**
 * Ensure location does not have any spaces
 * Check for windows
 */
@RunWith(Parameterized.class)
public class LoadModelTest {

	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String RESOURCES = USER_DIR + File.separator + "resources" + File.separator + "models";
	private static final String WITH_SPACES = RESOURCES + File.separator + "dir with spaces";

	private static String version;
	
	private static String notExisting = "emptyNotExisting";
	private static String empty = "empty";
	private static String feedbackController = "feedbackController";
	
	@ClassRule
	public static AssumeMatlabInstalled installation = new AssumeMatlabInstalled();
	
	@BeforeClass
	public static void setup(){
		version = installation.getVersion();
		
	}
	
	private static Iterable<Object[]> data;
	
	private static Iterable<Object[]> getData() {
		if (data== null) {
			ArrayList<Object[]> list = new ArrayList<>();
			List<Boolean> bools = Arrays.asList(new Boolean[] {false, true});
			List<String> files = Arrays.asList(new String[] {feedbackController, empty, notExisting});
			List<String> workdirs = Arrays.asList(new String[] {"", RESOURCES, WITH_SPACES});
			for (Boolean store : bools) {
				for (Boolean read : bools) {
					//for (Boolean open : bools) {
						for (Boolean close : bools) {
							for (String workdir : workdirs) {
								for (String file : files) {
									list.add(new Object[] {read, store, /*open,*/ close, workdir, file});
								}
							}
						}
					//}
				}
			}
			data = list;
		}
		return data;
	}
	
	@Parameters(name = "Test-{index}: Read={0},Store={1},Open={3},Close={3},Workdir={4},File={5}")
	public static Iterable<Object[]> data() {
		return getData();
	}
	
	@Parameter(0)
	public boolean readOnLoad;
	
	@Parameter(1)
	public boolean storeOnDisposal;
	
	/*@Parameter(2)
	public boolean openOnLoad;*/
	
	@Parameter(2)
	public boolean closeOnDisposal;
	
	@Parameter(3)
	public String workdir;
	
	@Parameter(4)
	public String fileName;
	
	private SimulinkModel model;
	
	private File file;
	private File testoutput = new File("testOutput");
	
	private File getFile(String fileName, String workdir) throws IOException {
		File original = new File( ( workdir.equals("") ? RESOURCES : workdir) + File.separator + fileName + ".slx");
		
		// Ensure temporary dir destination is available
		if (!testoutput.exists()) {			
			testoutput.mkdir();
		}
		// Compute temporary destination of the file
		Path destination = testoutput.toPath().toAbsolutePath().resolve(original.getParentFile().getName()).resolve(original.getName());
		
		// Ensure the working directory exists
		File parent = destination.getParent().toFile();
		if (!parent.exists()) parent.mkdir();
		
		// If the original file exists, copy it to destination
		if (readOnLoad && original.exists()) {
			Files.copy(original.toPath(), destination);
		}
		return destination.toFile();
	}
	
	@Rule
	public TestName name = new TestName();
	
	@Before
	public void logName() {
		System.out.println(name.getMethodName());
	}
	
	@Test
	public void testLoadDir() {
		
		model = new SimulinkModel();

		try {
			file = getFile(fileName, workdir);
		} catch (IOException e1) {
			e1.printStackTrace();
			cleanup();
			fail("Problems copying file to temp location");
		}
		model.setFile(file);
		if (!workdir.equals("")) {			
			model.setWorkingDir(file.getParentFile());
		}
		
		model.setReadOnLoad(readOnLoad);
		model.setStoredOnDisposal(storeOnDisposal);
		//model.setOpenOnLoad(openOnLoad);
		model.setOpenOnLoad(false);
		model.setCloseOnDispose(closeOnDisposal);

		model.setName("M");
		model.setCachingEnabled(false);
		try {			
			String engine = ENGINE_JAR.path(version);
			model.setEngineJarPath(engine);
		} catch (Exception e) {
			cleanup();
			fail("Error setting MATLAB Configuration");
		}
		try {
			model.load();
			
			assertTrue(true);
			
			// TEST EOL
			EolModule eolModule = new EolModule();
			try {			
				eolModule.parse("\"hi\".println();");
			} catch (Exception e) {
				cleanup();
				fail("Parsing failed");
			}	
			try {
				eolModule.getContext().getModelRepository().addModel(model);
				eolModule.execute();
			} catch (Exception e) {
				cleanup();
				fail("Couldn't go on with eol");
			} finally {
				eolModule = null;				
			}
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getCause().toString());
		} finally {
			model.dispose();
			try {
				model.getEngine().eval("close_system('?')", model.getSimulinkModelName());
				model.getEngine().flush();
			} catch (MatlabException e) {
				e.printStackTrace();
				cleanup();
			}
			model = null;
		}
	}
	
	@After
	public void deleteCreated() {
		cleanup();
	}

	private void cleanup() {
		try {
			FileUtils.deleteDirectory(testoutput);
			testoutput.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void deleteNonExisting() {
		//getFile(notExisting, RESOURCES).delete();
		//getFile(notExisting, WITH_SPACES).delete();
	}
	

}
