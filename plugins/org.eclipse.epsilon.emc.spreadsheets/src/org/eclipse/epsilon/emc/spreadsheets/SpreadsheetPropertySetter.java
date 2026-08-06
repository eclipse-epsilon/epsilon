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

import java.util.*;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertySetter;

/**
 * This class allows setting values of spreadsheet row cells.
 * 
 * @author Martins Francis
 */
public class SpreadsheetPropertySetter extends JavaPropertySetter {
	protected final SpreadsheetModel model;

	public SpreadsheetPropertySetter(final SpreadsheetModel model) {
		this.model = model;
	}

	@Override
	public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {
		if (object instanceof Collection<?>) {
			this.edit((Collection<?>) object, value, property, context);
		}
		else if (object instanceof SpreadsheetRow) {
			this.edit((SpreadsheetRow) object, value, property, context);
		}
		else {
			super.invoke(object, property, value, context);
		}
	}

	/**
	 * Convenience method for editing a row and column.
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws EolRuntimeException
	 */
	public void invoke(SpreadsheetRow row, SpreadsheetColumn column, Object value, ModuleElement ast, IEolContext context) throws EolRuntimeException {
		invoke(row, column.getIdentifier(), value, context);
	}

	public void edit(Collection<?> rows, Object value, String property, IEolContext context) throws EolRuntimeException {
		for (final Object row : rows) {
			final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(this.model);
			setter.invoke(row, property, value, context);
		}
	}

	public void edit(SpreadsheetRow row, Object value, String property, IEolContext context) throws EolRuntimeException {
		final SpreadsheetColumn column = row.getColumn(property);
		if (column == null) {
			throw new EolIllegalPropertyException(row, property, context);
		}

		Collection<?> rowSourceRefs = row.getReferencesBySource(column);
		Collection<?> rowTargetRefs = row.getReferencesByTarget(column);
		
		if (rowSourceRefs != null && !rowSourceRefs.isEmpty()) {
			this.editReferencingCell(row, column, value);
		}
		else if (rowTargetRefs != null && !rowTargetRefs.isEmpty()) {
			this.editReferencedCell(row, column, value);
		}
		else {
			this.editPlainCell(row, column, value);
		}
	}

	public void editReferencingCell(final SpreadsheetRow row, final SpreadsheetColumn column, final Object value) {
		final List<SpreadsheetRow> referencedRows = SpreadsheetUtils.extractAllRowsFromObject(value);
		if (referencedRows.isEmpty()) {
			final String message = "Referencing cell can be edited by passing one row or collection of rows";
			throw new IllegalArgumentException(message);
		}

		final Set<SpreadsheetReference> references = row.getReferencesBySource(column);
		final List<String> valuesToWrite = this.getReferencedValues(referencedRows, references);

		this.editReferencingCell(row, column, valuesToWrite);
	}

	private List<String> getReferencedValues(final List<SpreadsheetRow> rows,
		final Set<SpreadsheetReference> references) {
		final List<String> values = new ArrayList<>();
		for (final SpreadsheetRow row : rows) {
			boolean thisRowIsReferenced = false;
			for (final SpreadsheetReference reference : references) {
				thisRowIsReferenced = reference.getReferencedWorksheet() == row.getWorksheet();
				if (thisRowIsReferenced) {
					values.addAll(row.getAllVisibleCellValues(reference.getReferencedColumn()));
				}
			}
			if (!thisRowIsReferenced) {
				throw new IllegalArgumentException("Row is not referenced");
			}
		}
		return values;
	}

	private void editReferencingCell(final SpreadsheetRow row, final SpreadsheetColumn column,
		final List<String> values) {
		final boolean moreThanOneValue = column.isNotMany() && values.size() > 1;
		if (moreThanOneValue) {
			final String firstValue = values.iterator().next();
			values.clear();
			values.add(firstValue);
		}

		Collection<?> colTargetRefs = row.getReferencesByTarget(column);
		
		if (colTargetRefs != null && !colTargetRefs.isEmpty()) {
			this.editReferencedCell(row, column, values);
		}
		else {
			row.writeVisibleCellValues(column, values);
		}
	}

	public void editReferencedCell(final SpreadsheetRow row, final SpreadsheetColumn column, final Object value) {
		final List<String> currentValues = row.getAllVisibleCellValues(column);
		this.editPlainCell(row, column, value);
		final List<String> newValues = row.getAllVisibleCellValues(column);
		currentValues.removeAll(newValues);
		final List<String> removedValues = currentValues;
		final Set<SpreadsheetReference> targetReferences = row.getReferencesByTarget(column);
		for (final SpreadsheetReference reference : targetReferences) {
			if (reference.isCascadingUpdates()) {
				this.cascadeChangesToReference(reference, row, column, removedValues, newValues);
			}
		}
	}

	private void cascadeChangesToReference(final SpreadsheetReference reference, final SpreadsheetRow row,
		final SpreadsheetColumn column, final List<String> removedValues, final List<String> newCellValues) {
		for (final String removedValue : removedValues) {
			final List<SpreadsheetRow> referencedRowsWithValue = row.getWorksheet().findRows(column, removedValue);
			if (referencedRowsWithValue != null && !referencedRowsWithValue.isEmpty()) {
				continue; // another referenced row has the value thus no need to cascade it
			}

			final SpreadsheetWorksheet referencingWorksheet = reference.getReferencingWorksheet();
			final SpreadsheetColumn referencingColumn = reference.getReferencingColumn();
			final List<SpreadsheetRow> referencingRows = referencingWorksheet.findRows(referencingColumn, removedValue);
			for (final SpreadsheetRow referencingRow : referencingRows) {
				this.cascadeChangesToReferencingRow(referencingRow, reference, removedValue, newCellValues);
			}
		}
	}

	private void cascadeChangesToReferencingRow(final SpreadsheetRow referencingRow,
		final SpreadsheetReference reference, final String removedValue, final List<String> newValues) {
		final SpreadsheetColumn referencingColumn = reference.getReferencingColumn();
		final List<String> cellValues = referencingRow.getAllVisibleCellValues(referencingColumn);
		final Set<String> cellValueSet = new LinkedHashSet<>(cellValues);
		cellValueSet.remove(removedValue);

		// Only when reference column relationship is many to not many we can be sure
		// that the new value replaces old
		// and thus should be cascaded to referencing rows
		final boolean knowWhichValueIsReplaced = reference.getReferencedColumn().isNotMany();
		if (knowWhichValueIsReplaced) {
			cellValueSet.addAll(newValues);
		}
		this.editReferencingCell(referencingRow, referencingColumn, new ArrayList<>(cellValueSet));
	}

	public void editPlainCell(final SpreadsheetRow row, final SpreadsheetColumn column, final Object newCellValues) {
		List<String> valuesToWrite = new ArrayList<>();
		if (column.isMany()) {
			if (newCellValues instanceof Collection<?>) {
				valuesToWrite.addAll(SpreadsheetUtils.convertObjectToList(newCellValues));
			}
			else {
				valuesToWrite.addAll(Arrays.asList(String.valueOf(newCellValues).split(column.getDelimiter())));
			}
		}
		else {
			valuesToWrite.add(SpreadsheetUtils.convertObjectToString(column, newCellValues));
		}
		row.writeVisibleCellValues(column, valuesToWrite);
	}

}
