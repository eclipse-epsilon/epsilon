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
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.junit.BeforeClass;
import org.junit.Test;

public class SpreadsheetDataTypeTest {

	@BeforeClass
	public static void testEnum() {
		SharedTestMethods.superficialEnumCodeCoverage(SpreadsheetDataType.class);
	}

	@Test
	public void testConvertString() {
		SpreadsheetDataType dataType = SpreadsheetDataType.convert("sTrinG");
		assertTrue(dataType == SpreadsheetDataType.STRING);
	}

	@Test
	public void testConvertInteger() {
		SpreadsheetDataType dataType = SpreadsheetDataType.convert("inteGer");
		assertTrue(dataType == SpreadsheetDataType.INTEGER);
	}

	@Test
	public void testConvertDouble() {
		SpreadsheetDataType dataType = SpreadsheetDataType.convert("DOUBLE");
		assertTrue(dataType == SpreadsheetDataType.DOUBLE);
	}

	@Test
	public void testConvertFloat() {
		SpreadsheetDataType dataType = SpreadsheetDataType.convert("float");
		assertTrue(dataType == SpreadsheetDataType.FLOAT);
	}

	@Test
	public void testConvertBoolean() {
		SpreadsheetDataType dataType = SpreadsheetDataType.convert("bOOlean");
		assertTrue(dataType == SpreadsheetDataType.BOOLEAN);
	}

	@Test
	public void testConvertUnknown() {
		SpreadsheetDataType dataType = SpreadsheetDataType.convert("UNKNOWN");
		assertTrue(dataType == SpreadsheetConstants.DEFAULT_COL_DATATYPE);
	}

	@Test
	public void testConvertNull() {
		SpreadsheetDataType dataType = SpreadsheetDataType.convert(null);
		assertTrue(dataType == SpreadsheetConstants.DEFAULT_COL_DATATYPE);
	}

	@Test
	public void testCastNullDataType() {
		Object value = SpreadsheetDataType.castColumnValue(null, "string");
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_VALUE));
	}

	@Test
	public void testCastString() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.STRING, "string");
		assertTrue(value.equals("string"));
	}

	@Test
	public void testCastStringNullValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.STRING, null);
		assertTrue(value.equals("null"));
	}

	@Test
	public void testCastInteger() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.INTEGER, "123");
		assertTrue(value.equals(123));
	}

	@Test
	public void testCastIntegerInvalidValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.INTEGER, "notint");
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
	}

	@Test
	public void testCastIntegerNullValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.INTEGER, null);
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
	}

	@Test
	public void testCastDouble() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.DOUBLE, "123.00789");
		assertTrue(value.equals(123.00789));
	}

	@Test
	public void testCastDoubleInvalidValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.DOUBLE, "notdouble");
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
	}

	@Test
	public void testCastDoubleNullValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.DOUBLE, null);
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
	}

	@Test
	public void testCastFloat() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.FLOAT, "123.007894");
		assertTrue(value.equals(Float.valueOf("123.007894")));
	}

	@Test
	public void testCastFloatInvalidValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.FLOAT, "notfloat");
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_FLOAT));
	}

	@Test
	public void testCastFloatNullValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.FLOAT, null);
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_FLOAT));
	}

	@Test
	public void testCastBoolean() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.BOOLEAN, "true");
		assertTrue(value.equals(true));
	}

	@Test
	public void testCastBooleanInvalidValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.BOOLEAN, "notbool");
		assertTrue(value.equals(false));
	}

	@Test
	public void testCastBooleanNullValue() {
		Object value = SpreadsheetDataType.castColumnValue(SpreadsheetDataType.BOOLEAN, null);
		assertTrue(value.equals(false));
	}

	@Test
	public void testDefaultStringValue() {
		Object value = SpreadsheetDataType.getDefaultDTValue(SpreadsheetDataType.STRING);
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_STRING));
	}

	@Test
	public void testDefaultBooleanValue() {
		Object value = SpreadsheetDataType.getDefaultDTValue(SpreadsheetDataType.BOOLEAN);
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
	}

	@Test
	public void testFormatAsString() {
		String string = SpreadsheetDataType.formatAsString(SpreadsheetDataType.STRING);
		assertTrue(string.equalsIgnoreCase("string"));
		String integer = SpreadsheetDataType.formatAsString(SpreadsheetDataType.INTEGER);
		assertTrue(integer.equalsIgnoreCase("integer"));
		String dbl = SpreadsheetDataType.formatAsString(SpreadsheetDataType.DOUBLE);
		assertTrue(dbl.equalsIgnoreCase("double"));
		String flt = SpreadsheetDataType.formatAsString(SpreadsheetDataType.FLOAT);
		assertTrue(flt.equalsIgnoreCase("float"));
		String bool = SpreadsheetDataType.formatAsString(SpreadsheetDataType.BOOLEAN);
		assertTrue(bool.equalsIgnoreCase("boolean"));
		String nul = SpreadsheetDataType.formatAsString(null);
		assertTrue(nul == null);
	}

}
