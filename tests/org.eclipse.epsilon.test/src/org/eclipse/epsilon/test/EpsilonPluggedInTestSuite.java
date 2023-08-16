/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.test;

import org.eclipse.epsilon.common.dt.test.CommonDevelopmentToolsTestSuite;
import org.eclipse.epsilon.ecore.delegates.test.acceptance.DelegatesSuite;
import org.eclipse.epsilon.emc.cdo.tests.CDOPluggedInTestSuite;
import org.eclipse.epsilon.emc.emf.test.EmfPluggedInTestSuite;
import org.eclipse.epsilon.picto.test.PictoTestSuite;
import org.eclipse.epsilon.workflow.tasks.eugenia.EugeniaTestSuite;
import org.eclipse.epsilon.workflow.test.WorkflowPluggedInTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

/**
 * Test suite to be run as JUnit Plugged In test.
 * <code>mvn -f tests/org.eclipse.epsilon.test install -P plugged</code>.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({
	CommonDevelopmentToolsTestSuite.class,
	EmfPluggedInTestSuite.class,
	EugeniaTestSuite.class,
	WorkflowPluggedInTestSuite.class,
	CDOPluggedInTestSuite.class,
	DelegatesSuite.class/*,
	PictoTestSuite.class*/
})
public class EpsilonPluggedInTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(EpsilonPluggedInTestSuite.class);
	}
}
