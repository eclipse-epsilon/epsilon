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
package org.eclipse.epsilon.hutn.dt.editor.contentAssist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ContextualiserTests {

	@Test
	public void shouldBePackageNameWhenAtPackageLevel() {
		final String text = "families { ";
		
		assertEquals("families", new Contextualiser().contextualise(text));
	}
	
	@Test
	public void shouldBeClassNameWhenAtClassLevel() {
		final String text = "families {" +
		                    "	Person { ";
		
		assertEquals("Person", new Contextualiser().contextualise(text));
	}
	
	@Test
	public void shouldBeClassNameAfterAClass() {
		final String text = "families {" +
		                    "	Person { } ";
		
		assertEquals("families", new Contextualiser().contextualise(text));
	}
	
	@Test
	public void shouldBeClassNameAfterPrimitiveSlot() {
		final String text = "families {" +
		                    "	Person { " +
		                    "      name: \"John\" ";
		
		assertEquals("Person", new Contextualiser().contextualise(text));
	}
	
	@Test
	public void shouldBeClassNameWhenAtClassLevelAndClassHasIdentifier() {
		final String text = "families {" +
		                    "	Person \"John\" { ";
		
		assertEquals("Person", new Contextualiser().contextualise(text));
	}
	
	@Test
	public void shouldBeClassNameWhenAtClassLevelAndClassHasIdentifierWithSpaces() {
		final String text = "families {" +
		                    "	Person \"John P Doe\" { ";
		
		assertEquals("Person", new Contextualiser().contextualise(text));
	}
	
	@Test
	public void shouldBeNullForEmptyString() {
		assertEquals(null, new Contextualiser().contextualise(""));
	}
	
	@Test
	public void shouldBeNullForStringWithNoBrace() {
		assertEquals(null, new Contextualiser().contextualise("foo bar"));
	}
}
