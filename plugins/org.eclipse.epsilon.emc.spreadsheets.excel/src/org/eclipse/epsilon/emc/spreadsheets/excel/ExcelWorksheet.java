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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.common.util.Wrapper;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumn;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;

public class ExcelWorksheet extends SpreadsheetWorksheet {
	
	protected ExcelModel model;
	protected Sheet sheet;

	public ExcelWorksheet(final ExcelModel model, final Sheet sheet, final boolean existsInSpreadsheet) {
		super(model, sheet.getSheetName(), existsInSpreadsheet);

		this.model = model;
		this.sheet = sheet;

		if (this.existsInSpreadsheet) {
			this.loadHeader();
		}
	}

	@Override
	protected void createInSpreadsheet() {
		this.existsInSpreadsheet = true;
		this.writeHeaderRow();
	}

	private void writeHeaderRow() {
		
		final Row row = this.sheet.createRow(this.getHeaderRowIndex());
		final ExcelRow headerRow = new ExcelRow(this, row);

		for (final SpreadsheetColumn column : this.getHeader().getColumns()) {
			if (!StringUtil.isEmpty(column.getName())) {
				row.createCell(column.getIndex());
				headerRow.overwriteCellValue(column, column.getName());
			}
		}
	}

	@Override
	protected void loadHeader() {
		super.checkThatWorksheetExists();

		if (this.sheet.getPhysicalNumberOfRows() > 0) {
			final Row row = this.sheet.getRow(0);
			final ExcelRow excelRow = new ExcelRow(this, row);
			for (Iterator<Cell> it = row.cellIterator(); it.hasNext();) {
				final Cell headerCell = it.next();
				final ExcelColumn excelColumn = new ExcelColumn(this, headerCell.getColumnIndex());
				final String columnName = excelRow.getVisibleCellValue(excelColumn);
				super.addColumn(headerCell.getColumnIndex(), columnName);
			}
		}
	}

	@Override
	protected SpreadsheetColumn createColumn(final int index) {
		return new ExcelColumn(this, index);
	}

	@Override
	public List<SpreadsheetRow> getRows() {
		final List<SpreadsheetRow> rows = new ArrayList<>();
		final int numOfRows = this.sheet.getPhysicalNumberOfRows();
		for (int i = this.getFirstRowIndex(); i <= numOfRows; i++) {
			final Row row = this.sheet.getRow(i);
			if (row != null) {
				
				// Only consider rows with at least one non-blank cell
				final Wrapper<Boolean> emptyRow = new Wrapper<Boolean>(true);
				row.cellIterator().forEachRemaining(cell -> {
					if (!(cell.getCellType() == CellType.BLANK)) { 
						emptyRow.setValue(false); 
					}
				});
				
				if (!emptyRow.getValue()) rows.add(new ExcelRow(this, row));
			}
		}
		return rows;
	}

	@Override
	public SpreadsheetRow insertRow(final Map<SpreadsheetColumn, String> values) {
		
		final int newRowIndex = this.sheet.getPhysicalNumberOfRows();
		final Row row = sheet.createRow(newRowIndex);
		for (final Map.Entry<SpreadsheetColumn, String> entry : values.entrySet()) {
			final Cell cell = row.createCell(entry.getKey().getIndex());
			cell.setCellValue(entry.getValue());
		}

		final ExcelRow excelRow = new ExcelRow(this, row);
		return excelRow;
	}

	@Override
	public void removeRow(final SpreadsheetRow row) {
		if (row != null) {
			final ExcelRow excelRow = (ExcelRow) row;
			final int rowIndex = excelRow.row.getRowNum();
			final int lastRowNum = this.sheet.getLastRowNum();

			this.sheet.removeRow(excelRow.row);

			if (rowIndex >= this.getFirstRowIndex() && rowIndex < lastRowNum) {
				sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
			}
		}
	}

	@Override
	public String getDefaultEmptyCellValue() {
		return "";
	}

	public int getHeaderRowIndex() {
		return 0;
	}

	public int getFirstRowIndex() {
		return 1;
	}
}
