/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - extended AutoCloseable with default impl.
 ******************************************************************************/
package org.eclipse.epsilon.eol.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.m3.Metamodel;
import org.eclipse.epsilon.eol.models.transactions.IModelTransactionSupport;

public interface IModel extends AutoCloseable {

	public static class AmbiguityCheckResult {
		public final IModel model;
		public final List<String> options;

		public AmbiguityCheckResult(IModel model, List<String> options) {
			this.model = model;
			this.options = options;
		}

		public boolean isTypeAmbiguous() {
			return options.size() > 1;
		}
	}

	void load(StringProperties properties) throws EolModelLoadingException;
	
	void load(StringProperties properties, String basePath) throws EolModelLoadingException;
	
	void load(StringProperties properties, IRelativePathResolver relativePathResolver) throws EolModelLoadingException;
	
	void load() throws EolModelLoadingException;
	
	String getName();
	
	void setName(String name);
	
	List<String> getAliases();
	
	Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException;
	
	/**
	 * Returns a collection containing all of the objects contained in 
	 * this model.
	 * 
	 * @return all of the objects contained in this model.
	 */
	Collection<?> allContents();
	
	Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException;
	
	Collection<?> getAllOfKind(String kind) throws EolModelElementTypeNotFoundException;
	
	Object getTypeOf(Object instance);
	
	/**
	 * Returns a string representing the type of the instance object. The value
	 * returned by this function can be passed to createInstance to instantiate
	 * another object of the same type as instance.
	 * 
	 * @param instance The model object whose type is to be determined.
	 * @return the name of the type of the model object, instance.
	 * 
	 * @throws IllegalArgumentException when isModelElement(instance) returns false
	 */
	String getTypeNameOf(Object instance);
	
	/**
	 * Returns a string representing the fully-qualified type of the instance object. The value
	 * returned by this function can be passed to createInstance to instantiate
	 * another object of the same type as instance.
	 * 
	 * @param instance The model object whose type is to be determined.
	 * @return the name of the type of the model object, instance.
	 * 
	 * @throws IllegalArgumentException when isModelElement(instance) returns false
	 */
	String getFullyQualifiedTypeNameOf(Object instance);
	
	Object createInstance(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException;
	
	Object createInstance(String type, Collection<Object> parameters) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException;
	
	Object getElementById(String id);
	
	String getElementId(Object instance);
	
	void setElementId(Object instance, String newId);
	
	void deleteElement(Object instance) throws EolRuntimeException;
	
	boolean isOfKind(Object instance, String type) throws EolModelElementTypeNotFoundException;

	boolean isOfType(Object instance, String type) throws EolModelElementTypeNotFoundException;
	
	/**
	 * Used to test whether an object is contained in this model.
	 *  
	 * @param instance the Java object to test.
	 * @return true if and only if instance is contained by this model.
	 */
	boolean owns(Object instance);
	
	/**
	 * Returns <code>true</code> if this instance could have this property at some point.
	 */
	boolean knowsAboutProperty(Object instance, String property);
	
	/**
	 * Returns <code>true</code> if this instance has an explicit value for this property.
	 * This would be equivalent to eIsSet in EMF.
	 */
	boolean isPropertySet(Object instance, String property) throws EolRuntimeException;
	
	boolean isInstantiable(String type); 
	
	/**
	 * Used to test whether a Java object can be contained by this model. For example,
	 * EMF models can contain instances of EObject, but not instances of just Object.
	 *  
	 * @param instance the Java object to test.
	 * @return true if and only if instance can be contained by this model.
	 */
	boolean isModelElement(Object instance);
	
	boolean hasType(String type);

	/**
	 * Used to test if a given type name is ambiguous or not within a model. For example,
	 * the package registry in an EMF model could include multiple EClasses with the same
	 * name.
	 */
	default AmbiguityCheckResult checkAmbiguity(String type) {
		return new AmbiguityCheckResult(this, hasType(type) ? Collections.singletonList(type) : Collections.emptyList());
	}
	
	boolean store(String location);
	
	boolean store();
	
	void dispose();
	
	IPropertyGetter getPropertyGetter();
	
	IPropertySetter getPropertySetter();
	
	boolean isStoredOnDisposal();
	
	void setStoredOnDisposal(boolean storedOnDisposal);
	
	boolean isReadOnLoad();
	
	void setReadOnLoad(boolean readOnLoad);
	
	IModelTransactionSupport getTransactionSupport();
	
	Metamodel getMetamodel(StringProperties properties, IRelativePathResolver resolver);
	
	/**
	 * @since 1.6
	 */
	@Override
	default void close() {
		this.dispose();
	}
}
