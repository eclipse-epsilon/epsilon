/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.*;
import static org.eclipse.epsilon.common.util.OperatingSystem.isWindows;
import org.eclipse.epsilon.emc.emf.tools.EmfTool;
import org.junit.Test;

public class EmfToolResolveUriTests {

	@Test
	public void shouldResolveRelativeToDirectory() throws Exception {
		String root = "/a/path/";
		if (isWindows()) root = "c:"+root;
		final String nearby = "nearby.model";
		
		final String actual = new EmfTool().resolveURI(nearby, root);
		
		assertEquals(root + nearby, actual.replace('\\', '/'));
	}
	
	@Test
	public void shouldResolveRelativeToFile() throws Exception {
		String root = "/a/path/";
		if (isWindows()) root = "c:"+root;
		final String nearby = "nearby.model";
		
		final String actual = new EmfTool().resolveURI(nearby, root+"source.file");
		
		assertEquals(root + nearby, actual.replace('\\', '/'));
	}
	
	@Test
	public void shouldNotResolveTargetThatIsAbsolute() throws Exception {
		String path = "/a/path/nearby.model";
		if (isWindows()) path = "c:"+path;
		
		final String actual = new EmfTool().resolveURI(path, "/another/path/source.file");
		
		assertEquals(path, actual);
	}
	
	@Test
	public void shouldNotResolveTargetThatHasASchema() throws Exception {
		final String actual = new EmfTool().resolveURI("file:/a/path/nearby.model#//@contents.0", "/another/path/source.file");
		
		assertEquals("file:/a/path/nearby.model#//@contents.0", actual);
	}
	
	@Test
	public void shouldNotChangeUriFragment() throws Exception {
		String root = "/a/path/";
		if (isWindows()) root = "c:"+root;
		final String nearby = "nearby.model#//@contents.0";
		
		final String actual = new EmfTool().resolveURI(nearby, root+"source.file");
		
		assertEquals(root+"nearby.model#//@contents.0", actual.replace('\\', '/'));
	}
}
