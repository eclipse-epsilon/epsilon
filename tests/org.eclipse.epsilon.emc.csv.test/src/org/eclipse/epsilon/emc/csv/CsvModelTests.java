/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CsvModelTests.LoadModelTests.class,
				CsvModelTests.ModelAccessTests.class,
				CsvModelTests.ElementIdTests.class})
public class CsvModelTests {
	
	private static final String NEWLINE = System.lineSeparator();
	
	public static class LoadModelTests {
		
		@Test
		public void simpleCSV() throws Exception {
			final String csv = "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "212-06-7778,Rabbi,Varran,rvarrand@jugem.jp,Male,GIS Technical Architect,3551249058791476,Suspendisse potenti.,Horror";
			CsvModel model = new CsvModel();
			char fieldSeparator = ',';
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(false);
			model.setVarargsHeaders(false);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			Collection<Map<String, Object>> allRows = model.allContentsFromModel();
			assertEquals(2, allRows.size());
			for (Map<String, Object> row : allRows) {
				assertTrue(row.containsKey("field"));
			}
			Object[] allRowsArray = allRows.toArray();
			String[] csvData = csv.split(NEWLINE);
			for (int i=0;i < allRowsArray.length; i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> row = (Map<String, Object>) allRowsArray[i];
				@SuppressWarnings("unchecked")
				List<String> csvRowValues = (List<String>) row.get("field");
				Collection<String> rowData = Arrays.asList(csvData[i].split(String.valueOf(fieldSeparator)));
				assertTrue(String.format("Row data matches csv values, row = %s.", i), rowData.containsAll(csvRowValues));
			}
		}
		
		@Test
		public void simpleCSVOtherSeparator() throws Exception {
			final String csv = "604-78-8459;Ricoriki;Dwyr;rdwyr0@parallels.com;Male;VP Quality Control;;Duis at velit eu est congue elementum.;Horror" + NEWLINE +
							   "212-06-7778;Rabbi;Varran;rvarrand@jugem.jp;Male;GIS Technical Architect;3551249058791476;Suspendisse potenti.;Horror";
			CsvModel model = new CsvModel();
			char fieldSeparator = ',';
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(false);
			model.setVarargsHeaders(false);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			Collection<Map<String, Object>> allRows = model.allContentsFromModel();
			assertEquals(2, allRows.size());
			for (Map<String, Object> row : allRows) {
				assertTrue(row.containsKey("field"));
			}
			Object[] allRowsArray = allRows.toArray();
			String[] csvData = csv.split(NEWLINE);
			for (int i=0;i < allRowsArray.length; i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> row = (Map<String, Object>) allRowsArray[i];
				@SuppressWarnings("unchecked")
				List<String> csvRowValues = (List<String>) row.get("field");
				Collection<String> rowData = Arrays.asList(csvData[i].split(String.valueOf(fieldSeparator)));
				assertTrue(String.format("Row data matches csv values, row = %s.", i), rowData.containsAll(csvRowValues));
			}
		}
		
		@Test
		public void simpleCSVWithHeaders() throws Exception {
			final String[] headers = new String[]{"id","first_name","last_name","email","gender","job","credit_card","quote","movies"}; 
			final char fieldSeparator = ',';
			final String csv = "id,first_name,last_name,email,gender,job,credit_card,quote,movies" + NEWLINE + 
							   "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "212-06-7778,Rabbi,Varran,rvarrand@jugem.jp,Male,GIS Technical Architect,3551249058791476,Suspendisse potenti.,Horror" + NEWLINE +
							   "318-48-3006,Constantino,Eyckelbeck,ceyckelbeckf@histats.com,Male,Recruiter,564182300132483644,In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt.,War";
			CsvModel model = new CsvModel();
			model.setKnownHeaders(true);
			model.setVarargsHeaders(false);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			Collection<Map<String, Object>> allRows = model.allContentsFromModel();
			assertEquals("All rows have ben read", 3, allRows.size());
			for (Map<String, Object> row : allRows) {
				assertTrue(row.containsKey("id"));
				assertTrue(row.containsKey("first_name"));
				assertTrue(row.containsKey("last_name"));
				assertTrue(row.containsKey("email"));
				assertTrue(row.containsKey("gender"));
				assertTrue(row.containsKey("job"));
				assertTrue(row.containsKey("credit_card"));
				assertTrue(row.containsKey("quote"));
				assertTrue(row.containsKey("movies"));
			}
			Object[] allRowsArray = allRows.toArray();
			String[] csvData = csv.split(NEWLINE);
			for (int i=0;i < allRowsArray.length; i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> row = (Map<String, Object>) allRowsArray[i];
				String[] rowData = csvData[i+1].split(String.valueOf(fieldSeparator));
				for (int j=0; j<headers.length; j++) {
					assertEquals(String.format("Row data matches csv field %s, row = %s.", headers[j], i),
						rowData[j], row.get(headers[j])
					);
				}
			}
		}
		
		@Test
		public void varargsCSVWithHeaders() throws Exception {
			final String[] headers = new String[]{"id","first_name","last_name","email","gender","job","credit_card","quote","movies"}; 
			char fieldSeparator = ',';
			final String csv = "id,first_name,last_name,email,gender,job,credit_card,quote,movies" + NEWLINE + 
							   "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "272-41-1349,Norry,Halpin,nhalpin1@slashdot.org,Female,Legal Assistant,,Aenean sit amet justo. Morbi ut odio.,Drama,Film-Noir,Thriller" + NEWLINE +
							   "850-05-5333,Flossy,Mobberley,fmobberley7@msn.com,Female,Chief Design Engineer,3558038696922012,Nulla tempus.,Comedy,Romance";
			CsvModel model = new CsvModel();
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(true);
			model.setVarargsHeaders(true);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			Collection<Map<String, Object>> allRows = model.allContentsFromModel();
			assertEquals("All rows have ben read", 3, allRows.size());
			for (Map<String, Object> row : allRows) {
				assertTrue(row.containsKey("id"));
				assertTrue(row.containsKey("first_name"));
				assertTrue(row.containsKey("last_name"));
				assertTrue(row.containsKey("email"));
				assertTrue(row.containsKey("gender"));
				assertTrue(row.containsKey("job"));
				assertTrue(row.containsKey("credit_card"));
				assertTrue(row.containsKey("quote"));
				assertTrue(row.containsKey("movies"));
			}
			Object[] allRowsArray = allRows.toArray();
			String[] csvData = csv.split(NEWLINE);
			for (int i=0;i < allRowsArray.length; i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> row = (Map<String, Object>) allRowsArray[i];
				String[] rowData = csvData[i+1].split(String.valueOf(fieldSeparator));
				for (int j=0; j<headers.length; j++) {
					//Varargs
					if (j == headers.length-1) {
						List<String> varargRowData = new ArrayList<>();
						for(int k=0; k<rowData.length-headers.length+1;k++) {
							varargRowData.add(rowData[j+k]);
						}
						assertEquals(String.format("Row data matches csv field %s, row = %s.", headers[j], i),
							varargRowData, row.get(headers[j])
						);
					}
					else {
						assertEquals(String.format("Row data matches csv field %s, row = %s.", headers[j], i),
							rowData[j], row.get(headers[j])
						);
					}
				}
			}
		}
		
	}
	
	public static class ModelAccessTests {
		
		final String[] headers = new String[]{"id","first_name","last_name","email","gender","job","credit_card","quote","movies"}; 
		
		@Rule
	    public ExpectedException thrown = ExpectedException.none();
		
		private CsvModel model;

		@Before
		public void setup() throws Exception {
			char fieldSeparator = ',';
			final String csv = "id,first_name,last_name,email,gender,job,credit_card,quote,movies" + NEWLINE + 
							   "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "272-41-1349,Norry,Halpin,nhalpin1@slashdot.org,Female,Legal Assistant,,Aenean sit amet justo. Morbi ut odio.,Drama,Film-Noir,Thriller" + NEWLINE +
							   "850-05-5333,Flossy,Mobberley,fmobberley7@msn.com,Female,Chief Design Engineer,3558038696922012,Nulla tempus.,Comedy,Romance";
			model = new CsvModel();
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(true);
			model.setVarargsHeaders(true);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
		}
		
		@After
		public void teardown() {
			model.dispose();
		}
		
		@Test
		public void testIsModelElement() {
			Random rand = new Random();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			int rowIndex = rand.nextInt(rows.size());
			Map<String, Object> row = rows.stream().skip(rowIndex).findFirst().get();
			assertTrue(model.isModelElement(row));
			Map<String, Object> row2 = new LinkedHashMap<>();
			assertFalse(model.isModelElement(row2));
			row2 = new HashMap<>();
			row2.putAll(row);
			assertFalse(model.isModelElement(row2));
		}

		@Test
		public void testGetTypeOf() {
			Random rand = new Random();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			int rowIndex = rand.nextInt(rows.size());
			Map<String, Object> row = rows.stream().skip(rowIndex).findFirst().get();
			assertEquals(LinkedHashMap.class, model.getTypeOf(row));
			Map<String, Object> row2 = new LinkedHashMap<>();
			thrown.expect(IllegalArgumentException.class);
	        thrown.expectMessage("Not a valid CSV model instance");
			model.getTypeOf(row2);
			row2 = new HashMap<>();
			row2.putAll(row);
			model.getTypeOf(row2);
		}

		@Test
		public void testDeleteElementInModel() throws Exception {
			Random rand = new Random();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			int size = rows.size();
			int rowIndex = rand.nextInt(rows.size());
			Map<String, Object> row = rows.stream().skip(rowIndex).findFirst().get();
			boolean result = false;
			result = model.deleteElementInModel(row);
			assertTrue(result);
			rows = model.allContentsFromModel();
			assertTrue("Size has decreased because element was removed.", rows.size() < size);
			Map<String, Object> row2 = new LinkedHashMap<>();
			result = model.deleteElementInModel(row2);
			assertFalse(result);
		}

		@Test
		public void testGetAllTypeNamesOf() {
			Random rand = new Random();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			int rowIndex = rand.nextInt(rows.size());
			Map<String, Object> row = rows.stream().skip(rowIndex).findFirst().get();
			Collection<String>  names = model.getAllTypeNamesOf(row);
			assertEquals("Only one name.", 1, names.size());
			assertTrue("Name is 'Row'", names.contains("Row"));
			Map<String, Object> row2 = new LinkedHashMap<>();
			thrown.expect(IllegalArgumentException.class);
	        thrown.expectMessage("Not a valid CSV model instance");
			model.getAllTypeNamesOf(row2);
			row2 = new HashMap<>();
			row2.putAll(row);
			model.getAllTypeNamesOf(row2);
		}

		@Test
		public void testGetEnumerationValue() throws Exception {
			thrown.expect(UnsupportedOperationException.class);
	        thrown.expectMessage("CSV Models don't support enumerations.");
			model.getEnumerationValue("enum", "label");
		}

		@Test
		public void testGetTypeNameOf() {
			Random rand = new Random();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			int rowIndex = rand.nextInt(rows.size());
			Map<String, Object> row = rows.stream().skip(rowIndex).findFirst().get();
			String name = model.getTypeNameOf(row);
			assertEquals("Name is 'Row'", LinkedHashMap.class.getName(), name);
			Map<String, Object> row2 = new LinkedHashMap<>();
			thrown.expect(IllegalArgumentException.class);
	        thrown.expectMessage("Not a valid CSV model instance");
			model.getTypeNameOf(row2);
			row2 = new HashMap<>();
			row2.putAll(row);
			model.getTypeNameOf(row2);
		}

		@Test
		public void testOwns() {
			Random rand = new Random();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			int rowIndex = rand.nextInt(rows.size());
			Map<String, Object> row = rows.stream().skip(rowIndex).findFirst().get();
			assertTrue("Row is in model", model.owns(row));
			Map<String, Object> row2 = new LinkedHashMap<>();
			thrown.expect(IllegalArgumentException.class);
	        thrown.expectMessage("Not a valid CSV model instance");
			model.getTypeNameOf(row2);
			row2 = new HashMap<>();
			row2.putAll(row);
			model.getTypeNameOf(row2);
			
			fail("Not yet implemented");
		}

		@Test
		public void testIsInstantiable() {
			String type = "Row";
			assertTrue("Row type is instantiatable.", model.isInstantiable(type));
			type ="other";
			assertFalse("Only Row type is instantiatable", model.isInstantiable(type));
		}

		@Test
		public void testHasType() {
			String type = "Row";
			assertTrue("Row type is known.", model.hasType(type));
			type ="other";
			assertFalse("Only Row type is known", model.hasType(type));
		}

		@Test
		public void testGetAllOfTypeFromModelString() throws Exception {
			Collection<Map<String, Object>> rows = model.getAllOfTypeFromModel("Row");
			Collection<Map<String, Object>> rows2 = model.allContentsFromModel();
			assertEquals(rows2, rows);
			thrown.expect(EolModelElementTypeNotFoundException.class);
			rows = model.getAllOfTypeFromModel("TypeA");
		}

		@Test
		public void testGetAllOfKindFromModelString() throws Exception {
			Collection<Map<String, Object>> rows = model.getAllOfKindFromModel("Row");
			Collection<Map<String, Object>> rows2 = model.allContentsFromModel();
			assertEquals(rows2, rows);
			thrown.expect(EolModelElementTypeNotFoundException.class);
			rows = model.getAllOfKindFromModel("TypeA");
		}

		@Test
		public void testCreateInstanceInModelString() throws Exception {
			Map<String, Object> newRow = model.createInstanceInModel("Row");
			for (String key : headers) {
				assertTrue(String.format("Field %s should be empty", key), ((String) newRow.get(key)).isEmpty());
			}
			thrown.expect(EolModelElementTypeNotFoundException.class);
			newRow = model.createInstanceInModel("TypeA");
		}

		@Test
		public void testDifferentRowsHaveDifferentHashcodes() throws Exception {
			Map<String, Object> newRow1 = model.createInstanceInModel("Row");
			Map<String, Object> newRow2 = model.createInstanceInModel("Row");

			/*
			 * Different rows should have different hashcodes and be considered not to be
			 * equal to each other. Otherwise, this may cause issues in some Epsilon
			 * languages, such as ETL.
			 */
			assertNotEquals(newRow1.hashCode(), newRow2.hashCode());
			assertNotEquals(newRow1, newRow2);
		}

		@Test
		public void testHasProperty() throws Exception {
			for (String key : headers) {
				assertTrue(String.format("Row has property %s.", key), model.hasProperty("Row", key));
			}
			assertFalse("Row doesn't have property someProperty.", model.hasProperty("Row", "someProperty"));
			model.dispose();
			char fieldSeparator = ',';
			final String csv = "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "272-41-1349,Norry,Halpin,nhalpin1@slashdot.org,Female,Legal Assistant,,Aenean sit amet justo. Morbi ut odio.,Drama,Film-Noir,Thriller" + NEWLINE +
							   "850-05-5333,Flossy,Mobberley,fmobberley7@msn.com,Female,Chief Design Engineer,3558038696922012,Nulla tempus.,Comedy,Romance";
			model = new CsvModel();
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(false);
			model.setVarargsHeaders(true);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			assertTrue("Row has property field.", model.hasProperty("Row", "field"));
			assertFalse("Row doesn't have property someProperty.", model.hasProperty("Row", "someProperty"));
			thrown.expect(EolModelElementTypeNotFoundException.class);
			model.hasProperty("TypeA", headers[0]);
		}
		
		
	}

	public static class ElementIdTests {
		
		@Rule
	    public ExpectedException thrown = ExpectedException.none();
		
		@Test
		public void noId() throws Exception {
			char fieldSeparator = ',';
			final String csv = "id,first_name,last_name,email,gender,job,credit_card,quote,movies" + NEWLINE + 
							   "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "272-41-1349,Norry,Halpin,nhalpin1@slashdot.org,Female,Legal Assistant,,Aenean sit amet justo. Morbi ut odio.,Drama,Film-Noir,Thriller" + NEWLINE +
							   "850-05-5333,Flossy,Mobberley,fmobberley7@msn.com,Female,Chief Design Engineer,3558038696922012,Nulla tempus.,Comedy,Romance";
			CsvModel model = new CsvModel();
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(true);
			model.setVarargsHeaders(true);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			Random rand = new Random();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			int rowIndex = rand.nextInt(rows.size());
			Map<String, Object> row = rows.stream().skip(rowIndex).findFirst().get();
			thrown.expect(UnsupportedOperationException.class);
			thrown.expectMessage("The if field has not been set. To use ids, make sure the launch "
					+ "configuration defines the name or index of the id field.");
			model.getElementId(row);
			model.dispose();
		}
		
		@Test
		public void fieldNameId() throws Exception {
			char fieldSeparator = ',';
			final String csv = "id,first_name,last_name,email,gender,job,credit_card,quote,movies" + NEWLINE + 
							   "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "272-41-1349,Norry,Halpin,nhalpin1@slashdot.org,Female,Legal Assistant,,Aenean sit amet justo. Morbi ut odio.,Drama,Film-Noir,Thriller" + NEWLINE +
							   "850-05-5333,Flossy,Mobberley,fmobberley7@msn.com,Female,Chief Design Engineer,3558038696922012,Nulla tempus.,Comedy,Romance";
			CsvModel model = new CsvModel();
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(true);
			model.setVarargsHeaders(true);
			model.setIdFieldName("id");
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			Map<String, Object> row;
			for (int i=0;i<3; i++) {
				row = rows.stream().skip(i).findFirst().get();
				String id = model.getElementId(row);
				assertEquals("Row's id field matches id", id, row.get("id"));
			}
			model.dispose();
		}
		
		@Test
		public void fieldIndexId() throws Exception {
			char fieldSeparator = ',';
			final String csv = "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "272-41-1349,Norry,Halpin,nhalpin1@slashdot.org,Female,Legal Assistant,,Aenean sit amet justo. Morbi ut odio.,Drama,Film-Noir,Thriller" + NEWLINE +
							   "850-05-5333,Flossy,Mobberley,fmobberley7@msn.com,Female,Chief Design Engineer,3558038696922012,Nulla tempus.,Comedy,Romance";
			CsvModel model = new CsvModel();
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(false);
			model.setIdFieldIndex(0);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			Map<String, Object> row;
			for (int i=0;i<3; i++) {
				row = rows.stream().skip(i).findFirst().get();
				String id = model.getElementId(row);
				@SuppressWarnings("unchecked")
				List<String> actual = (List<String>) row.get("field");
				assertEquals("Row's id field matches id", id, actual.get(0));
			}
			model.dispose();
		}
		
		@Test
		public void getElementBtId() throws Exception {
			char fieldSeparator = ',';
			String csv = "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
							   "272-41-1349,Norry,Halpin,nhalpin1@slashdot.org,Female,Legal Assistant,,Aenean sit amet justo. Morbi ut odio.,Drama,Film-Noir,Thriller" + NEWLINE +
							   "850-05-5333,Flossy,Mobberley,fmobberley7@msn.com,Female,Chief Design Engineer,3558038696922012,Nulla tempus.,Comedy,Romance";
			CsvModel model = new CsvModel();
			model.setFieldSeparator(fieldSeparator);
			model.setKnownHeaders(false);
			model.setIdFieldIndex(0);
			StringReader reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			Collection<Map<String, Object>> rows = model.allContentsFromModel();
			Iterator<Map<String, Object>> it = rows.iterator();
			assertEquals("Element matches id.", it.next(), model.getElementById("604-78-8459"));
			assertEquals("Element matches id.", it.next(), model.getElementById("272-41-1349"));
			assertEquals("Element matches id.", it.next(), model.getElementById("850-05-5333"));
			assertNull("Element matches id.", model.getElementById("someId"));
			model.dispose();
			csv = "id,first_name,last_name,email,gender,job,credit_card,quote,movies" + NEWLINE + 
					   "604-78-8459,Ricoriki,Dwyr,rdwyr0@parallels.com,Male,VP Quality Control,,Duis at velit eu est congue elementum.,Horror" + NEWLINE +
					   "272-41-1349,Norry,Halpin,nhalpin1@slashdot.org,Female,Legal Assistant,,Aenean sit amet justo. Morbi ut odio.,Drama,Film-Noir,Thriller" + NEWLINE +
					   "850-05-5333,Flossy,Mobberley,fmobberley7@msn.com,Female,Chief Design Engineer,3558038696922012,Nulla tempus.,Comedy,Romance";
			model.setKnownHeaders(true);
			model.setVarargsHeaders(true);
			model.setIdFieldName("email");
			reader = new StringReader(csv);
			model.setReader(new BufferedReader(reader));
			model.load();
			rows = model.allContentsFromModel();
			it = rows.iterator();
			assertEquals("Element matches id.", it.next(), model.getElementById("rdwyr0@parallels.com"));
			assertEquals("Element matches id.", it.next(), model.getElementById("nhalpin1@slashdot.org"));
			assertEquals("Element matches id.", it.next(), model.getElementById("fmobberley7@msn.com"));
			assertNull("Element matches id.", model.getElementById("someId"));
			model.dispose();
		}
	}
}
