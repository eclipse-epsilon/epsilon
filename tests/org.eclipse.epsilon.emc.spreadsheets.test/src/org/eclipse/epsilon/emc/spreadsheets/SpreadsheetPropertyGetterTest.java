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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.*;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.junit.Test;

public class SpreadsheetPropertyGetterTest {

	@Test
	public void testColumnNotMany() throws EolRuntimeException {
		String columnName = "COLUMN";
		List<String> values = new ArrayList<>();
		values.add("VALUE");
		Set<SpreadsheetReference> sourceReferences = new HashSet<>();
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);

		when(row.getModel()).thenReturn(model);
		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getAllVisibleCellValues(column)).thenReturn(values);
		when(row.getReferencesBySource(column)).thenReturn(sourceReferences);
		when(column.isMany()).thenReturn(false);

		SpreadsheetPropertyGetter getter = new SpreadsheetPropertyGetter(model);
		Object response = getter.invoke(row, columnName, null);

		assertEquals("VALUE", response);
	}

	@Test
	public void testColumnNotManyAndCellIsBlank() throws EolRuntimeException {
		String columnName = "COLUMN";
		List<String> values = new ArrayList<>();
		Set<SpreadsheetReference> sourceReferences = new HashSet<>();
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);

		when(row.getModel()).thenReturn(model);
		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getAllVisibleCellValues(column)).thenReturn(values);
		when(row.getReferencesBySource(column)).thenReturn(sourceReferences);
		when(column.isMany()).thenReturn(false);

		SpreadsheetPropertyGetter getter = new SpreadsheetPropertyGetter(model);
		Object response = getter.invoke(row, columnName, null);

		assertEquals("", response);
	}

	@Test
	public void testColumnIsMany() throws EolRuntimeException {
		String columnName = "COLUMN";
		List<String> values = new ArrayList<>();
		values.add("VALUE1");
		values.add("VALUE2");
		Set<SpreadsheetReference> sourceReferences = new HashSet<>();
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);

		when(row.getModel()).thenReturn(model);
		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getAllVisibleCellValues(column)).thenReturn(values);
		when(row.getReferencesBySource(column)).thenReturn(sourceReferences);
		when(column.isMany()).thenReturn(true);

		SpreadsheetPropertyGetter getter = new SpreadsheetPropertyGetter(model);
		Object response = getter.invoke(row, columnName, null);

		assertTrue(((Collection<?>) response).containsAll(values));
	}

	@Test
	public void testColumnIsManyAndCellIsBlank() throws EolRuntimeException {
		String columnName = "COLUMN";
		List<String> values = new ArrayList<>();
		Set<SpreadsheetReference> sourceReferences = new HashSet<>();
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);

		when(row.getModel()).thenReturn(model);
		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getAllVisibleCellValues(column)).thenReturn(values);
		when(row.getReferencesBySource(column)).thenReturn(sourceReferences);
		when(column.isMany()).thenReturn(true);

		SpreadsheetPropertyGetter getter = new SpreadsheetPropertyGetter(model);
		Object response = getter.invoke(row, columnName, null);

		assertEquals(0, ((Collection<?>) response).size());
	}

	@Test
	public void testCollection() throws EolRuntimeException {
		SpreadsheetRow row1 = mock(SpreadsheetRow.class);
		SpreadsheetRow row2 = mock(SpreadsheetRow.class);
		List<SpreadsheetRow> rows = new ArrayList<>();
		rows.add(row1);
		rows.add(row2);

		String columnName = "COLUMN";
		List<String> values1 = new ArrayList<>();
		values1.add("VALUE1");
		List<String> values2 = new ArrayList<>();
		values2.add("VALUE2");

		Set<SpreadsheetReference> sourceReferences = new HashSet<>();
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);

		when(row1.getModel()).thenReturn(model);
		when(row1.getColumn(columnName)).thenReturn(column);
		when(row1.getAllVisibleCellValues(column)).thenReturn(values1);
		when(row1.getReferencesBySource(column)).thenReturn(sourceReferences);
		when(column.isMany()).thenReturn(false);

		when(row2.getModel()).thenReturn(model);
		when(row2.getColumn(columnName)).thenReturn(column);
		when(row2.getAllVisibleCellValues(column)).thenReturn(values2);
		when(row2.getReferencesBySource(column)).thenReturn(sourceReferences);
		when(column.isMany()).thenReturn(false);

		SpreadsheetPropertyGetter getter = new SpreadsheetPropertyGetter(model);
		Object response = getter.invoke(rows, columnName, null);

		@SuppressWarnings("unchecked")
		Iterator<String> it = ((List<String>) response).iterator();
		String value1 = it.next();
		String value2 = it.next();
		assertTrue(
			"VALUE1".equals(value1) && "VALUE2".equals(value2) ||
			"VALUE2".equals(value1) && "VALUE1".equals(value2)
		);
	}
}
