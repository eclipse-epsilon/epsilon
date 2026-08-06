/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Horacio Hoyos - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.csv;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.csv.CsvModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.Model;
import org.eclipse.epsilon.workflow.tasks.AbstractLoadModelTask;

/**
 * The Ant task to load Csv Models.
 */
public class LoadCsvModelTask extends AbstractLoadModelTask {
	
	/** The field separator. */
	protected String fieldSeparator = ",";
	
	/** The quote char. */
	protected char quoteChar = '"';
	
	/** The has known headers. */
	protected boolean knownHeaders;
	
	/** The has varargs headers. */
	protected boolean varargsHeaders;
	
	/** The field that can be used as id */
	protected String idFieldValue;
	
	/** The file. */
	protected File file;

	/** The alias. */
	protected String alias;

	/** The cached. */
	protected boolean cached = true;

	/** The read on load. */
	protected boolean read = true;

	/** The store on disposal. */
	protected boolean store = false;

	
	@Override
	public IModel loadModel() throws BuildException {
		final CsvModel model = createCsvModel();

		final StringProperties properties = new StringProperties();
		properties.put(Model.PROPERTY_NAME, name + "");
		properties.put(Model.PROPERTY_ALIASES, alias + "");
		properties.put(CachedModel.PROPERTY_CACHED, String.valueOf(cached));
		properties.put(Model.PROPERTY_READONLOAD, String.valueOf(read));
		properties.put(Model.PROPERTY_STOREONDISPOSAL, String.valueOf(store));
		properties.put(CsvModel.PROPERTY_FIELD_SEPARATOR, fieldSeparator);
		properties.put(CsvModel.PROPERTY_QUOTE_CHARACTER, quoteChar);
		properties.put(CsvModel.PROPERTY_HAS_KNOWN_HEADERS, String.valueOf(knownHeaders));
		properties.put(CsvModel.PROPERTY_HAS_VARARGS_HEADERS, String.valueOf(varargsHeaders));
		properties.put(CsvModel.PROPERTY_ID_FIELD, idFieldValue);
		
		if (file != null) {
			properties.put(CsvModel.PROPERTY_FILE, file.getAbsolutePath());
		}
		
		try {
			model.load(properties);
			return model;
		} catch (EolModelLoadingException e) {
			throw new BuildException(e);
		}
		
	}

	/**
	 * Creates the csv model. This logic has been extracted so that it can be
	 * stubbed out in tests
	 *
	 * @return the csv model
	 */
	protected CsvModel createCsvModel() {
		return new CsvModel();
	}

	/**
	 * Gets the field separator.
	 *
	 * @return the fieldSeparator
	 */
	public String getFieldSeparator() {
		return fieldSeparator;
	}

	/**
	 * Sets the field separator.
	 *
	 * @param fieldSeparator the fieldSeparator to set
	 */
	public void setFieldSeparator(String fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	/**
	 * Checks if is known headers.
	 *
	 * @return the knownHeaders
	 */
	public boolean isKnownHeaders() {
		return knownHeaders;
	}

	/**
	 * Sets the known headers.
	 *
	 * @param knownHeaders the knownHeaders to set
	 */
	public void setKnownHeaders(boolean knownHeaders) {
		this.knownHeaders = knownHeaders;
	}

	/**
	 * Checks if is varargs headers.
	 *
	 * @return the varargsHeaders
	 */
	public boolean isVarargsHeaders() {
		return varargsHeaders;
	}

	/**
	 * Sets the varargs headers.
	 *
	 * @param varargsHeaders the varargsHeaders to set
	 */
	public void setVarargsHeaders(boolean varargsHeaders) {
		this.varargsHeaders = varargsHeaders;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias.
	 *
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Checks if is cached.
	 *
	 * @return the cached
	 */
	public boolean isCached() {
		return cached;
	}

	/**
	 * Sets the cached.
	 *
	 * @param cached the cached to set
	 */
	public void setCached(boolean cached) {
		this.cached = cached;
	}

	/**
	 * Checks if is read.
	 *
	 * @return the read
	 */
	public boolean isRead() {
		return read;
	}

	/**
	 * Sets the read.
	 *
	 * @param read the read to set
	 */
	public void setRead(boolean read) {
		this.read = read;
	}

	/**
	 * Checks if is store.
	 *
	 * @return the store
	 */
	public boolean isStore() {
		return store;
	}

	/**
	 * Sets the store.
	 *
	 * @param store the store to set
	 */
	public void setStore(boolean store) {
		this.store = store;
	}

	/**
	 * @return the quoteChar
	 */
	public char getQuoteChar() {
		return quoteChar;
	}

	/**
	 * @param quoteChar the quoteChar to set
	 */
	public void setQuoteChar(char quoteChar) {
		this.quoteChar = quoteChar;
	}

	/**
	 * @return the idFieldValue
	 */
	public String getIdFieldValue() {
		return idFieldValue;
	}

	/**
	 * @param idFieldValue the idFieldValue to set
	 */
	public void setIdFieldValue(String idFieldValue) {
		this.idFieldValue = idFieldValue;
	}
}
