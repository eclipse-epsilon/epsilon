/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - optimisation
 ******************************************************************************/
package org.eclipse.epsilon.eol.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertySetter;
import org.eclipse.epsilon.eol.m3.Metamodel;
import org.eclipse.epsilon.eol.models.transactions.IModelTransactionSupport;
import org.eclipse.epsilon.eol.models.transactions.NoModelTransactionSupport;

public abstract class Model implements IModel {
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_READONLOAD = "readOnLoad";
	public static final String PROPERTY_STOREONDISPOSAL = "storeOnDisposal";
	public static final String PROPERTY_ALIASES = "aliases";
	
	/**
	 * Indicates that the model will not be modified.
	 * @since 1.6
	 */
	public static final String PROPERTY_READONLY = "readOnly";
	
	/**
	 * Used for acquiring properties externally (e.g. from environment variables)
	 * @since 1.6
	 */
	protected static final String ENV_PREFIX = "org.eclipse.epsilon.emc.";
	
	protected String name;
	protected List<String> aliases = new ArrayList<>(1);
	protected boolean storeOnDisposal = false;
	protected boolean readOnLoad = true;
	
	/**
	 * @since 1.6
	 */
	protected IPropertyGetter propertyGetter;
	
	/**
	 * @since 1.6
	 */
	protected IPropertySetter propertySetter;
	
	public Model() {
		propertyGetter = new JavaPropertyGetter();
		propertySetter = new JavaPropertySetter();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public List<String> getAliases() {
		return aliases;
	}
	
	@Override
	public void load(StringProperties properties, String basePath) throws EolModelLoadingException {	
		load(properties, relativePath -> basePath + relativePath);	
	}
	
	@Override
	public void load(StringProperties properties) throws EolModelLoadingException {
		load(properties, relativePath -> relativePath);
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		setName(properties.getProperty(PROPERTY_NAME, name));
		setReadOnLoad(properties.getBooleanProperty(PROPERTY_READONLOAD, readOnLoad));
		setStoredOnDisposal(properties.getBooleanProperty(PROPERTY_STOREONDISPOSAL, storeOnDisposal));
		
		if (properties.hasProperty(PROPERTY_ALIASES)) {
			String[] aliases = properties.getProperty(PROPERTY_ALIASES).split(",");
			CollectionUtil.addCapacityIfArrayList(this.aliases, aliases.length);
			for (String alias : aliases) {
				this.aliases.add(alias.trim());
			}
		}
	}
	
	@Override
	public Object createInstance(String type, Collection<Object> parameters)
			throws EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException {
		return createInstance(type);
	}
	
	public Collection<?> allInstances() {
		return allContents();
	}

	@Override
	public boolean isOfKind(Object instance, String metaClass) throws EolModelElementTypeNotFoundException {
		Collection<?> allOfKind = getAllOfKind(metaClass);
		return allOfKind != null && allOfKind.contains(instance);
	}

	@Override
	public boolean isOfType(Object instance, String metaClass) throws EolModelElementTypeNotFoundException {
		Collection<?> allOfClass = getAllOfType(metaClass);
		return allOfClass != null && allOfClass.contains(instance);	
	}
	
	@Override
	public String getFullyQualifiedTypeNameOf(Object instance) {
		return getTypeNameOf(instance);
	}
	
	@Override
	public boolean isModelElement(Object instance) {
		return owns(instance);
	}
	
	@Override
	public Object getTypeOf(Object instance) {
		return null;
	}
	
	@Override
	public boolean isReadOnLoad() {
		return readOnLoad;
	}

	@Override
	public boolean isStoredOnDisposal() {
		return storeOnDisposal;
	}

	@Override
	public void setReadOnLoad(boolean readOnLoad) {
		this.readOnLoad = readOnLoad;
	}

	@Override
	public void setStoredOnDisposal(boolean storedOnDisposal) {
		this.storeOnDisposal = storedOnDisposal;
	}

	@Override
	public IPropertyGetter getPropertyGetter() {
		return propertyGetter;
	}

	@Override
	public IPropertySetter getPropertySetter() {
		return propertySetter;
	}
	
	@Override
	public void dispose() {
		if (isStoredOnDisposal()) {
			store();
		}
	}
	
	@Override
	public boolean knowsAboutProperty(Object instance, String property) {
		return owns(instance);
	}
	
	NoModelTransactionSupport transactionSupport = new NoModelTransactionSupport();
	
	@Override
	public IModelTransactionSupport getTransactionSupport() {
		return transactionSupport;
	}
	
	@Override
	public Metamodel getMetamodel(StringProperties properties, IRelativePathResolver resolver) {
		return null;
	}

	@Override
	public boolean isPropertySet(Object instance, String property) throws EolRuntimeException {
		return getPropertyGetter().invoke(instance, property, null) != null;
	}
}
