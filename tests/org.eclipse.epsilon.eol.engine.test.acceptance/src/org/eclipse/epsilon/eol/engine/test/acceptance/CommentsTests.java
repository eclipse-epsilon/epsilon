/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance;

import static org.junit.Assert.assertEquals;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.Test;

/**
 * 
 * @author Dimitris Kolovos
 * @since 2.2
 */
public class CommentsTests {
	
	@Test
	public void testSingleLineComment() throws Exception {
		EolModule module = new EolModule();
		module.parse("//c1\nif (true){}");
		assertEquals("c1", module.getMain().getStatements().get(0).getComments().get(0).toString());
	}
	
	@Test
	public void testMultiLineComment() throws Exception {
		EolModule module = new EolModule();
		module.parse("/*c1*/\nif (true){}");
		assertEquals("c1", module.getMain().getStatements().get(0).getComments().get(0).toString());
	}
}
