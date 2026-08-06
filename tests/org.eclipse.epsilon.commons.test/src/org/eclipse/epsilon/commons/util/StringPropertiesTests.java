/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.commons.util;

import static org.junit.Assert.assertEquals;
import java.util.Map.Entry;
import org.eclipse.epsilon.common.util.StringProperties;
import org.junit.Test;

public class StringPropertiesTests {
	
	@Test
	public void testCreateFromParameters() {
		String parameter = "name=libnames" + System.lineSeparator() +
				"type=String" + System.lineSeparator() + 
				"value=Congress,University,City,Cafe,House" + System.lineSeparator() +
				"size=3"  + System.lineSeparator() +
				"use=false";
		StringProperties sp = new StringProperties(parameter);
		assertEquals(sp.getProperty("name"), ("libnames"));
		assertEquals(sp.getProperty("type"), ("String"));
		assertEquals(sp.getProperty("value"), ("Congress,University,City,Cafe,House"));
		assertEquals(sp.getIntegerProperty("size", 0), (3));
		assertEquals(sp.getBooleanProperty("use", true), (false));
	}
	
	@Test
	public void testHasProperty() {
		StringProperties sp = new StringProperties();
		sp.put("valuedKey", "libnames");
		sp.put("emptyKey", "");
		assertEquals("StringProperties has property valuedKey.", sp.hasProperty("valuedKey"), (true));
		assertEquals("StringProperties does not have property emptyKey.", sp.hasProperty("emptyKey"), (false));
		assertEquals("StringProperties does not have unknown property.", sp.hasProperty("unkown"), (false));
	}
	
	@Test
	public void testCloneProperties() {
		String parameter = "name=libnames" + System.lineSeparator() +
				"type=String" + System.lineSeparator() + 
				"value=Congress,University,City,Cafe,House" + System.lineSeparator() +
				"size=3"  + System.lineSeparator() +
				"use=false";
		StringProperties sp = new StringProperties(parameter);
		StringProperties spClone = sp.clone();
		for(Entry<Object, Object> e : sp.entrySet()) {
			assertEquals("Clone has key", spClone.hasProperty((String) e.getKey()), (true));
			assertEquals("Clone matches va;lue", spClone.getProperty((String) e.getKey()), (e.getValue()));
		}
	}
	
	@Test
	public void testGetBooleanProperty() {
		StringProperties sp = new StringProperties();
		sp.put("trueKey", "true");
		sp.put("falseKey", "false");
		sp.put("notBooleanKey", "notBoolean");
		assertEquals("True key is true.", sp.getBooleanProperty("trueKey", false), (true));
		assertEquals("False key is false.", sp.getBooleanProperty("falseKey", false), (false));
		assertEquals("Not boolean key defaults true", sp.getBooleanProperty("notBooleanKey", true), (true));
		assertEquals("Not boolean key defaults false", sp.getBooleanProperty("notBooleanKey", false), (false));
	}
	
	@Test
	public void testGetIntegerProprety() {
		StringProperties sp = new StringProperties();
		sp.put("integerKey", "3");
		assertEquals("Integer key value is 3", sp.getIntegerProperty("integerKey", -1), (3));
		assertEquals("Integer key value is default(-1)", sp.getIntegerProperty("missingKey", -1), (-1));
	}
	
	@Test
	public void testGetStringDefaultProperty() {
		StringProperties sp = new StringProperties();
		sp.put("someProperty", "someValue");
		assertEquals(sp.getProperty("someProperty"), ("someValue"));
		assertEquals(sp.getProperty("notPresent", "defaultValue"), ("defaultValue"));
	}
}
