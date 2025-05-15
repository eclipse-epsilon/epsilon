/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Horacio Hoyos - Support for headers and model modification/save
 ******************************************************************************/
package org.eclipse.epsilon.emc.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

/**
 * The Class CsvModel provides The Epsilon Model Connectivity Layer for CSV
 * files (http://tools.ietf.org/html/rfc4180), with the difference that it 
 * supports files in which each line has a different number of fields.
 * <p>
 * Three properties allow configuration of how to read the CSV file:
 * <ul>
 * <li>PROPERTY_FIELD_SEPARATOR, by default a comma (','), but you can specify
 * a different separator. In particular, fields are identified using
 * {@link java.lang.String#split(String)}, and as such, PROPERTY_FIELD_SEPARATOR is
 * actually treated as regular expression (see <a href="Regex>http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html#sum)</a>
 * <li>PROPERTY_HAS_KNOWN_HEADERS, "There maybe an optional header line appearing
 * as the first line of the file with the same format as normal record lines. This
 * header will contain names corresponding to the fields in the file and should
 * contain the same number of fields as the records in the rest of the file". If
 * this property is set to true, model loading will verify that all lines have the
 * same number of fields as the header. When accessing the model elements (rows)
 * each of the header fields can be used to access information on each record.
 * <li>PROPERTY_HAS_VARARGS_HEADERS, allows the use of varargs headers. This is
 * a special type of header in which the last filed in the header line is used
 * as a base name for all fields after this one. This allows for lines to have a
 * variable number of fields. During model loading it will be verified that each
 * line has at least the same number of fields as header fields minus one. Fields
 * in a record including and following the last header filed will be assigned the
 * header lastHeader0, lastHeader1, lastHeader2,... and so on an so forth.
 * </ul>
 * 
 * 
 */
public class CsvModel extends CachedModel<Map<String, Object>> {

	/**
	 * Various Epsilon languages assume that different model
	 * elements will have different hashcodes (e.g. ETL). This
	 * subclass of LinkedHashMap reverts to system identity-based
	 * hashcodes, to ensure that.
	 */
	protected static class Row extends LinkedHashMap<String, Object> {
		private static final long serialVersionUID = 1L;

		@Override
		public int hashCode() {
			return System.identityHashCode(this);
		}

		@Override
		public boolean equals(Object other) {
			return this == other;
		}
	}

	public static final String HEADERLESS_FIELD_NAME = "field";

	/** The Constant PROPERTY_FILE. */
	public static final String PROPERTY_FILE = "file";

	/** The Constant PROPERTY_FIELD_SEPARATOR. */
	public static final String PROPERTY_FIELD_SEPARATOR = "fieldSeparator";

	/** The Constant PROPERTY_HAS_KNOWN_HEADERS. */
	public static final String PROPERTY_HAS_KNOWN_HEADERS = "hasKnownHeaders";
	
	/** The Constant PROPERTY_HAS_VARARGS_HEADERS. */
	public static final String PROPERTY_HAS_VARARGS_HEADERS = "hasVarargsHeaders";

	public static final String PROPERTY_FILE_ENCODING = "fileEncoding";

	public static final String PROPERTY_QUOTE_CHARACTER = "quoteCharacter";
	
	public static final String PROPERTY_ID_FIELD = "idField";
	
	/** The field separator. */
	protected char fieldSeparator = ',';
	
	/** The quote char. */
	protected char quoteChar = '"';
	
	/** The has known headers. */
	protected boolean knownHeaders;
	
	/** The has varargs headers. */
	protected boolean varargsHeaders;
	
	/** The field that can be used as id */
	protected String idFieldName = "";			// When using header
	protected int idFieldIndex = -1;			// For no header
	
	/** The file path. */
	protected String file;
	
	/* Objects in this model are Maps */
	/** The rows. */
	protected List<Map<String, Object>> rows;
	
	/** The file reader */
	private BufferedReader reader;

	protected Charset cs;
	
	public CsvModel() {
		propertyGetter = new CsvPropertyGetter();
		propertySetter = new CsvPropertySetter();
	}
	
	/**
	 * Gets the field separator.
	 *
	 * @return the field separator
	 */
	public Character getFieldSeparator() {
		return fieldSeparator;
	}

	/**
	 * Sets the field separator.
	 *
	 * @param fieldSeparator the new field separator
	 */
	public void setFieldSeparator(Character fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	/**
	 * Checks if is known headers.
	 *
	 * @return true, if is known headers
	 */
	public boolean isKnownHeaders() {
		return knownHeaders;
	}

	/**
	 * Sets the known headers.
	 *
	 * @param knownHeaders the new known headers
	 */
	public void setKnownHeaders(boolean knownHeaders) {
		this.knownHeaders = knownHeaders;
	}

	/**
	 * Checks if using variable args headers.
	 *
	 * @return true, if using variable args headers
	 */
	public boolean isVarargsHeaders() {
		return varargsHeaders;
	}

	/**
	 * Sets the variable args headers.
	 *
	 * @param varargsHeaders true if using variable args headers.
	 */
	public void setVarargsHeaders(boolean varargsHeaders) {
		this.varargsHeaders = varargsHeaders;
	}
	

	/**
	 * @return the id Field Name
	 */
	public String getIdFieldName() {
		return idFieldName;
	}

	/**
	 * @param idFieldName the name of the field to use as id
	 */
	public void setIdFieldName(String idFieldName) {
		this.idFieldName = idFieldName;
	}

	/**
	 * @return the id Field Index
	 */
	public int getIdFieldIndex() {
		return idFieldIndex;
	}

	/**
	 * @param idFieldIndex the index of the field to use as id
	 */
	public void setIdFieldIndex(int idFieldIndex) {
		this.idFieldIndex = idFieldIndex;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#getEnumerationValue(java.lang.String, java.lang.String)
	 */
	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		throw new UnsupportedOperationException("CSV Models don't support enumerations.");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.Model#getTypeOf(java.lang.Object)
	 */
	@Override
	public Object getTypeOf(Object instance) {
		if (!isModelElement(instance)) {
			throw new IllegalArgumentException("Not a valid CSV model instance");
		}
		return LinkedHashMap.class;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#getTypeNameOf(java.lang.Object)
	 */
	@Override
	public String getTypeNameOf(Object instance) {
		if (!isModelElement(instance)) {
			throw new IllegalArgumentException("Not a valid CSV model instance");
		}
		return LinkedHashMap.class.getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#getElementById(java.lang.String)
	 */
	@Override
	public Object getElementById(String id) {
		if (idFieldName.isEmpty() && (idFieldIndex == -1)) {
			throw new UnsupportedOperationException("The if field has not been set. To use ids, make sure the launch "
					+ "configuration defines the name or index of the id field.");
		}
		if (!idFieldName.isEmpty()) {
			for (Map<String, Object> row : rows) {
				if (row.get(idFieldName).equals(id)) {
					return row;
				}
			}
		}
		else {
			for (Map<String, Object> row : rows) {
				List<?> fields = (List<?>) row.get(HEADERLESS_FIELD_NAME);
				if (fields.get(idFieldIndex).equals(id)) {
					return row;
				}
			}

		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#getElementId(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getElementId(Object instance) {
		if (idFieldName.isEmpty() && (idFieldIndex == -1)) {
			throw new UnsupportedOperationException("The if field has not been set. To use ids, make sure the launch "
					+ "configuration defines the name or index of the id field.");
		}
		Map<String, Object> row = (Map<String, Object>) instance;
		if (!idFieldName.isEmpty()) {
			return (String) row.get(idFieldName);
		}
		else {
			List<Object> fields = (List<Object>) row.get(HEADERLESS_FIELD_NAME);
			return (String) fields.get(idFieldIndex);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#setElementId(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setElementId(Object instance, String newId) {
		if (idFieldName.isEmpty() && (idFieldIndex == -1)) {
			throw new UnsupportedOperationException("The if field has not been set. To use ids, make sure the launch "
					+ "configuration defines the name or index of the id field.");
		}
		Map<String, Object> row = (Map<String, Object>) instance;
		if (!idFieldName.isEmpty()) {
			row.put(idFieldName, newId);
		}
		else {
			List<Object> fields = (List<Object>) row.get(HEADERLESS_FIELD_NAME);
			fields.set(idFieldIndex, newId);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#owns(java.lang.Object)
	 */
	@Override
	public boolean owns(Object instance) {
		return rows.contains(instance);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#isInstantiable(java.lang.String)
	 */
	@Override
	public boolean isInstantiable(String type) {
		return hasType(type);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.Model#isModelElement(java.lang.Object)
	 */
	@Override
	public boolean isModelElement(Object instance) {
		return (instance instanceof LinkedHashMap) && rows.contains(instance);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#hasType(java.lang.String)
	 */
	@Override
	public boolean hasType(String type) {
		return "Row".equals(type);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#store(java.lang.String)
	 */
	@Override
	public boolean store(String location) {
		try {
			FileUtil.setFileContents(concatenateMap(), new File(location));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#store()
	 */
	@Override
	public boolean store() {
		
		try {
			FileUtil.setFileContents(concatenateMap(), new File(this.file));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String concatenateMap() {
		StringBuilder output = new StringBuilder();
		if (this.knownHeaders) {
			// First line is the headers
			Iterator<String> keyIt = rows.get(0).keySet().iterator();
			output.append(keyIt.next());
			while (keyIt.hasNext()) {
				output.append(this.fieldSeparator);
				output.append(keyIt.next());
			}
			output.append(System.getProperty("line.separator"));
		}
		for (Map<String, Object> row : rows) {
			Iterator<Object> fieldIt = row.values().iterator();
			output.append(fieldIt.next());
			while (fieldIt.hasNext()) {
				output.append(this.fieldSeparator);
				output.append(fieldIt.next());
			}
			output.append(System.getProperty("line.separator"));
			
		}
		return output.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#allContentsFromModel()
	 */
	@Override
	protected Collection<Map<String, Object>> allContentsFromModel() {
		return rows;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getAllOfTypeFromModel(java.lang.String)
	 */
	@Override
	protected Collection<Map<String, Object>> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException {
		if (!"Row".equals(type)) {
			throw new EolModelElementTypeNotFoundException(this.name, type);
		}
		return allContents();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getAllOfKindFromModel(java.lang.String)
	 */
	@Override
	protected Collection<Map<String, Object>> getAllOfKindFromModel(String kind) throws EolModelElementTypeNotFoundException {
		if (!"Row".equals(kind)) {
			throw new EolModelElementTypeNotFoundException(this.name, kind);
		}
		return allContents();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#createInstanceInModel(java.lang.String)
	 */
	@Override
	protected Row createInstanceInModel(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		if (!"Row".equals(type)) {
			throw new EolModelElementTypeNotFoundException(this.name, type);
		}

		Row returnVal = new Row();
		for (String key : rows.get(0).keySet()) {
			returnVal.put(key, "");
		}
		rows.add(returnVal);
		return returnVal;
	}

	/**
	 * Checks if the model elements have this property.
	 * 
	 *
	 * @param type the type (should always be "Row" (see {@link #hasType(String)} )
	 * @param property the property
	 * @return true, if successful
	 * @throws EolModelElementTypeNotFoundException the eol model element type not found exception
	 */
	public boolean hasProperty(String type, String property) throws EolModelElementTypeNotFoundException {
		if (!hasType(type)) {
			throw new EolModelElementTypeNotFoundException(this.name, type);
		}
		if (!this.knownHeaders) {
			return property.equals(HEADERLESS_FIELD_NAME);
		} else {
			return rows.get(0).keySet().contains(property);
		}
	}

	@Override
	protected void loadModel() throws EolModelLoadingException {
		try {
			rows = createRows(reader, knownHeaders, fieldSeparator, varargsHeaders);
		} catch (Exception e) {
			throw new EolModelLoadingException(e, this);
		}
	}

	/**
	 * @param rows 
	 * @param reader 
	 * @param varargsHeaders 
	 * @param fieldSeparator 
	 * @param knownHeaders 
	 * @throws EolModelLoadingException
	 */
	protected static List<Map<String, Object>> createRows(BufferedReader reader,
			boolean knownHeaders, char fieldSeparator, boolean varargsHeaders) throws Exception {
		
		List<Map<String, Object>> rows = new LinkedList<>();
		CSVFormat csvFormat = CSVFormat.RFC4180.withDelimiter(fieldSeparator);
		if (knownHeaders) csvFormat = csvFormat.withFirstRecordAsHeader();
		
		try (CSVParser records = csvFormat.parse(reader)) {
			if (knownHeaders) {
				for (CSVRecord record : records) {
					Row row = new Row();
					if (!varargsHeaders) {
						for (Map.Entry<String, String> entry : record.toMap().entrySet()) {
							row.put(entry.getKey(), entry.getValue());
						}
					}
					else {
						Map<String, Integer> hm = records.getHeaderMap();
						Set<String> hmKeys = hm.keySet();
						Iterator<String> hmKeysIT = hmKeys.iterator();
						Iterator<String> it = record.iterator();
						int i = 0;
						int normalFields = hmKeys.size()-1;
						while (it.hasNext() && (i < normalFields)) {
							String value = it.next();
							row.put(hmKeysIT.next(), value);
							i++;
						}
						List<String> varargsField = new ArrayList<>();
						while (it.hasNext()) {
							varargsField.add(it.next());	
						}
						row.put(hmKeysIT.next(), varargsField);
					}
					rows.add(row);
				}
			}
			else {
				for (CSVRecord record : records) {
					List<String> values = new ArrayList<>();
					record.iterator().forEachRemaining(values::add);
					Row row = new Row();
					row.put("field", values);
					rows.add(row);
				}
			}
		}
		catch (IOException e) {
			throw new IllegalStateException("Error reading the CSV file");
		}
		finally {
			if (reader != null) {
				try { reader.close(); } catch (IOException e) { }
			}
		}
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#load(org.eclipse.epsilon.common.util.StringProperties, java.lang.String)
	 */
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		
		file = resolver.resolve(properties.getProperty(PROPERTY_FILE)); 
	    fieldSeparator = properties.getProperty(PROPERTY_FIELD_SEPARATOR, ",").charAt(0); 
	    quoteChar = properties.getProperty(PROPERTY_QUOTE_CHARACTER, "\"").charAt(0); 
	    knownHeaders = properties.getBooleanProperty(PROPERTY_HAS_KNOWN_HEADERS, true); 
	    varargsHeaders = properties.getBooleanProperty(PROPERTY_HAS_VARARGS_HEADERS, false); 
	    if (knownHeaders) { 
	      idFieldName = properties.getProperty(PROPERTY_ID_FIELD, ""); 
	    } 
	    else { 
	       
	      int integerProperty = properties.getIntegerProperty(PROPERTY_ID_FIELD, -1); 
	      if (integerProperty >= 0) { 
	        idFieldIndex = integerProperty; 
	      } 
	    } 
	    cs = Charset.forName((String) properties.getOrDefault(PROPERTY_FILE_ENCODING, "UTF-8")); 
		try {
			setReader(Files.newBufferedReader(Paths.get(this.file), cs));
		} catch (IOException e) {
			throw new EolModelLoadingException(e, this);
		}
		load();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#disposeModel()
	 */
	@Override
	protected void disposeModel() {
		if (rows != null) rows.clear();
	}
	
	@Override
	public boolean isLoaded() {
		return rows != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#deleteElementInModel(java.lang.Object)
	 */
	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		return rows.remove(instance);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getCacheKeyForType(java.lang.String)
	 */
	@Override
	protected Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getAllTypeNamesOf(java.lang.Object)
	 */
	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) {
		if (!isModelElement(instance)) {
			throw new IllegalArgumentException("Not a valid CSV model instance");
		}
		return Collections.singleton("Row");
	}
	
	/**
	 * Assign the reader used by the model.
	 * 
	 * @param reader
	 */
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}
}
