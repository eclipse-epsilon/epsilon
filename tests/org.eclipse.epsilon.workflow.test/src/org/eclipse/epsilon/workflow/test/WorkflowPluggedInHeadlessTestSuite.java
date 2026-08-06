package org.eclipse.epsilon.workflow.test;

import org.eclipse.epsilon.workflow.tasks.HeadlessOSGiTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({HeadlessOSGiTests.class})
public class WorkflowPluggedInHeadlessTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(WorkflowPluggedInHeadlessTestSuite.class);
	}
}
