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

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class represents a worksheet header and consists of a number of ordered
 * columns. The ordering of columns is enforced by
 * {@link SpreadsheetColumnComparator}.
 * 
 * @author Martins Francis
 */
public class SpreadsheetWorksheetHeader {
	private SpreadsheetWorksheet worksheet;
	private SortedSet<SpreadsheetColumn> header;

	public SpreadsheetWorksheetHeader(final SpreadsheetWorksheet worksheet) {
		this.worksheet = worksheet;
		this.header = new TreeSet<>(new SpreadsheetColumnComparator());
	}

	/**
	 * This method returns the worksheet this header is for.
	 * 
	 * @return worksheet
	 */
	public SpreadsheetWorksheet getWorksheet() {
		return worksheet;
	}

	/**
	 * Adds the given column to the header.
	 * 
	 * @param column
	 */
	public void addColumn(final SpreadsheetColumn column) {
		if (column.getWorksheet() == this.worksheet) {
			this.header.add(column);
		}
	}

	/**
	 * This method returns the first header column identifiable by the given
	 * identifier. If there is no such column then null is returned instead.
	 * 
	 * @param identifier
	 * @return header column or null
	 */
	public SpreadsheetColumn getColumn(final String identifier) {
		final Iterator<SpreadsheetColumn> it = this.header.iterator();
		while (it.hasNext()) {
			final SpreadsheetColumn column = it.next();
			if (column.isIdentifiableBy(identifier)) {
				return column;
			}
		}
		return null;
	}

	/**
	 * This method provides the columns that make up the header of the worksheet.
	 * 
	 * @return header columns
	 */
	public SortedSet<SpreadsheetColumn> getColumns() {
		return this.header;
	}

	/**
	 * This method returns true if the given columns is part of the worksheet's
	 * header.
	 * 
	 * @param column
	 * @return true if column is part of the header, false otherwise
	 */
	public boolean contains(final SpreadsheetColumn column) {
		return this.header.contains(column);
	}

	@Override
	public String toString() {
		return "SpreadsheetWorksheetHeader [worksheet=" + worksheet.getName() + ", header=" + header + "]";
	}

}
