/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.*;
import org.eclipse.epsilon.common.util.OperatingSystem;
import org.junit.Test;

public class URITest {
	
	@Test
	public void testCreateUri() {
		assertEquals("platform:/resource/project/file.txt", createURI("project/file.txt"));
		assertEquals("http://www.foo.com", createURI("http://www.foo.com"));
	}
	
	@Test
	public void testWindows() {
		if (OperatingSystem.isWindows()) {
			assertEquals("file:/c:/foo.txt", createURI("c:/foo.txt"));
		}
	}
	
	@Test
	public void testUnix() {
		if (OperatingSystem.isUnix()) {
			assertEquals("platform:/resource/local/d0/file.txt", createURI("/local/d0/file.txt"));
			assertEquals("file:/local/d0/file.txt",     createFileBasedURI("/local/d0/file.txt"));
			
			assertEquals("file:/local/d0/file.txt", createURI("file:/local/d0/file.txt"));
			assertEquals("platform:/resource/local/d0/file.txt", createURI("platform:/resource/local/d0/file.txt"));
			assertEquals("c:/foo.txt", createURI("c:/foo.txt"));			
		}
	}
	
	protected static String createURI(String s) {
		return EmfUtil.createPlatformResourceURI(s).toString();
	}
	
	protected static String createFileBasedURI(String s) {
		return EmfUtil.createFileBasedURI(s).toString();
	}
}
 
