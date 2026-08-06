package org.eclipse.epsilon.workflow.tasks;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.epsilon.common.util.FileUtil;
import org.junit.Test;

public class HeadlessOSGiTests {
	
	@Test
	public void testEol() throws Exception {
		runBuild("eol.xml");
	}
	
	@Test
	public void testEmg() throws Exception {
		runBuild("emg.xml");
		
	}
	
	protected void runBuild(String buildFile) throws Exception {
		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(FileUtil.getFileStandalone(buildFile, HeadlessOSGiTests.class).getAbsolutePath());
		runner.run();
	}
	
}
