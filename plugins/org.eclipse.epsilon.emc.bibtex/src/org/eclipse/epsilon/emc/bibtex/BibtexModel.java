/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.bibtex;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.bibtex.domain.Bibliography;
import org.eclipse.epsilon.emc.bibtex.domain.Publication;
import org.eclipse.epsilon.emc.bibtex.parser.BibtexParser;
import org.eclipse.epsilon.emc.bibtex.parser.BibtexUnparser;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class BibtexModel extends CachedModel<Publication> {

	public static final String PROPERTY_SOURCE_FILE = "sourceFile";
	
	private String bibtex;
	private Bibliography bibliography = new Bibliography();

	public BibtexModel() {
		propertyGetter = new BibtexPropertyGetter();
	}
	
	@Override
	public IPropertySetter getPropertySetter() {
		return new BibtexPropertySetter();
	}
	
	public void setBibtex(String bibtex) {
		this.bibtex = bibtex;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean isLoaded() {
		return bibtex != null && bibliography != null;
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);

		readBibtexFromFile(properties, resolver);
		load();
	}

	private void readBibtexFromFile(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		try {				
			final File bibtexFile = new File(resolver.resolve(properties.getProperty(PROPERTY_SOURCE_FILE)));
			this.bibtex = FileUtil.getFileContents(bibtexFile);
		} catch (Exception e) {
			throw new EolModelLoadingException(e, this);
		}
	}
	
	@Override
	protected void loadModel() throws EolModelLoadingException {
		bibliography = new BibtexParser(bibtex).parse();
	}

	@Override
	public boolean owns(Object instance) {
		return instance instanceof Publication;
	}
	
	@Override
	public boolean hasType(String type) {
		return bibliography.hasType(type);
	}
	
	@Override
	protected Collection<Publication> allContentsFromModel() {
		return new LinkedList<>(bibliography.publications);
	}
	
	@Override
	protected Collection<Publication> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException {
		return bibliography.allOfType(type);	
	}
	
	@Override
	protected Collection<Publication> getAllOfKindFromModel(String kind) throws EolModelElementTypeNotFoundException {
		return getAllOfType(kind);
	}
	
	@Override
	protected Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException {
		return type;
	}
	
	@Override
	public boolean store(String location) {
		try {
			FileUtil.setFileContents(new BibtexUnparser(bibliography).unparse(), new File(location));
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getTypeOf(Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTypeNameOf(Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getElementById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getElementId(Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setElementId(Object instance, String newId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInstantiable(String type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isModelElement(Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean store() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Publication createInstanceInModel(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void disposeModel() {}

	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) {
		throw new UnsupportedOperationException();
	}
}
