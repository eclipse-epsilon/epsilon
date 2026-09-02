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
	
	/**
	 * Regression test for issue #235: an {@link org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor}
	 * registered through the <code>org.eclipse.epsilon.common.dt.operationContributor</code>
	 * extension point must be discovered when a script runs headlessly through the
	 * workflow Ant tasks. Before the fix, calling the contributed <code>sayHello()</code>
	 * operation failed with "Method 'sayHello' not found for: World", causing the build to fail.
	 *
	 * @see HelloOperationContributor
	 */
	@Test
	public void testOperationContributor() throws Exception {
		runBuild("operationContributor.xml");
	}
	
	protected void runBuild(String buildFile) throws Exception {
		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(FileUtil.getFileStandalone(buildFile, HeadlessOSGiTests.class).getAbsolutePath());
		runner.run();
	}
	
}
