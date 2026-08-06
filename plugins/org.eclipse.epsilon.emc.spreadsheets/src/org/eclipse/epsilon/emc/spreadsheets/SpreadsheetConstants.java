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

/**
 * This class holds various spreadsheet driver-wide constants.
 * 
 * @author Martins Francis
 */
public class SpreadsheetConstants {
	public static final String DT_STRING = "string";
	public static final String DT_INTEGER = "integer";
	public static final String DT_BOOLEAN = "boolean";
	public static final String DT_DOUBLE = "double";
	public static final String DT_FLOAT = "float";

	public static final String DEFAULT_DT_STRING = " ";
	public static final String DEFAULT_DT_INTEGER = "0";
	public static final String DEFAULT_DT_BOOLEAN = "false";
	public static final String DEFAULT_DT_DOUBLE = "0"; // 0.0d displayed as 0 in spreadsheets
	public static final String DEFAULT_DT_FLOAT = "0"; // 0.0f displayed as 0 in spreadsheets
	public static final String DEFAULT_DT_VALUE = " ";

	public static final boolean DEFAULT_WORKSHEET_DT_STRICT = false;
	public static final boolean DEFAULT_WORKSHEET_CREATE_ON_LOAD = false;

	public static final SpreadsheetDataType DEFAULT_COL_DATATYPE = SpreadsheetDataType.STRING;
	public static final boolean DEFAULT_COL_MANY = false;
	public static final String DEFAULT_COL_DELIMITER = ",";
	public static final String PREFIX_COLUMN = "c_";
	public static final String PREFIX_VALUE = "v_";

	public static final boolean DEFAULT_REFERENCE_MANY = false;
	public static final boolean DEFAULT_REFERENCE_CASCADE = false;

	/**
	 * This application-wide constant is used to identify worksheets that should be
	 * ignored when the model is loaded. Any worksheet whose name starts with those
	 * characters will be ignored.
	 */
	public static final String WORKSHEET_IGNORE_CHARS = "--";

	/**
	 * This application-wide constant is used to split the column names i.e. the
	 * name will be set to the value preceding characters defined here. For example,
	 * if definition is "-" and name in worksheet is "first-name" then name of
	 * column will be set to "first".
	 */
	public static final String HEADER_NAME_SPLIT_CHARS = "-";

}
