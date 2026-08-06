/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.eunit;

import static org.junit.Assert.fail;

import org.apache.tools.ant.BuildException;
import org.junit.Test;

/**
 * Tests for the EVL integration in EUnit.
 *
 * @author Antonio Garcia-Dominguez
 */
public class EUnitWithEVLTests extends EUnitTestCase {
	@Test
	public void valid() throws Exception {
		runTarget(ANT_BUILD_FILE, "evl-valid");
	}

	@Test
	public void errorFailsBuild() throws Exception {
		try {
			runTarget(ANT_BUILD_FILE, "evl-error-fails-build");
			fail("The build was expected to fail");
		} catch (BuildException ex) {
			final String message = ex.getCause().getCause().getMessage();
			assertContains(message, "1 error");
			assertContains(message, "0 warning");
		}
	}

	@Test
	public void warningFailsBuild() throws Exception {
		try {
			runTarget(ANT_BUILD_FILE, "evl-warning-fails-build");
			fail("The build was expected to fail");
		} catch (BuildException ex) {
			final String message = ex.getCause().getCause().getMessage();
			assertContains(message, "0 error");
			assertContains(message, "1 warning");
		}
	}

	@Test
	public void failOnErrorsHonored() throws Exception {
		runTarget(ANT_BUILD_FILE, "evl-failOnErrors-honored");
	}

	@Test
	public void failOnWarningsHonored() throws Exception {
		runTarget(ANT_BUILD_FILE, "evl-failOnWarnings-honored");
	}

	@Test
	public void exportAsModel() throws Exception {
		runTarget(ANT_BUILD_FILE, "evl-exportAsModel");
	}

	@Test
	public void guardedFixes() throws Exception {
		runTarget(ANT_BUILD_FILE, "evl-guarded-fixes");
	}
}
