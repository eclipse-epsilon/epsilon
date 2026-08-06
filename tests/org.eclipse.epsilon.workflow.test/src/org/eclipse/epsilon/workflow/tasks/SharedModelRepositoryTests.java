package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.models.ModelRepositoryManager;
import org.junit.Test;

public class SharedModelRepositoryTests {
	
	@Test
	public void testWithSharedModelRepository() throws Exception {
		testDoubleRun(true, "n0", "n0");
	}
	
	@Test
	public void testWithoutSharedModelRepository() throws Exception {
		testDoubleRun(false, "n0", "n1");
	}
	
	public void testDoubleRun(boolean shared, String... expected) throws Exception {
		
		File buildFile = FileUtil.getFileStandalone("sharedmodelrepository.xml", SharedModelRepositoryTests.class);
		
		try {
			AntRunner runner = new AntRunner();
			for (int i=0; i<=1; i++) {
				
				Map<String, String> properties = new HashMap<String, String>();
				
				// Putting an EpsilonTask.EPSILON_REPOSITORY property in the properties
				// of AntRunner, causes Epsilon tasks to use this named model repository
				// This is useful to support sharing repositories across runs
				if (shared) properties.put(EpsilonTask.EPSILON_REPOSITORY, "R1");
				properties.put("name", "n" + i);
				properties.put("expected", expected[i]);
				runner.addUserProperties(properties);
				
				runner.setBuildFileLocation(buildFile.getAbsolutePath());
				runner.run();
			}
		}
		finally {
			ModelRepositoryManager.getInstance().dispose();
		}
	}
	
}
