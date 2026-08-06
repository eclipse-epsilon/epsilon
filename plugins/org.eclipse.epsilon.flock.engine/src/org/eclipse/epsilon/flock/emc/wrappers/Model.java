/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.emc.wrappers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotAnEnumerationValueException;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinterManager;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;

public class Model {
	
	private final IReflectiveModel underlyingModel;
	private final PrettyPrinterManager printer;
	private final ModelValueWrapper wrapper;
	
	public Model(IReflectiveModel underlyingModel, PrettyPrinterManager printer) {
		this.underlyingModel = underlyingModel;
		this.printer         = printer;
		this.wrapper         = new ModelValueWrapper(this);
	}
	
	// Used by tests to customise wrapper
	Model(IReflectiveModel underlyingModel, ModelValueWrapper wrapper) {
		this.underlyingModel = underlyingModel;
		this.printer         = new PrettyPrinterManager();
		this.wrapper         = wrapper;
	}
	
	// Used by tests that don't wish to supply a printer print manager
	Model(IReflectiveModel underlyingModel) {
		this(underlyingModel, new PrettyPrinterManager());
	}
	
	
	public ModelElement createInstance(String typeName) throws EolRuntimeException {
		return wrapModelElement(underlyingModel.createInstance(typeName));
	}
	
	public boolean hasType(String typeName) {
		return underlyingModel.hasType(typeName);
	}
	
	public boolean isInstantiable(String typeName) {
		return underlyingModel.isInstantiable(typeName);
	}
	
	public boolean hasProperty(String type, String property) throws EolRuntimeException {
		return underlyingModel.hasProperty(type, property);
	}
	
	public boolean hasPackage(String originalPackage) {
		return underlyingModel.hasPackage(originalPackage);
	}
	
	/**
	 * Returns all of the model elements that are directly contained in this model.
	 * This <em>excludes</em> model elements that are referenced from this model,
	 * but contained in another model.
	 */
	public Iterable<ModelElement> directContents() {
		final Collection<ModelElement> modelElements = new LinkedList<>();
		
		for (Object unwrappedModelElement : underlyingModel.allContents()) {
			if (underlyingModel.owns(unwrappedModelElement)) {
				modelElements.add(wrapModelElement(unwrappedModelElement));
			}
		}
		
		return modelElements;
	}
	
	public boolean owns(ModelElement element) {
		return owns(element.unwrap());
	}
	
	public boolean owns(Object unwrappedElement) {
		return underlyingModel.owns(unwrappedElement);
	}
	
	public ModelValue<?> wrap(Object value) {
		return wrapper.wrapValue(value);
	}
	
	ModelElement wrapModelElement(Object object) {
		return wrapper.wrapModelElement(object);
	}
	
	boolean isEnumeration(Object object) {
		return underlyingModel.isEnumerationValue(object);
	}
	
	boolean isModelElement(Object object) {
		return underlyingModel.isModelElement(object);
	}

	String getTypeNameOf(Object underlyingModelElement) {
		return underlyingModel.getFullyQualifiedTypeNameOf(underlyingModelElement);
	}
	
	String getUnqualifiedTypeNameOf(Object underlyingModelElement) {
		return underlyingModel.getTypeNameOf(underlyingModelElement);
	}
	
	List<String> getPackageNamesOf(Object underlyingModelElement) {
		final List<String> parts = Arrays.asList(getTypeNameOf(underlyingModelElement).split("::"));
		if (parts.size() > 1) {
			// List#subList returns a "view" of the original list, which appears to be garbage collected
			// when this method returns. Hence, we construct a new list to return to clients.
			return new LinkedList<>(parts.subList(0, parts.size() - 1));
		} else {
			return Collections.emptyList();
		}
	}
	
	public Object getContainerOf(Object underlyingModelObject) {
		return underlyingModel.getContainerOf(underlyingModelObject);
	}

	public boolean isTypeOf(Object underlyingModelElement, String type) {
		try {
			return underlyingModel.isOfType(underlyingModelElement, type);
			
		} catch (EolModelElementTypeNotFoundException e) {
			return false;
		}
	}
	
	boolean isKindOf(Object underlyingModelElement, String type) {
		try {
			return underlyingModel.isOfKind(underlyingModelElement, type);
			
		} catch (EolModelElementTypeNotFoundException e) {
			return false;
		}
	}
	
	boolean knowsAboutProperty(Object underlyingModelElement, String property) {
		return underlyingModel.knowsAboutProperty(underlyingModelElement, property);
	}

	Collection<String> getPropertiesOf(String type) throws EolModelElementTypeNotFoundException {
		return underlyingModel.getPropertiesOf(type);
	}

	ModelValue<?> getValueOfProperty(Object underlyingModelElement, String property) throws EolRuntimeException {
		return wrapper.wrapValue(underlyingModel.getPropertyGetter().invoke(underlyingModelElement, property, null));
	}

	void setValueOfProperty(Object underlyingModelElement, String property, ModelValue<?> value) throws EolRuntimeException {
		final IReflectivePropertySetter setter = underlyingModel.getPropertySetter();
		setter.invoke(underlyingModelElement, property,
			setter.coerce(underlyingModelElement, property, value.unwrap(), null),
			null
		);
	}
	
	boolean conforms(Object underlyingModelElement, String property, ModelValue<?> value) throws EolRuntimeException {
		final IReflectivePropertySetter setter = underlyingModel.getPropertySetter();
		return setter.conforms(underlyingModelElement, property, value.unwrap(), null);
	}
	
	String getStringRepresentationOf(Object underlyingModelObject) {
		return printer.print(underlyingModelObject);
	}

	Object getEquivalentEnumerationValue(Object literal) throws EolEnumerationValueNotFoundException, EolNotAnEnumerationValueException {		
		final String enumeration = underlyingModel.getEnumerationTypeOf(literal);
		final String label = underlyingModel.getEnumerationLabelOf(literal);
		
		return underlyingModel.getEnumerationValue(enumeration, label);
	}
	
	public Object getUnwrappedEquivalent(Object unwrappedModelElement, Model otherModel, ConservativeCopyContext context) throws ConservativeCopyException {
		return wrap(unwrappedModelElement).getUnwrappedEquivalentIn(otherModel, context);
	}

	public boolean preventLoadingOfExternalModelElements() {
		return underlyingModel.preventLoadingOfExternalModelElements();
	}

	public String getIdentity(Object underlyingModelObject) {
		return underlyingModel.getElementId(underlyingModelObject);
	}
	
	public void setIdentity(Object underlyingModelObject, String newIdentity) {
		underlyingModel.setElementId(underlyingModelObject, newIdentity);
	}
}
