/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumn;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;

public class ExcelRow extends SpreadsheetRow {
	
	protected ExcelWorksheet worksheet;
	protected Row row;

	public ExcelRow(final ExcelWorksheet worksheet, final Row row) {
		super(worksheet);
		this.worksheet = worksheet;
		this.row = row;
	}

	@Override
	public String getVisibleCellValue(final SpreadsheetColumn column) {
		super.validateColumn(column);

		String visibleCellValue = "";
		final Cell cell = this.row.getCell(column.getIndex());
		if (cell != null) {
			final FormulaEvaluator evaluator = worksheet.model.workbook.getCreationHelper().createFormulaEvaluator();
			final CellValue cellValue = evaluator.evaluate(cell);
			if (cellValue != null) {
				switch (cellValue.getCellType()) {
					case NUMERIC:
						double numericValue = cellValue.getNumberValue();
						switch (column.getDataType()) {
						case INTEGER:
							visibleCellValue += Math.round(numericValue);
							break;
						case FLOAT:
							visibleCellValue += (float) numericValue;
							break;
						case STRING:
							visibleCellValue += new DataFormatter().formatCellValue(cell, evaluator);
							break;
						case DOUBLE:
							visibleCellValue += numericValue;
							break;
						case BOOLEAN:
							visibleCellValue += Boolean.valueOf(numericValue != 0);
							break;
						}
						break;
					case BOOLEAN:
						visibleCellValue += cellValue.getBooleanValue();
						break;
					case STRING:
					default:
						visibleCellValue = cellValue.getStringValue();
				}
			}
			else {
				visibleCellValue += cell.getStringCellValue();
			}
		}
		return visibleCellValue;
	}

	@Override
	public void overwriteCellValue(final SpreadsheetColumn column, final String value) {
		super.validateColumn(column);
		final Cell cell = this.row.getCell(column.getIndex());
		
		if (this.row.getRowNum() == 0) {
			cell.setCellValue(value);
		}
		else {
			if (value != null && value.startsWith("=")) {
				cell.setCellFormula(value.substring(1));
			}
			else {
				if (column.isMany()) {
					cell.setCellValue(value);
				}
				else {
					switch (column.getDataType()) {
					case BOOLEAN: cell.setCellValue(Boolean.parseBoolean(value)); break;
					case INTEGER: cell.setCellValue(Integer.parseInt(value)); break;
					case FLOAT: cell.setCellValue(Float.parseFloat(value)); break;
					case DOUBLE: cell.setCellValue(Float.parseFloat(value)); break;
					default: cell.setCellValue(value);
					}
				}
			}
		}
	}
	
	public Row getRow() {
		return row;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row.getRowNum();
		result = prime * result + ((worksheet == null) ? 0 : worksheet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExcelRow other = (ExcelRow) obj;
		if (row.getRowNum() != other.row.getRowNum()) {
			return false;
		}
		if (worksheet == null) {
			if (other.worksheet != null) {
				return false;
			}
		}
		else if (!worksheet.equals(other.worksheet)) {
			return false;
		}
		return true;
	}

}
