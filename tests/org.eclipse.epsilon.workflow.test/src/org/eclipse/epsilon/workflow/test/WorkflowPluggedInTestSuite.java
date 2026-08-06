/*******************************************************************************
 * Copyright (c) 2011 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.workflow.tasks.SharedModelRepositoryTests;
import org.eclipse.epsilon.workflow.tasks.eunit.EUnitModelComparisonTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EUnitModelComparisonTests.class, 
			   SharedModelRepositoryTests.class})
public class WorkflowPluggedInTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(WorkflowPluggedInTestSuite.class);
	}
}
