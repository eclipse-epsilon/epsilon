/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.unparser.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.hutn.model.hutn.Object;
import org.junit.Test;


public abstract class AbstractObjectUnparserTest {

	protected static String unparsed;
	protected static Object expected;
	
	@Test
	public void textStartsWithType() {
		assertEquals(expected.getType(), type());
	}
	
	@Test
	public void identifierFollowsType() {
		if (expected.getIdentifier() == null) {
			assertEquals("", identifier());
		} else {
			assertEquals('"' + expected.getIdentifier() + '"', identifier());
		}
	}
	
	@Test
	public void bodyStartsWithBrace() {
		assertTrue(body().startsWith("{"));
	}
	
	@Test
	public void bodyEndsWithBrace() {
		assertTrue(body().endsWith("}"));
	}
	
	
	private static String type() {
		return unparsed.split(" ")[0].trim();
	}
	
	private static String identifier() {
		return unparsed.split(" ")[1].trim();
	}
	
	private static String body() {
		final String[] split = unparsed.split(" ");
		final StringBuilder sb = new StringBuilder();
		
		for (int index=2; index<split.length; index++) {
			sb.append(split[index]);
		}
		
		return sb.toString();
	}
	
	protected static String bodyContents() {
		return unparsed.substring(unparsed.indexOf('{')+1, unparsed.lastIndexOf('}'));
	}
}
