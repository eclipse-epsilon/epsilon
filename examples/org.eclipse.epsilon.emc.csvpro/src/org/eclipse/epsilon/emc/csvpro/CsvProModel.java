/*******************************************************************************
 * Copyright (c) 2016 University of York
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Horacio Hoyos - Initial API and implementation
 *******************************************************************************/
package org.eclipse.epsilon.emc.csvpro;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

// TODO: Auto-generated Javadoc
/**
 * The Class CsvProModel.
 */
public class CsvProModel extends CachedModel<Integer> {
	
	/**
	 * The Class CsvPropertyGetter.
	 */
	private class CsvPropertyGetter extends AbstractPropertyGetter {

		/* (non-Javadoc)
		 * @see org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter#invoke(java.lang.Object, java.lang.String)
		 */
		@Override
		public Object invoke(Object object, String property) throws EolRuntimeException {
			assert object instanceof Integer;
			return data.get(property).get((int) object);
		}
	}
	
	/**
	 * The Class CsvPropertySetter.
	 */
	private class CsvPropertySetter extends AbstractPropertySetter {

		/* (non-Javadoc)
		 * @see org.eclipse.epsilon.eol.execute.introspection.IPropertySetter#invoke(java.lang.Object)
		 */
		@Override
		public void invoke(Object value) throws EolRuntimeException {
			data.get(property).set((int) object, value.toString());
		}		
	}

	/** The Constant PROPERTY_FILE. */
	public static final String PROPERTY_FILE = "file";

	/** The Constant PROPERTY_FIELD_SEPARATOR. */
	public static final String PROPERTY_FIELD_SEPARATOR = "separator";

	/** The Constant PROPERTY_HAS_KNOWN_HEADERS. */
	public static final String PROPERTY_HAS_KNOWN_HEADERS = "useheaders";

	/** The Constant PROPERTY_HAS_VARARGS_HEADERS. */
	public static final String PROPERTY_HAS_VARARGS_HEADERS = "vararg";

	/** The Constant PROPERTY_USE_TYPE_COLUMN. */
	public static final String PROPERTY_USE_TYPE_COLUMN = "usetypecolumn";
	
	/** The Constant PROPERTY_TYPE_COLUMN. */
	public static final String PROPERTY_TYPE_COLUMN = "typecolumn";

	/** The Constant PROPERTY_USE_INDEX_COLUMN. */
	public static final String PROPERTY_USE_INDEX_COLUMN = "useindexcolumn";

	/** The Constant PROPERTY_INDEX_COLUMN. */
	public static final String PROPERTY_INDEX_COLUMN = "indexcolumn";
	
	/** The store the CSV data in columns mapped by column name. */
	private Map<String, List<String>> data;
	
	/** The rows. */
	private TreeMap<String, Integer> rows;		// Keep track of rows index and allow add/delete
	
	/** The typed elements. */
	private Map<String, CsvProCollection> typedElements;
		
	/** The pg. */
	private CsvPropertyGetter pg;

	/** The ps. */
	private CsvPropertySetter ps;

	/** The file. */
	private String file;

	/** The field separator. */
	private String fieldSeparator;

	/** The known headers. */
	private boolean knownHeaders;

	/** The varargs headers. */
	private boolean varargsHeaders;
	
	/** The type colum. */
	private int typeColum;
	
	/** The type colum name. */
	private String typeColumName;
	
	/** The use type colum. */
	private boolean useTypeColum;

	/** The index colum. */
	private int indexColum;

	/** The use index colum. */
	private boolean useIndexColum;
	
	/** The index colum name. */
	private String indexColumName;
	
	/** The all contents cache. */
	private CsvProCollection allContentsCache;

	/**
	 * Instantiates a new csv pro model.
	 */
	public CsvProModel() {
		super();
		pg = new CsvPropertyGetter();
		ps = new CsvPropertySetter();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#allContents()
	 */
	public Collection<Integer> allContents() {
		if (!isCachingEnabled()) {
			CsvProCollection ret = getNewCsvProCollection();
			ret.setRows(rows);
			return ret;
		}
		if (allContentsCache == null) {
			allContentsCache = getNewCsvProCollection();
			allContentsCache.setRows(rows);
		}
		return allContentsCache;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getAllOfType(java.lang.String)
	 */
	public Collection<Integer> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		if (!isCachingEnabled()) {
			return getAllOfTypeFromModel(type);
		}
		else {
			return super.getAllOfType(type);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getAllOfKind(java.lang.String)
	 */
	public Collection<Integer> getAllOfKind(String kind) throws EolModelElementTypeNotFoundException {
		if (!isCachingEnabled()) {
			return getAllOfKindFromModel(kind);
		}
		else {
			return super.getAllOfKind(kind);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#allContentsFromModel()
	 */
	@Override
	protected Collection<Integer> allContentsFromModel() {
		CsvProCollection values = getNewCsvProCollection();
		values.setRows(rows);
		return values;
	}
	
	/**
	 * Gets the new csv pro collection.
	 *
	 * @return the new csv pro collection
	 */
	private CsvProCollection getNewCsvProCollection() {
		if (useIndexColum) {
			return new CsvProCollection(indexColumName);
		}
		else {
			return new CsvProCollection();
		}
	}

	/**
	 * When creating an instance several consideration have to be taken into consideration:
	 *	- If no typing, then a new entry in the data map must be craeted to add "blank" field values for all attributes
	 *	  of the new row
	 *  - If typed, then the attribute used for typing should be set accordingly and the new row shouls also be added
	 *    to the typedEloements map
	 *  - If indexing is used, during instantiation there is no valid value for the id attribute, so it can be added with
	 *    a blank id to the rows tree. However, this will not work if several elements ace created in batch. A
	 *    possibility is to use the row number as an id temporally, but care has to be taken to avoid clashes. The
	 *    PropertySetter must be made aware of indexing so it can fix the key value for an object when the id or do it
	 *    via the setElementId, but then make sure that the id attribute is read only. 
	 */
	@Override
	protected Integer createInstanceInModel(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		throw new UnsupportedOperationException("This method is left unimplemented for the users to complete the example.");
	}

	/**
	 * The row information must be deleted from all data structures. 
	 */
	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		throw new UnsupportedOperationException("This method is left unimplemented for the users to complete the example.");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#disposeModel()
	 */
	@Override
	protected void disposeModel() {
		data.clear();
		data = null;
		rows.clear();
		rows = null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getAllOfKindFromModel(java.lang.String)
	 */
	@Override
	protected Collection<Integer> getAllOfKindFromModel(String kind)
			throws EolModelElementTypeNotFoundException {
		return getAllOfTypeFromModel(kind);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getAllOfTypeFromModel(java.lang.String)
	 */
	@Override
	protected Collection<Integer> getAllOfTypeFromModel(String type)
			throws EolModelElementTypeNotFoundException {
		if (!("Row".equals(type) || (useTypeColum && typedElements.containsKey(type)))) {
			throw new EolModelElementTypeNotFoundException(this.name, type);
		}
		if ("Row".equals(type))
			return allContents();
		else
			return typedElements.get(type);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getAllTypeNamesOf(java.lang.Object)
	 */
	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) {
		if (owns(instance)) {
			List<String> result = new ArrayList<>();
			result.add("Row");
			if (useTypeColum) {
				result.add(data.get(typeColumName).get((int) instance));
			}
			return result;
		}
		return Collections.emptyList();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#getCacheKeyForType(java.lang.String)
	 */
	@Override
	protected Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#getElementById(java.lang.String)
	 */
	@Override
	public Object getElementById(String id) {
		return rows.get(id);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#getElementId(java.lang.Object)
	 */
	@Override
	public String getElementId(Object instance) {
		assert owns(instance);
		return data.get(indexColumName).get((int) instance);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#getEnumerationValue(java.lang.String, java.lang.String)
	 */
	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		throw new UnsupportedOperationException("Not Implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.Model#getPropertyGetter()
	 */
	@Override
	public IPropertyGetter getPropertyGetter() {
		return pg;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.Model#getPropertySetter()
	 */
	@Override
	public IPropertySetter getPropertySetter() {
		return ps;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#getTypeNameOf(java.lang.Object)
	 */
	@Override
	public String getTypeNameOf(Object instance) {
		if (owns(instance)) {
			if (useTypeColum) {
				return data.get(typeColumName).get((int) instance);
			}
			return "Row";
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#hasType(java.lang.String)
	 */
	@Override
	public boolean hasType(String type) {
		return "Row".equals(type) || (useTypeColum && typedElements.containsKey(type));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#isInstantiable(java.lang.String)
	 */
	@Override
	public boolean isInstantiable(String type) {
		if (!useTypeColum) {
			return "Row".equals(type);
		}
		else {
			return typedElements.containsKey(type);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#load(org.eclipse.epsilon.common.util.StringProperties, org.eclipse.epsilon.eol.models.IRelativePathResolver)
	 */
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		
		this.file = resolver.resolve(properties.getProperty(PROPERTY_FILE));
		this.fieldSeparator = properties.getProperty(PROPERTY_FIELD_SEPARATOR);
		this.knownHeaders = properties.getBooleanProperty(PROPERTY_HAS_KNOWN_HEADERS, true);
		this.varargsHeaders = properties.getBooleanProperty(PROPERTY_HAS_VARARGS_HEADERS, false);
		this.typeColum = properties.getIntegerProperty(PROPERTY_TYPE_COLUMN, 0);
		this.useTypeColum = properties.getBooleanProperty(PROPERTY_USE_TYPE_COLUMN, false);
		this.indexColum = properties.getIntegerProperty(PROPERTY_INDEX_COLUMN, 0);
		this.useIndexColum = properties.getBooleanProperty(PROPERTY_USE_INDEX_COLUMN, false);
		load();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.CachedModel#loadModel()
	 */
	@Override
	protected void loadModel() throws EolModelLoadingException {
		LinkedList<String> lines = null;
		try {
			lines = new LinkedList<>(FileUtil.getFileLineContents(new File(file)));
		} catch (Exception ex) {
			throw new EolModelLoadingException(ex, this);
		} finally {
			if(this.knownHeaders) {
				if (useTypeColum) {
					typedElements = new HashMap<>(lines.size());
				}
				rows = new TreeMap<>();
				List<String> keys = Arrays.asList(lines.get(0).split(this.fieldSeparator));
				data = new HashMap<>(keys.size());
				if (useTypeColum) {
					typeColumName = keys.get(typeColum);
				}
				if (useIndexColum) {
					indexColumName = keys.get(indexColum);
				}
				for (String k : keys) {
					data.put(k, new ArrayList<String>());
				}
				List<String> values;
				for (int i=1; i < lines.size(); i++) {
					int index = i-1;
					values = Arrays.asList(lines.get(i).split(this.fieldSeparator));
					if (keys.size() != values.size()) {
						Exception ex = new Exception("Line " + (i+1) + " contains different number of elements than the header");
						throw new EolModelLoadingException(ex, this);
					}
					for (int f=0; f<keys.size(); f++) {
						List<String> datavals = data.get(keys.get(f));
						if (useTypeColum && (f == typeColum)) {
							CsvProCollection typed = typedElements.get(values.get(f));
							if (typed == null) {
								typed = getNewCsvProCollection();
								typed.setRows(new TreeMap<String, Integer>());
								typedElements.put(values.get(f), typed);
							}
							if (useIndexColum) {
								typed.put(values.get(indexColum), index);
							}
							else {
								typed.put(String.valueOf(index), index);
							}
						}
						if (useIndexColum && (f == indexColum)) {
							rows.put(values.get(f), index);
						}
						else {
							rows.put(String.valueOf(index), index);
						}
						datavals.add(values.get(f));
					}
				}
			} else {
				Exception ex = new Exception("CSV Pro models need headers");
				throw new EolModelLoadingException(ex, this);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#owns(java.lang.Object)
	 */
	@Override
	public boolean owns(Object instance) {
		return rows.containsValue(instance); 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#setElementId(java.lang.Object, java.lang.String)
	 */
	@Override
	public void setElementId(Object instance, String newId) {
		// Probably id change is allowed when indexing is selected
		throw new UnsupportedOperationException("Row Id can not be changed");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#store()
	 */
	@Override
	public boolean store() {
		throw new UnsupportedOperationException("Not Implemented");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.eol.models.IModel#store(java.lang.String)
	 */
	@Override
	public boolean store(String location) {
		throw new UnsupportedOperationException("Not Implemented");
	}
		
	
}
