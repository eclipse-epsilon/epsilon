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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class represents a row of a worksheet. A row must always belong to a
 * worksheet.
 * 
 * @author Martins Francis
 */
public abstract class SpreadsheetRow {
	
	protected SpreadsheetWorksheet worksheet;

	public SpreadsheetRow(final SpreadsheetWorksheet worksheet) {
		if (worksheet == null) {
			final String message = "Row must belong to a worksheet";
			throw new IllegalArgumentException(message);
		}
		this.worksheet = worksheet;
	}

	public SpreadsheetModel getModel() {
		return this.worksheet.getModel();
	}

	public SpreadsheetWorksheet getWorksheet() {
		return this.worksheet;
	}

	public SpreadsheetColumn getColumn(final String columnIdentifier) {
		return this.worksheet.getColumn(columnIdentifier);
	}

	public Set<SpreadsheetReference> getReferencesBySource(final SpreadsheetColumn column) {
		return this.getWorksheet().getModel().getReferencesBySource(this.getWorksheet(), column);
	}

	public Set<SpreadsheetReference> getReferencesByTarget(final SpreadsheetColumn column) {
		return this.getWorksheet().getModel().getReferencesByTarget(this.getWorksheet(), column);
	}

	/**
	 * The purpose of this method is to return the visible value stored by the given
	 * cell. The 'visible value' means the value stored by the cell and observed
	 * when the row is viewed in the spreadsheet. This means any worksheet metadata
	 * shall be disregarded by this method.
	 * 
	 * @param column
	 * @return the visible value of the cell
	 */
	public abstract String getVisibleCellValue(SpreadsheetColumn column);

	protected void validateColumn(final SpreadsheetColumn column) {
		if (column == null) {
			final String message = "Column must not be null";
			throw new IllegalArgumentException(message);
		}

		if (column.getWorksheet() != this.worksheet) {
			final String message = "Column is from another worksheet";
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * The purpose of this method is to return all visible values of the given cell.
	 * This means that cell multiplicity is accounted for when reading the cell
	 * however any other worksheet metadata information is disregarded. Whitespace
	 * around every cell value is removed before it is returned if specified to do
	 * so.
	 * 
	 * @param column
	 * @param trim
	 * @return all visible values stored in the cell
	 */
	public List<String> getAllVisibleCellValues(final SpreadsheetColumn column, final boolean trim) {
		final List<String> values = new ArrayList<>();
		final String visibleCellValue = this.getVisibleCellValue(column);
		if (visibleCellValue != null && !visibleCellValue.isEmpty()) {
			if (column.isMany()) {
				for (final String value : visibleCellValue.split(column.getDelimiter())) {
					values.add(trim ? value.trim() : value);
				}
			}
			else {
				values.add(trim ? visibleCellValue.trim() : visibleCellValue);
			}
		}
		return values;
	}

	/**
	 * This method returns a list of every value contained by the cell.
	 * 
	 * @param column
	 * @return cell values
	 */
	public List<String> getAllVisibleCellValuesAsIs(final SpreadsheetColumn column) {
		return this.getAllVisibleCellValues(column, false);
	}

	/**
	 * This method returns a list of every value contained by the cell. Each value
	 * is trimmed before adding to the list.
	 * 
	 * @param column
	 * @return cell values, trimmed
	 */
	public List<String> getAllVisibleCellValues(final SpreadsheetColumn column) {
		return this.getAllVisibleCellValues(column, true);
	}

	/**
	 * The purpose of this method is to overwrite the value of the cell.
	 * 
	 * @param column
	 * @param value  the new value of the cell
	 */
	public abstract void overwriteCellValue(SpreadsheetColumn column, String value);

	/**
	 * The purpose of this method is to write the given values to the given cell. If
	 * the column may contain multiple values then each value from the list is
	 * separated by column's delimiter before writing to the cell. If the column may
	 * contain only one value then only the first value from the list is written.
	 * Data type conformity is enforced if specified to do so in metadata
	 * configuration.
	 * 
	 * @param column
	 * @param valuesToWrite
	 */
	public void writeVisibleCellValues(final SpreadsheetColumn column, final List<String> valuesToWrite) {
		if (column.isMany() || valuesToWrite.isEmpty()) {
			this.writeManyVisibleValues(column, valuesToWrite);
		}
		else {
			this.writeOneVisibleValue(column, valuesToWrite.get(0));
		}
	}

	/**
	 * This method writes the given values to the cell. Data type conformity is
	 * enforced for each value if applicable.
	 * 
	 * @param column
	 * @param valuesToWrite
	 */
	protected void writeManyVisibleValues(final SpreadsheetColumn column, final List<String> valuesToWrite) {
		final StringBuilder newCellValue = new StringBuilder();
		for (String value : valuesToWrite) {
			value = SpreadsheetUtils.getValueConformingToDataType(column, value);
			newCellValue.append(column.getDelimiter());
			newCellValue.append(value);
		}
		SpreadsheetUtils.removeFirstChar(newCellValue);
		this.overwriteCellValue(column, newCellValue.toString());
	}

	/**
	 * This method writes the given value to the cell. Data type conformity is
	 * enforced if applicable.
	 * 
	 * @param column
	 * @param valueToWrite
	 */
	protected void writeOneVisibleValue(final SpreadsheetColumn column, final String valueToWrite) {
		String value = valueToWrite;
		if (column.getWorksheet().isDataTypeStrict()) {
			value = SpreadsheetDataType.castColumnValue(column.getDataType(), value).toString();
		}
		this.overwriteCellValue(column, value);
	}

	// public abstract int hashCode();

	// public abstract boolean equals(Object other);

	@Override
	public String toString() {
		final StringBuilder stringObject = new StringBuilder(this.worksheet.getName() + " [");
		for (final SpreadsheetColumn column : this.worksheet.getHeader().getColumns()) {
			final String headerName = column.getIdentifier();
			final String cellValue = this.getVisibleCellValue(column);
			if (cellValue != null) {
				stringObject.append(headerName);
				stringObject.append("='");
				stringObject.append(cellValue);
				stringObject.append("', ");
			}
		}
		SpreadsheetUtils.removeLast(stringObject, ", ");
		stringObject.append("]");
		return stringObject.toString();
	}

}
