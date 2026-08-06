/*******************************************************************************
 * Copyright (c) 2008-2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - implemented IComparableModel and IAdaptableModel
 ******************************************************************************/
package org.eclipse.epsilon.eol.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.execute.operations.contributors.IOperationContributorProvider;
import org.eclipse.epsilon.eol.execute.operations.contributors.IWrapper;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.m3.Metamodel;
import org.eclipse.epsilon.eol.models.transactions.IModelTransactionSupport;

public class ModelReference implements IAdaptableModel, IWrapper, IOperationContributorProvider {

	protected IModel target;
	protected String name;
	protected List<String> aliases = new ArrayList<>();
	
	public ModelReference(IModel target) {
		this.target = target;
		this.name = target.getName();
		this.aliases.addAll(target.getAliases());
	}
	
	public IModel getTarget() {
		return target;
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
	public Collection<?> allContents() {
		return target.allContents();
	}

	@Override
	public Object createInstance(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		return target.createInstance(type);
	}

	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		target.deleteElement(instance);
	}

	@Override
	public void dispose() {
		target.dispose();
	}

	@Override
	public Collection<?> getAllOfKind(String type) throws EolModelElementTypeNotFoundException {
		return target.getAllOfKind(type);
	}

	@Override
	public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		return target.getAllOfType(type);
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		return target.getEnumerationValue(enumeration, label);
	}

	@Override
	public Object getElementById(String id) {
		return target.getElementById(id);
	}

	@Override
	public String getElementId(Object instance) {
		return target.getElementId(instance);
	}
	
	@Override
	public void setElementId(Object instance, String newId) {
		target.setElementId(instance, newId);
	}

	@Override
	public IPropertyGetter getPropertyGetter() {
		return target.getPropertyGetter();
	}

	@Override
	public IPropertySetter getPropertySetter() {
		return target.getPropertySetter();
	}

	@Override
	public Object getTypeOf(Object instance) {
		return target.getTypeOf(instance);
	}
	
	@Override
	public String getTypeNameOf(Object instance) {
		return target.getTypeNameOf(instance);
	}
	
	@Override
	public String getFullyQualifiedTypeNameOf(Object instance) {
		return target.getFullyQualifiedTypeNameOf(instance);
	}

	@Override
	public boolean hasType(String type) {
		return target.hasType(type);
	}

	@Override
	public boolean isInstantiable(String type) {
		return target.isInstantiable(type);
	}

	@Override
	public boolean isModelElement(Object instance) {
		return target.isModelElement(instance);
	}

	@Override
	public boolean isOfKind(Object instance, String type) throws EolModelElementTypeNotFoundException {
		return target.isOfKind(instance,type);
	}

	@Override
	public boolean isOfType(Object instance, String type) throws EolModelElementTypeNotFoundException {
		return target.isOfType(instance, type);
	}

	@Override
	public boolean isReadOnLoad() {
		return target.isReadOnLoad();
	}

	@Override
	public boolean isStoredOnDisposal() {
		return target.isStoredOnDisposal();
	}

	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		target.load(properties, resolver);
	}

	@Override
	public void load() throws EolModelLoadingException {
		target.load();
	}

	@Override
	public boolean owns(Object instance) {
		return target.owns(instance);
	}

	@Override
	public void setReadOnLoad(boolean readOnLoad) {
		target.setReadOnLoad(readOnLoad);
	}

	@Override
	public void setStoredOnDisposal(boolean storedOnDisposal) {
		target.setStoredOnDisposal(storedOnDisposal);
	}

	@Override
	public boolean store(String fileName) {
		return target.store(fileName);
	}

	@Override
	public boolean store() {
		return target.store();
	}

	@Override
	public IModelTransactionSupport getTransactionSupport() {
		return target.getTransactionSupport();
	}

	@Override
	public boolean knowsAboutProperty(Object instance, String property) {
		return target.knowsAboutProperty(instance, property);
	}

	@Override
	public boolean isPropertySet(Object instance, String property) throws EolRuntimeException {
		return target.isPropertySet(instance, property);
	}

	@Override
	public Object createInstance(String type, Collection<Object> parameters)
			throws EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException {
		return target.createInstance(type, parameters);
	}

	@Override
	public Object getWrapped() {
		return target;
	}

	@Override
	public <T> T adaptTo(Class<T> modelType) {
		if (modelType.isInstance(target)) {
			return modelType.cast(target);
		}
		else {
			return null;
		}
	}

	@Override
	public OperationContributor getOperationContributor() {
		if (target instanceof IOperationContributorProvider) {
			return ((IOperationContributorProvider) target).getOperationContributor();
		}
		else return null;
	}

	@Override
	public void load(StringProperties properties, String basePath)
			throws EolModelLoadingException {
		target.load(properties, basePath);
	}
	
	@Override
	public void load(StringProperties properties)
			throws EolModelLoadingException {
		target.load(properties);
	}

	@Override
	public Metamodel getMetamodel(StringProperties properties, IRelativePathResolver resolver) {
		return target.getMetamodel(properties, resolver);
	}

}
