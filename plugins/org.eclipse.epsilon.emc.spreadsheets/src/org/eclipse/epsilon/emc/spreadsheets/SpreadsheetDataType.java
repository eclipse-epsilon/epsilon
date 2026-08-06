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

import org.eclipse.epsilon.common.util.StringUtil;

/**
 * This ENUM enlists the various data types supported, namely String, Integer,
 * Double, Float and Boolean. This class provides convenience methods for
 * working with objects and data types.
 * 
 * @author Martins Francis
 */
public enum SpreadsheetDataType {
	STRING, INTEGER, BOOLEAN, DOUBLE, FLOAT;
	
	/**
	 * Converts the given String representation of a data type to the corresponding
	 * internal representation. If it cannot be converted, then
	 * SpreadsheetConstants.DEFAULT_COL_DATATYPE is returned instead.
	 * 
	 * @param dataType
	 * @return internal data type of the given String descriptor or
	 *         SpreadsheetConstants.DEFAULT_COL_DATATYPE
	 */
	public static SpreadsheetDataType convert(final String dataType) {
		if (!StringUtil.isEmpty(dataType)) {
			if (dataType.equalsIgnoreCase(SpreadsheetConstants.DT_STRING)) {
				return SpreadsheetDataType.STRING;
			}
			else if (dataType.equalsIgnoreCase(SpreadsheetConstants.DT_INTEGER)) {
				return SpreadsheetDataType.INTEGER;
			}
			else if (dataType.equalsIgnoreCase(SpreadsheetConstants.DT_BOOLEAN)) {
				return SpreadsheetDataType.BOOLEAN;
			}
			else if (dataType.equalsIgnoreCase(SpreadsheetConstants.DT_DOUBLE)) {
				return SpreadsheetDataType.DOUBLE;
			}
			else if (dataType.equalsIgnoreCase(SpreadsheetConstants.DT_FLOAT)) {
				return SpreadsheetDataType.FLOAT;
			}
		}
		return SpreadsheetConstants.DEFAULT_COL_DATATYPE;
	}

	/**
	 * Ensures that the given value conforms to the given SpreadsheetDataType. If
	 * the value does not conform or cannot be converted then the default value of
	 * the given SpreadsheetDataType is returned.
	 * 
	 * @param dataType
	 * @param value
	 * @return value converted to its data type
	 */
	public static Object castColumnValue(final SpreadsheetDataType dataType, final Object value) {
		try {
			if (dataType == SpreadsheetDataType.STRING) {
				return String.valueOf(value);
			}
			else if (dataType == SpreadsheetDataType.INTEGER) {
				return new Double(String.valueOf(value)).intValue();
			}
			else if (dataType == SpreadsheetDataType.BOOLEAN) {
				return Boolean.parseBoolean(String.valueOf(value));
			}
			else if (dataType == SpreadsheetDataType.DOUBLE) {
				return Double.parseDouble(String.valueOf(value));
			}
			else if (dataType == SpreadsheetDataType.FLOAT) {
				return Float.parseFloat(String.valueOf(value));
			}
			else {
				throw new Exception("Unknown data type: '" + dataType + "'");
			}
		}
		catch (NumberFormatException e) {
			return SpreadsheetDataType.getDefaultDTValue(dataType);
		}
		catch (Exception e) {
			return SpreadsheetDataType.getDefaultDTValue(dataType);
		}
	} 

	public static Object coerceColumnValue(final SpreadsheetDataType dataType, final Object value) {
		try {
			if (dataType == SpreadsheetDataType.STRING) {
				return String.valueOf(value);
			}
			else if (dataType == SpreadsheetDataType.INTEGER) {
				return new Double(String.valueOf(value)).intValue();
			}
			else if (dataType == SpreadsheetDataType.BOOLEAN) {
				return Boolean.parseBoolean(String.valueOf(value));
			}
			else if (dataType == SpreadsheetDataType.DOUBLE) {
				return Double.parseDouble(String.valueOf(value));
			}
			else if (dataType == SpreadsheetDataType.FLOAT) {
				return Float.parseFloat(String.valueOf(value));
			}
			else {
				throw new Exception("Unknown data type: '" + dataType + "'");
			}
		}
		catch (NumberFormatException e) {
			return coerceColumnValue(dataType, SpreadsheetDataType.getDefaultDTValue(dataType));
		}
		catch (Exception e) {
			return coerceColumnValue(dataType, SpreadsheetDataType.getDefaultDTValue(dataType));
		}
	} 
	
	/**
	 * Returns the default value of the given SpreadsheetDataType. If an unknown
	 * data type is given, then SpreadsheetConstants.DEFAULT_DT_VALUE is returned
	 * instead.
	 * 
	 * @param dataType
	 * @return default value for the given data type
	 */
	public static String getDefaultDTValue(final SpreadsheetDataType dataType) {
		if (dataType == SpreadsheetDataType.STRING) {
			return SpreadsheetConstants.DEFAULT_DT_STRING;
		}
		else if (dataType == SpreadsheetDataType.INTEGER) {
			return SpreadsheetConstants.DEFAULT_DT_INTEGER;
		}
		else if (dataType == SpreadsheetDataType.BOOLEAN) {
			return SpreadsheetConstants.DEFAULT_DT_BOOLEAN;
		}
		else if (dataType == SpreadsheetDataType.DOUBLE) {
			return SpreadsheetConstants.DEFAULT_DT_DOUBLE;
		}
		else if (dataType == SpreadsheetDataType.FLOAT) {
			return SpreadsheetConstants.DEFAULT_DT_FLOAT;
		}
		else {
			return SpreadsheetConstants.DEFAULT_DT_VALUE;
		}
	}

	/**
	 * Returns a string representation of the given SpreadsheetDataType. If data
	 * type is unknown then null is returned.
	 * 
	 * @param dataType
	 * @return String representation of the given data type
	 */
	public static String formatAsString(final SpreadsheetDataType dataType) {
		if (dataType == SpreadsheetDataType.STRING) {
			return "String";
		}
		else if (dataType == SpreadsheetDataType.INTEGER) {
			return "Integer";
		}
		else if (dataType == SpreadsheetDataType.BOOLEAN) {
			return "Boolean";
		}
		else if (dataType == SpreadsheetDataType.DOUBLE) {
			return "Double";
		}
		else if (dataType == SpreadsheetDataType.FLOAT) {
			return "Float";
		}
		else {
			return null;
		}
	}

}
