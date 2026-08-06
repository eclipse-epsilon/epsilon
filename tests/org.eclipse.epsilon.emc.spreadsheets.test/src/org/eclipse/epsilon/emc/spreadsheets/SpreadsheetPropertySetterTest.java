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

public class SpreadsheetPropertySetterTest {

	@Test
	public void testEditNull() throws EolRuntimeException {
		SpreadsheetRow row = null;
		final String columnName = "COLUMN";
		final String value = "VALUE";
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);

		try {
			setter.invoke(row, columnName, value, null);
			fail();
		}
		catch (Exception e) {
			// passed
		}
	}

	@Test
	public void testSettingToNull() throws EolRuntimeException {
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		final String columnName = "COLUMN";
		final String value = null;
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);

		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getReferencesBySource(column)).thenReturn(new HashSet<SpreadsheetReference>());
		when(row.getReferencesByTarget(column)).thenReturn(new HashSet<SpreadsheetReference>());
		when(column.isMany()).thenReturn(true);
		when(column.getDelimiter()).thenReturn(",");
		row.writeVisibleCellValues(column, Arrays.asList("null"));

		SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, columnName, value, null);
	}

	@Test
	public void testColumnNull() throws EolRuntimeException {
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		final String columnName = null;
		final String value = null;
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		
		when(row.getColumn(columnName)).thenReturn(null);

		SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);

		try {
			setter.invoke(row, columnName, value, null);
			fail();
		}
		catch (EolRuntimeException e) {
			// passed
		}
	}

	@Test
	public void testColumnNotMany() throws EolRuntimeException {
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		final String columnName = "COLUMN";
		final String value = "VALUE";
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);

		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getReferencesBySource(column)).thenReturn(new HashSet<SpreadsheetReference>());
		when(row.getReferencesByTarget(column)).thenReturn(new HashSet<SpreadsheetReference>());
		when(column.isMany()).thenReturn(false);
		row.writeVisibleCellValues(column, Arrays.asList(value));

		SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, columnName, value, null);
	}

	@Test
	public void testColumnMany() throws EolRuntimeException {
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		final String columnName = "COLUMN";
		final String value = "VALUE";
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);

		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getReferencesBySource(column)).thenReturn(new HashSet<SpreadsheetReference>());
		when(row.getReferencesByTarget(column)).thenReturn(new HashSet<SpreadsheetReference>());
		when(column.isMany()).thenReturn(true);
		when(column.getDelimiter()).thenReturn(",");
		row.writeVisibleCellValues(column, Arrays.asList(value));

		SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, columnName, value, null);
	}

	@Test
	public void testEditReferencingCellWithoutRow() throws EolRuntimeException {
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		final String columnName = "COLUMN";
		final String value = "VALUE";
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);
		Set<SpreadsheetReference> sourceReferences = new HashSet<>();
		SpreadsheetReference reference = mock(SpreadsheetReference.class);
		sourceReferences.add(reference);

		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getReferencesBySource(column)).thenReturn(sourceReferences);
		when(row.getReferencesByTarget(column)).thenReturn(new HashSet<SpreadsheetReference>());

		SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);

		try {
			setter.invoke(row, columnName, value, null);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testEditReferencingCellWithRow() throws EolRuntimeException {
		SpreadsheetRow row = mock(SpreadsheetRow.class);
		final String columnName = "COLUMN";
		SpreadsheetRow valueRow = mock(SpreadsheetRow.class);
		SpreadsheetModel model = mock(SpreadsheetModel.class);
		SpreadsheetColumn column = mock(SpreadsheetColumn.class);
		Set<SpreadsheetReference> sourceReferences = new HashSet<>();
		SpreadsheetReference reference = mock(SpreadsheetReference.class);
		sourceReferences.add(reference);
		SpreadsheetWorksheet targetWorksheet = mock(SpreadsheetWorksheet.class);
		final List<String> newValues = new ArrayList<>();
		newValues.add("V1");
		SpreadsheetColumn targetColumn = mock(SpreadsheetColumn.class);
		final List<String> currentValues = new ArrayList<>();
		currentValues.add("V1");
		currentValues.add("V2");
		currentValues.add("V3");
		currentValues.add("V4");

		when(row.getColumn(columnName)).thenReturn(column);
		when(row.getReferencesBySource(column)).thenReturn(sourceReferences);
		when(row.getReferencesByTarget(column)).thenReturn(new HashSet<SpreadsheetReference>());
		when(reference.getReferencedWorksheet()).thenReturn(targetWorksheet);
		when(valueRow.getWorksheet()).thenReturn(targetWorksheet);
		when(reference.getReferencedColumn()).thenReturn(targetColumn);
		when(valueRow.getAllVisibleCellValues(targetColumn)).thenReturn(currentValues);
		when(column.isNotMany()).thenReturn(true);
		when(row.getReferencesByTarget(column)).thenReturn(new HashSet<SpreadsheetReference>());
		row.writeVisibleCellValues(column, newValues);

		SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, columnName, valueRow, null);
		
		verify(column, times(1)).isNotMany();
		verify(row, times(2)).getReferencesBySource(column);
	}
}
