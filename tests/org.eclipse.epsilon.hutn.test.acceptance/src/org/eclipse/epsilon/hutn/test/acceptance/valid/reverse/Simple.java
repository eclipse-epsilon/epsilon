/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: Simple.java,v 1.1 2008/08/07 12:44:24 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid.reverse;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

public class Simple {
	
	private static String hutn;
	
	private static String spec;
	private static String metamodel;
	private static String families;
	private static String family;
	
	@BeforeClass
	public static void generateHutn() throws Exception {
		hutn = "@Spec {"                              +
		       "	MetaModel \"FamiliesMetaModel\" {" +
		       "		nsUri = \"families\""          +
		       "	}"                                 +
		       "}"                                    +
		       "Families {"                           +
		       "	Family \"The Smiths\" {"           +
		       "		name: \"The Smiths\""          +
		       "	}"                                 +
		       "}";
		
		
		spec      = findSection("@Spec");
		metamodel = findSection(spec, "MetaModel \"FamiliesMetaModel\"");
		
		families  = findSection("Families");
		family    = findSection("Family \"The Smiths\"");
	}
	
	@Test
	public void hutnShouldContainHeader() {
		assertTrue(containsSection("@Spec"));	
	}
	
	@Test
	public void specShouldContainMetamodel() {
		assertTrue(containsSection(spec, "MetaModel \"FamiliesMetaModel\""));	
	}
	
	@Test
	public void metamodelShouldHaveCorrectNsUri() {
		assertEquals("\"families\"", getAttributeValue(metamodel, "nsUri"));
	}
	
	@Test
	public void hutnShouldContainFamiliesPackage() {
		assertTrue(containsSection("Families"));	
	}
	
	@Test
	public void familiesPackageShouldContainTheSmiths() {
		assertTrue(containsSection(families, "Family \"The Smiths\""));	
	}
	
	@Test
	public void theSmithsShouldHaveCorrectName() {
		assertEquals("\"The Smiths\"", getAttributeValue(family, "name"));	
	}

	

	/////
	protected String getAttributeValue(String parent, String attributeName) {
		final Matcher m = getMatcher(parent, attributeName + "\\s*[=|:]\\s*(.*)\\s");
		
		assertTrue("Attribute \"" + attributeName + "\" not found in " + parent, m.find());
		
		return m.group(1);
	}
	
	protected static boolean containsSection(String name) {
		return containsSection(hutn, name);
	}
	
	protected static boolean containsSection(String parent, String name) {
		return findSection(parent, name) != null;
	}
	
	protected static String findSection(String name) {
		return findSection(hutn, name);
	}
	
	protected static String findSection(String parent, String name) {
		final Matcher m = getMatcher(parent, name + " {");
		
		assertTrue("Section with name \"" + name + "\" not found in " + parent, m.find());
		
		return findEndBrace(parent.substring(m.start()));
	}
	
	protected static String findEndBrace(String s) {
		int index = 0;
		int nestingLevel = 0;
		boolean foundStart = false;
		
		while (index < s.length() && (nestingLevel != 0 || !foundStart)) {
			final char c = s.charAt(index);
			
			if (c == '{') {
				if (nestingLevel == 0) {
					foundStart = true;
				}
				
				nestingLevel++;
			
			} else if (c == '}') {
				nestingLevel--;
			}
			
			index++;
		}
		
		return nestingLevel == 0 ? s.substring(0, index) : null;
	}

	protected static String escape(String regex) {
		String escaped = regex;
		
		escaped = escaped.replaceAll("\\{", "\\\\{");
		escaped = escaped.replaceAll("\\}", "\\\\}");
		
		return escaped;
	}
	
	protected static Matcher getMatcher(String source, String regex) {
		return Pattern.compile(escape(regex)).matcher(source);
	}
}
