/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;

public class CsvContentTransformer implements ViewContentTransformer {
	
	@Override
	public boolean canTransform(ViewContent content) {
		return "csv".equalsIgnoreCase(content.getFormat());
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) throws Exception {

		CSVFormat format = CSVFormat.RFC4180.withFirstRecordAsHeader();
		CSVParser parser = format.parse(new StringReader(content.getText()));

		List<CSVRecord> records = parser.getRecords();
		List<String> columnTypes = getColumnTypes(records);

		StringBuilder result = new StringBuilder();
		result.append(
				"<table class=\"table striped table-border row-hover\" data-horizontal-scroll=\"true\" data-role=\"table\" data-pagination=\"true\">")
				.append("<thead>");

		for (int i = 0; i < parser.getHeaderNames().size(); i++) {
			result.append("<th data-sortable=\"true\" data-sort-dir=\"asc\"")
					.append(" data-format=\"" + getColumnType(columnTypes, i) + "\">")
					.append(parser.getHeaderNames().get(i))
					.append("</th>");
		}
		result.append("</thead>").append("<tbody>");

		for (CSVRecord record : records) {
			result.append("<tr>");
			for (String cell : record) {
				result.append("<td>").append(cell).append("</td>");
			}
			result.append("</tr>");
		}
		result.append("</tbody>").append("</table>");
		
		return new ViewContent("html", pictoView.getViewRenderer().getHtml(result.toString()), content);
	}
	
	protected String getColumnType(List<String> columnTypes, int columnIndex) {
		if (columnTypes != null) {
			return columnTypes.get(columnIndex);
		}
		return "string";
	}

	protected String getCellType(String cell) {
		try {
			Double.parseDouble(cell);
			return "number";
		}
		catch (NumberFormatException n) {
		}
		return "string";
	}

	public List<String> getColumnTypes(List<CSVRecord> records) {
		if (records.size() == 0) {
			return null;
		}
		List<String> columnTypes = new ArrayList<String>();
		// check type over first rows
		int rowsToCheck = 5;
		for (int row = 0; row < rowsToCheck && row < records.size(); row++) {
			CSVRecord record = records.get(row);
			int column = 0;
			for (String cell : record) {
				String cellType = getCellType(cell);
				if (row > 0) {
					if (!cellType.equals(columnTypes.get(column))) {
						// default to string
						columnTypes.set(column, "string");
					}
				}
				else {
					columnTypes.add(cellType);
				}
				column++;
			}
		}
		return columnTypes;
	}

	public boolean isNumber(String s) {
		if (s == null) {
			return false;
		}
		try {
			Double.parseDouble(s);
		}
		catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	@Override
	public String getLabel(ViewContent content) {
		return "CSV";
	}
}
