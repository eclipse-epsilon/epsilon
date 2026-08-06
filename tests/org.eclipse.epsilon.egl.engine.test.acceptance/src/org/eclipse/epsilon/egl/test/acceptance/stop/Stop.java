/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.stop;

import java.io.File;

import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.junit.BeforeClass;
import org.junit.Test;

public class Stop {

	private static File Stop;
	private static File StopNested;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {
		Stop       = org.eclipse.epsilon.common.util.FileUtil.getFileStandalone("Stop.egl",       Stop.class);
		StopNested = org.eclipse.epsilon.common.util.FileUtil.getFileStandalone("StopNested.egl", Stop.class);
	}
	
	@Test
	public void stop() throws Exception {
		AcceptanceTestUtil.test(Stop, "Before");
	}
	
	@Test
	public void stopNested() throws Exception {
		AcceptanceTestUtil.test(StopNested, "NestedBefore" + FileUtil.NEWLINE +
		                                    "Before"       + FileUtil.NEWLINE +
		                                    "NestedAfter");
	}
}
