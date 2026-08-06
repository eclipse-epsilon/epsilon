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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetColumnMetadata;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetWorksheetMetadata;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

/**
 * This class represents a worksheet. Worksheets are assumed to be valid only if
 * they have a name. A worksheet may or may not exist in a spreadsheet (model)
 * and this can be indicated through the constructor. If a worksheet does not
 * exist in the spreadsheet it is expected to be created the first time is is
 * accessed.
 * 
 * @author Martins Francis
 */
public abstract class SpreadsheetWorksheet {
	
	protected SpreadsheetModel model;
	protected SpreadsheetWorksheetHeader header;
	protected boolean existsInSpreadsheet;
	protected String name;
	protected String alias;
	protected boolean dataTypeStrict;

	public SpreadsheetWorksheet(final SpreadsheetModel model, final String name, final boolean existsInSpreadsheet) {
		if (StringUtil.isEmpty(name)) {
			String message = "Worksheet must have a name";
			throw new IllegalArgumentException(message);
		}

		this.model = model;
		this.name = name.trim();
		this.alias = null;
		this.existsInSpreadsheet = existsInSpreadsheet;
		this.dataTypeStrict = SpreadsheetConstants.DEFAULT_WORKSHEET_DT_STRICT;
		this.header = new SpreadsheetWorksheetHeader(this);
	}

	public SpreadsheetModel getModel() {
		return this.model;
	}

	public SpreadsheetWorksheetHeader getHeader() {
		return this.header;
	}

	public boolean getExistsInSpreadsheet() {
		return this.existsInSpreadsheet;
	}

	public boolean getDoesNotExistInSpreadsheet() {
		return !this.existsInSpreadsheet;
	}

	public String getName() {
		return this.name;
	}

	public String getAlias() {
		return this.alias;
	}

	public boolean isDataTypeStrict() {
		return this.dataTypeStrict;
	}

	/**
	 * A worksheet can be identified by either it's name or alias.
	 * 
	 * @param identifier
	 * @return true if identifier describes this worksheet
	 */
	public boolean isIdentifiablyBy(final String identifier) {
		if (!StringUtil.isEmpty(identifier)) {
			return this.getName().equals(identifier) || Objects.equals(this.getAlias(), identifier);
		}
		return false;
	}

	/**
	 * The purpose of this method is to create this worksheet in the spreadsheet
	 * 
	 * @throws RuntimeException
	 */
	protected abstract void createInSpreadsheet();

	/**
	 * Loads header row information - index and name - from the spreadsheet if the
	 * worksheet exists in the spreadsheet
	 * 
	 * @throws EolModelLoadingException
	 */
	protected abstract void loadHeader() throws EolModelLoadingException;

	/**
	 * Adds worksheet metadata to the worksheet
	 * 
	 * @param metadata
	 * @throws IllegalArgumentException
	 */
	protected void addWorksheetMetadata(final SpreadsheetWorksheetMetadata metadata) throws IllegalArgumentException {
		if (this.getName().equals(metadata.getName())) {
			if (!StringUtil.isEmpty(metadata.getAlias())) {
				this.alias = metadata.getAlias();
			}
			if (!StringUtil.isEmpty(metadata.getDataTypeStrict())) {
				this.dataTypeStrict = Boolean.parseBoolean(metadata.getDataTypeStrict());
			}
		}
		else {
			final String message = "Incorrect metadata for worksheet '" + this.getName() + "'";
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Gets the column with the matching identifier
	 * 
	 * @param identifier
	 * @return column with the given identifier
	 */
	public SpreadsheetColumn getColumn(final String identifier) {
		for (final SpreadsheetColumn column : this.getHeader().getColumns()) {
			if (column.isIdentifiableBy(identifier)) {
				return column;
			}
		}
		return null;
	}

	/**
	 * Gets the header column with the matching index
	 * 
	 * @param index
	 * @return column with the given index
	 */
	public SpreadsheetColumn getColumn(final int index) {
		for (final SpreadsheetColumn column : this.getHeader().getColumns()) {
			if (column.getIndex() == index) {
				return column;
			}
		}
		return null;
	}

	/**
	 * Adds information about an existing header column to the worksheet.
	 * 
	 * @param index
	 * @param name
	 * @return SpreadsheetColumn
	 */
	public SpreadsheetColumn addColumn(final int index, final String name) {
		final SpreadsheetColumnMetadata metadata = new SpreadsheetColumnMetadata(Integer.toString(index), name);
		return this.addColumn(metadata);
	}

	/**
	 * Adds information about an existing header column to the worksheet. If the
	 * column described by the given metadata does not exist the column is created.
	 * 
	 * @param metadata
	 * @return SpreadsheetColumn
	 */
	public SpreadsheetColumn addColumn(final SpreadsheetColumnMetadata metadata) {
		final SpreadsheetColumn column = this.findOrCreateColumn(metadata);
		this.attachMetadataToColumn(column, metadata);
		return column;
	}

	private SpreadsheetColumn findOrCreateColumn(final SpreadsheetColumnMetadata metadata) {
		SpreadsheetColumn column = null;
		if (!StringUtil.isEmpty(metadata.getIndex())) {
			column = this.getColumn(Integer.parseInt(metadata.getIndex()));
		}
		if (column == null && !StringUtil.isEmpty(metadata.getName())) {
			column = this.getColumn(metadata.getName());
		}
		if (column == null) {
			if (!StringUtil.isEmpty(metadata.getIndex())) {
				column = this.createColumn(Integer.parseInt(metadata.getIndex()));
				this.header.addColumn(column);
			}
			else {
				final String message = "Unable to construct column from metadata '" + metadata + "'";
				throw new IllegalArgumentException(message);
			}
		}
		return column;
	}

	private void attachMetadataToColumn(final SpreadsheetColumn column, final SpreadsheetColumnMetadata metadata) {
		if (!StringUtil.isEmpty(metadata.getName())) {
			if (metadata.getName().contains(SpreadsheetConstants.HEADER_NAME_SPLIT_CHARS)) {
				column.setName(this.getValueBeforeDash(metadata.getName()));
			}
			else {
				column.setName(metadata.getName());
			}
		}

		if (!StringUtil.isEmpty(metadata.getAlias())) {
			column.setAlias(metadata.getAlias());
		}

		column.setDataType(SpreadsheetDataType.convert(metadata.getDataType()));

		if (!StringUtil.isEmpty(metadata.getMany())) {
			column.setMany(Boolean.parseBoolean(metadata.getMany()));
		}

		if (!StringUtil.isEmpty(metadata.getDelimiter())) {
			column.setDelimiter(metadata.getDelimiter());
		}
	}

	private String getValueBeforeDash(final String value) {
		final String[] splitValue = value.split(SpreadsheetConstants.HEADER_NAME_SPLIT_CHARS);
		if (splitValue.length != 0 && !StringUtil.isEmpty(splitValue[0])) {
			return splitValue[0].trim();
		}
		return null;
	}

	/**
	 * Creates a column with the specified column index
	 * 
	 * @param index
	 * @return column
	 */
	protected abstract SpreadsheetColumn createColumn(int index);

	/**
	 * Gets all rows contained by the worksheet
	 * 
	 * @return List of rows
	 */
	public abstract List<SpreadsheetRow> getRows();

	/**
	 * Writes a row to the worksheet. The Map links column identifiers with desired
	 * cell values. Cells that do not have a value provided will contain value
	 * returned by {@link #getDefaultEmptyCellValue()} or the default value for the
	 * column's data type if worksheet is data type strict.
	 * 
	 * @param parameters
	 * @return the newly written row
	 * @throws IllegalArgumentException if a column identifier could not be mapped
	 *                                  to a column in the worksheet
	 */
	public SpreadsheetRow addRow(final Map<String, Object> parameters) {
		final Map<SpreadsheetColumn, Object> rowValues = new HashMap<>();
		for (final Map.Entry<String, Object> entry : parameters.entrySet()) {
			final SpreadsheetColumn column = this.header.getColumn(entry.getKey());
			if (column != null) {
				rowValues.put(column, entry.getValue());
			}
			else {
				final String message = String.format("Column id '%s' is unknown", entry.getKey());
				throw new IllegalArgumentException(message);
			}
		}
		return this.addRowWithValuesInColumns(rowValues);
	}

	/**
	 * Writes a row to the worksheet. The Map links columns with desired cell
	 * values. Cells that do not have a value provided will contain value returned
	 * by {@link #getDefaultEmptyCellValue()} or the default value for the column's
	 * data type if worksheet is data type strict.
	 * 
	 * @param parameters
	 * @return the newly written row
	 */
	public SpreadsheetRow addRowWithValuesInColumns(final Map<SpreadsheetColumn, Object> parameters) {
		final Map<SpreadsheetColumn, String> values = this.getValuesForEmptyRow();
		for (final Map.Entry<SpreadsheetColumn, Object> entry : parameters.entrySet()) {
			Object value = entry.getValue();
			final boolean columnIsReferencing = this.model.getReferencesBySource(this, entry.getKey()).size() > 0;
			if (columnIsReferencing) {
				value = this.getValueToWriteToReferencingCell(entry.getKey(), value);
			}
			value = this.getValueToWriteToCell(entry.getKey(), value);
			values.put(entry.getKey(), String.valueOf(value));
		}

		return this.insertRow(values);
	}

	/**
	 * The purpose of this method is to return a map where each column of this
	 * worksheet is associated with its default value.
	 * 
	 * @return Map<SpreadsheetColumn, String>
	 */
	public Map<SpreadsheetColumn, String> getValuesForEmptyRow() {
		final Map<SpreadsheetColumn, String> row = new HashMap<>();
		for (final SpreadsheetColumn column : this.getHeader().getColumns()) {
			String value = this.getDefaultEmptyCellValue();
			if (this.isDataTypeStrict()) {
				value = SpreadsheetDataType.getDefaultDTValue(column.getDataType());
			}
			row.put(column, value);
		}
		return row;
	}

	/**
	 * This method returns the default value to be written to a cell when a new
	 * empty row is created in a worksheet that is not enforcing data type
	 * strictness.
	 * 
	 * @return String
	 */
	public abstract String getDefaultEmptyCellValue();

	/**
	 * This method returns all values to be written to the referencing column.
	 * 
	 * @param column
	 * @param value
	 * @return list of values to write
	 */
	private List<String> getValueToWriteToReferencingCell(final SpreadsheetColumn column, final Object value) {
		final List<SpreadsheetRow> rows = SpreadsheetUtils.extractAllRowsFromObject(value);
		if (rows == null || rows.isEmpty()) {
			final String message = "At least one row must be provided when writing to a referencing cell";
			throw new IllegalArgumentException(message);
		}

		final List<String> valuesToWrite = new ArrayList<>();
		for (final SpreadsheetRow row : rows) {
			final Set<SpreadsheetReference> references = this.model.getReferencesBySource(this, column);
			for (final SpreadsheetReference reference : references) {
				final boolean thisRowIsBeingReferenced = reference.getReferencedWorksheet() == row.getWorksheet();
				if (thisRowIsBeingReferenced) {
					valuesToWrite.addAll(row.getAllVisibleCellValuesAsIs(reference.getReferencedColumn()));
				}
			}
		}

		if (column.isNotMany() && valuesToWrite != null && !valuesToWrite.isEmpty()) {
			final String oneValueToWrite = valuesToWrite.get(0);
			valuesToWrite.clear();
			valuesToWrite.add(oneValueToWrite);
		}

		return valuesToWrite;
	}

	/**
	 * This method parses the given value and prepares it to be written to the given
	 * column.
	 * 
	 * @param column
	 * @param value
	 * @return the value to be written to the column
	 */
	private String getValueToWriteToCell(final SpreadsheetColumn column, final Object inputValue) {
		final StringBuilder valueToWrite = new StringBuilder();
		final String value = SpreadsheetUtils.convertObjectToString(column, inputValue);
		if (column.isMany() && this.isDataTypeStrict()) {
			for (final String splitValue : value.split(column.getDelimiter())) {
				final Object castValue = SpreadsheetDataType.castColumnValue(column.getDataType(), splitValue.trim());
				valueToWrite.append(castValue);
				valueToWrite.append(column.getDelimiter());
			}
			SpreadsheetUtils.removeLast(valueToWrite, column.getDelimiter());
		}
		else if (this.isDataTypeStrict()) {
			final Object castValue = SpreadsheetDataType.castColumnValue(column.getDataType(), value.trim());
			valueToWrite.append(castValue);
		}
		else {
			valueToWrite.append(value);
		}
		return valueToWrite.toString();
	}

	/**
	 * Inserts a new row in the worksheet with given values in the given columns.
	 * 
	 * @param values
	 * @return the newly created row
	 */
	protected abstract SpreadsheetRow insertRow(Map<SpreadsheetColumn, String> values);

	/**
	 * Deletes the given row from the worksheet. This method handles deletions of
	 * referencing rows, referenced rows and regular rows. It also takes care of
	 * cascading updates if specified to do so.
	 * 
	 * @param row
	 * @throws EolRuntimeException
	 */
	public void deleteRow(final SpreadsheetRow row) throws EolRuntimeException {
		final boolean rowBelongsToThisWorksheet = row.getWorksheet() == this;
		if (rowBelongsToThisWorksheet) {
			Collection<? extends SpreadsheetReference> worksheetTargetRefs = this.model.getReferencesByTarget(this);
			if (worksheetTargetRefs != null && !worksheetTargetRefs.isEmpty()) {
				final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(this.model);
				for (final SpreadsheetReference reference : worksheetTargetRefs) {
					if (reference.isCascadingUpdates()) {
						setter.editReferencedCell(row, reference.getReferencedColumn(), this.getDefaultEmptyCellValue());
					}
				}
			}
			this.removeRow(row);
		}
		else {
			final String message = "Row " + row + " does not belong to worksheet " + this;
			throw new EolRuntimeException(message);
		}
	}

	/**
	 * Deletes the given row from the actual worksheet.
	 * 
	 * @param row
	 * @throws EolRuntimeException
	 */
	public abstract void removeRow(SpreadsheetRow row);

	/**
	 * Finds all rows in the worksheet whose cell, corresponding to the given
	 * column, has the provided value. By 'has' it means that if the cell may
	 * contain multiple values then if one of those values match the search value
	 * then the row is returned. If the cell may not contain multiple values the
	 * cell's value must be an exact match to the search value.
	 * 
	 * @param column
	 * @param value
	 * @return List of rows whose cell contains value
	 */
	public List<SpreadsheetRow> findRows(final SpreadsheetColumn column, final String value) {
		this.checkThatWorksheetExists();

		final List<SpreadsheetRow> rows = new ArrayList<>();
		for (final SpreadsheetRow row : this.getRows()) {
			final List<String> cellValues = row.getAllVisibleCellValues(column);
			if (cellValues.contains(value.trim())) {
				rows.add(row);
			}
		}
		return rows;
	}

	/**
	 * This method checks if this worksheet exists in the spreadsheet.
	 * 
	 * @throws IllegalStateException if worksheet does not exist
	 */
	protected void checkThatWorksheetExists() throws IllegalStateException {
		if (this.getDoesNotExistInSpreadsheet()) {
			final String message = this.getNonexistentWorksheetMessage();
			throw new IllegalStateException(message);
		}
	}

	protected String getNonexistentWorksheetMessage() {
		return String.format("Worksheet '%s' does not exist in spreadsheet '%s'", this.getName(), this.model.getName());
	}

	@Override
	public String toString() {
		final StringBuilder stringObject = new StringBuilder("\n" + this.getName());
		stringObject.append(" [");
		if (!StringUtil.isEmpty(this.getAlias())) {
			stringObject.append("alias='" + this.getAlias() + "', ");
		}
		stringObject.append("existsInSpreadsheet='" + this.existsInSpreadsheet + "' ")
			.append("dataTypeStrict='" + this.dataTypeStrict + "' ");
		
		for (SpreadsheetColumn column : this.getHeader().getColumns()) {
			stringObject.append(column.toString());
		}
		stringObject.append("]\n");

		return stringObject.toString();
	}

}
