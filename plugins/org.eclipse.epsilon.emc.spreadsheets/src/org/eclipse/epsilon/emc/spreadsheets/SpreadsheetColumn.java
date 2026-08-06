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

import java.util.Objects;

import org.eclipse.epsilon.common.util.StringUtil;

/**
 * This class represents a column of a worksheet. Each column must belong to a
 * worksheet and have a known, valid index. A valid index is greater than
 * returned by {@link SpreadsheetWorksheet#getColumnStartingIndex()}.
 * 
 * @author Martins Francis
 */
public abstract class SpreadsheetColumn {
	
	protected SpreadsheetWorksheet worksheet;
	protected int index;
	protected String name;
	protected String alias;
	protected SpreadsheetDataType dataType;
	protected boolean many;
	protected String delimiter;

	public SpreadsheetColumn(final SpreadsheetWorksheet worksheet, final int index) {
		this.validateConstructorArguments(worksheet, index);

		this.worksheet = worksheet;
		this.index = index;
		this.name = null;
		this.alias = null;
		this.dataType = SpreadsheetConstants.DEFAULT_COL_DATATYPE;
		this.many = SpreadsheetConstants.DEFAULT_COL_MANY;
		this.delimiter = SpreadsheetConstants.DEFAULT_COL_DELIMITER;
	}

	/**
	 * This method ensures 1) given worksheet is not null 2) index is less than
	 * value returned by {@link SpreadsheetWorksheet#getColumnStartingIndex()} -
	 * otherwise an IllegalArgumentException is thrown.
	 * 
	 * @param worksheet
	 * @param index
	 */
	protected void validateConstructorArguments(final SpreadsheetWorksheet worksheet, final int index) {
		String message = null;
		if (worksheet == null) {
			message = "A column must belong to a worksheet";
		}
		else if (index < 0) {
			message = String.format("Indexing starts with 1 - received %d", index);
		}

		if (message != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * @return the worksheet this column belongs to
	 */
	public SpreadsheetWorksheet getWorksheet() {
		return this.worksheet;
	}

	/**
	 * @return the index of the column
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * @return the name of the column
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the alias of the column
	 */
	public String getAlias() {
		return this.alias;
	}

	/**
	 * @return the data type of the column
	 */
	public SpreadsheetDataType getDataType() {
		return this.dataType;
	}

	/**
	 * @return the multiplicity of the column i.e. whether it may contain multiple
	 *         delimiter-separated values
	 */
	public boolean isMany() {
		return this.many;
	}

	/**
	 * @return true if this column may not store multiple values in a single cell
	 */
	public boolean isNotMany() {
		return !this.isMany();
	}

	/**
	 * @return the delimiter used for separating the individual cell values. This is
	 *         useful if a column may contain multiple values
	 */
	public String getDelimiter() {
		return this.delimiter;
	}

	/**
	 * @param name the name of the column
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param alias the alias of the column
	 */
	public void setAlias(final String alias) {
		this.alias = alias;
	}

	/**
	 * @param dataType the data type of the column
	 */
	public void setDataType(final SpreadsheetDataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * @param many the multiplicity of the column
	 */
	public void setMany(final boolean many) {
		this.many = many;
	}

	/**
	 * @param delimiter the delimiter used for separating the values stored in
	 *                  individual cells of the column
	 */
	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Column can be identified either by its name, alias or prefixed column index
	 * 
	 * @param identifier
	 * @return true if column can be identified by the identifier, false otherwise
	 */
	public boolean isIdentifiableBy(final String identifier) {
		if (identifier != null) {
			return Objects.equals(this.name, identifier) || Objects.equals(this.alias, identifier)
				|| this.isValidIndex(identifier);
		}
		return false;
	}

	/**
	 * This method returns the index of this column prefixed by
	 * SpreadsheetConstants.PREFIX_COLUMN.
	 * 
	 * @return prefixed index
	 */
	public String getPrefixedIndex() {
		return SpreadsheetConstants.PREFIX_COLUMN + Integer.toString(this.index);
	}

	/**
	 * This method checks if the index embedded within the provided prefixed column
	 * index is equal to the index of this column i.e. it will always return true if
	 * identifier is equal to the output of getPrefixedIndex().
	 * 
	 * @param identifier
	 * @return true if index of the column equals the embedded index
	 */
	public boolean isValidIndex(final String identifier) {
		final boolean identifierIsPrefixed = !StringUtil.isEmpty(identifier)
			&& identifier.startsWith(SpreadsheetConstants.PREFIX_COLUMN);
		if (identifierIsPrefixed) {
			final int lengthOfPrefix = SpreadsheetConstants.PREFIX_COLUMN.length();
			final int extractedIndex = Integer.parseInt(identifier.substring(lengthOfPrefix));
			return this.index == extractedIndex;
		}
		return false;
	}

	/**
	 * @return an identifier of the column. If name is set then it is returned,
	 *         otherwise if alias is set then it is returned. If neither is set then
	 *         prefixed column index is returned
	 */
	public String getIdentifier() {
		if (this.name != null) {
			return this.name;
		}
		else if (this.alias != null) {
			return this.alias;
		}
		else {
			return this.getPrefixedIndex();
		}
	}

	@Override
	public String toString() {
		String ret = "SpreadsheetColumn (" + Integer.toString(this.index) + "): [worksheet='" + worksheet.getName()
			+ "', ";
		if (this.name != null) {
			ret += "name='" + this.name + "', ";
		}
		if (this.alias != null) {
			ret += "alias='" + this.alias + "', ";
		}
		if (this.dataType != null) {
			ret += "dataType='" + SpreadsheetDataType.formatAsString(this.dataType) + "', ";
		}
		ret += "many='" + Boolean.toString(many) + "', ";
		ret += "delimiter='" + this.delimiter + "'";
		return ret + "]";
	}

}
