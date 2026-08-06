/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SpreadsheetUtilsTest {

	@Test
	public void testConvertObjectToList_null() {
		List<String> list = SpreadsheetUtils.convertObjectToList(null);
		assertTrue(list.size() == 1);
		assertTrue(list.get(0).equals("null"));
	}

	@Test
	public void testConvertObjectToList_String() {
		List<String> list = SpreadsheetUtils.convertObjectToList("VALUE");
		assertTrue(list.size() == 1);
		assertTrue(list.get(0).equals("VALUE"));
	}

	@Test
	public void testConvertObjectToList_Collection() {
		List<String> values = Arrays.asList("V1", "V2");
		List<String> list = SpreadsheetUtils.convertObjectToList(values);
		assertTrue(list.size() == 2);
		assertTrue(list.contains("V1"));
		assertTrue(list.contains("V2"));
	}

	@Test
	public void removeFirstChar() {
		StringBuilder sb = new StringBuilder("VAL");
		SpreadsheetUtils.removeFirstChar(sb);
		assertTrue(sb.toString().equals("AL"));
	}

	@Test
	public void removeFirstChar_Empty() {
		StringBuilder sb = new StringBuilder();
		SpreadsheetUtils.removeFirstChar(sb);
		assertTrue(sb.toString().equals(""));
	}

	@Test
	public void removeFirst() {
		StringBuilder sb = new StringBuilder("VALUE");
		SpreadsheetUtils.removeFirst(sb, "VA");
		assertTrue(sb.toString().equals("LUE"));
	}

	@Test
	public void removeFirst_NoMatch() {
		StringBuilder sb = new StringBuilder("VALUE");
		SpreadsheetUtils.removeFirst(sb, "AL");
		assertTrue(sb.toString().equals("VALUE"));
	}

	@Test
	public void removeFirst_Empty() {
		StringBuilder sb = new StringBuilder();
		SpreadsheetUtils.removeFirst(sb, "");
		assertTrue(sb.toString().equals(""));
	}

	@Test
	public void removeLastChar() {
		StringBuilder sb = new StringBuilder("VAL");
		SpreadsheetUtils.removeLastChar(sb);
		assertTrue(sb.toString().equals("VA"));
	}

	@Test
	public void removeLastChar_Empty() {
		StringBuilder sb = new StringBuilder();
		SpreadsheetUtils.removeLastChar(sb);
		assertTrue(sb.toString().equals(""));
	}

	@Test
	public void removeLast() {
		StringBuilder sb = new StringBuilder("VALUE");
		SpreadsheetUtils.removeLast(sb, "UE");
		assertTrue(sb.toString().equals("VAL"));
	}

	@Test
	public void removeLast_NoMatch() {
		StringBuilder sb = new StringBuilder("VALUE");
		SpreadsheetUtils.removeLast(sb, "AL");
		assertTrue(sb.toString().equals("VALUE"));
	}

	@Test
	public void removeLast_Empty() {
		StringBuilder sb = new StringBuilder();
		SpreadsheetUtils.removeLast(sb, "");
		assertTrue(sb.toString().equals(""));
	}

}
