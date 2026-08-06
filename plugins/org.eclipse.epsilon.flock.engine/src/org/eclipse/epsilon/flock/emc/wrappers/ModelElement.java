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

import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;

public class ModelElement extends BackedModelValue<Object> {
	
	private final ModelType type;
	
	static ModelElement create(Model model, Object object) {
		if (!model.isModelElement(object)) 
			throw new IllegalArgumentException("Object is not an element of this model: " + object);
			
		final ModelType type = new ModelType(model, model.getTypeNameOf(object), model.getUnqualifiedTypeNameOf(object));
		return new ModelElement(model, type, object);
	}
	
	ModelElement(Model model, ModelType type, Object underlyingModelElement) {
		super(model, underlyingModelElement);
		this.type = type;
	}
	
	@Override
	public ModelValue<?> getEquivalentIn(Model model, ConservativeCopyContext context) {
		// context.getEquivalent might be null, so ensure return value is wrapped
		return isExternal() ? this : model.wrap(context.getEquivalent(this));
	}
	
	private boolean isExternal() {
		return !model.owns(underlyingModelObject);
	}
	
	public Model getModel() {
		return model;
	}

	public ModelType getType() {
		return type;
	}

	public String getTypeName() {
		return type.getName();
	}
	
	public String getUnqualifiedTypeName() {
		return type.getUnqualifiedName();
	}
	
	public boolean isTypeOf(String type) {
		return model.isTypeOf(underlyingModelObject, type);
	}
	
	public boolean isKindOf(String type) {
		return model.isKindOf(underlyingModelObject, type);
	}
	
	public boolean belongsTo(String originalPackage) {
		// FIXME how about nested packages?
		final List<String> packages = model.getPackageNamesOf(underlyingModelObject);
		return !packages.isEmpty() && packages.get(0).equals(originalPackage);
	}
	
	public ModelElement getContainer() {
		final Object container = model.getContainerOf(underlyingModelObject);
		return container == null ? null : ModelElement.create(model, container);
	}
	
	public void copyIdentityFrom(ModelElement original) {
		setIdentity(original.getIdentity());
	}
	
	String getIdentity() {
		return model.getIdentity(underlyingModelObject);
	}
	
	void setIdentity(String identity) {
		model.setIdentity(underlyingModelObject, identity);
	}

	
	public Collection<String> getPropertiesSharedWith(ModelElement element) throws EolModelElementTypeNotFoundException {
		return type.getPropertiesSharedWith(element.getType());
	}
	
	public ModelValue<?> getValueOfProperty(String property) throws EolRuntimeException {
		return model.getValueOfProperty(underlyingModelObject, property);
	}

	public void conservativelySetValueForProperty(ModelValue<?> equivalentValue, String propertyName, ConservativeCopyContext context) throws EolRuntimeException {		
		if (conforms(propertyName, equivalentValue))
			setValueOfProperty(propertyName, equivalentValue);
	}
	
	private boolean conforms(String property, ModelValue<?> value) throws EolRuntimeException {
		return model.conforms(underlyingModelObject, property, value);
	}
	
	private void setValueOfProperty(String property, ModelValue<?> value) throws EolRuntimeException {
		try {
			model.setValueOfProperty(underlyingModelObject, property, value);
		} catch (Exception e) {
			// TODO - note circumstances in which this block is entered and guard with appropriate checks 
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "<" + model.getStringRepresentationOf(underlyingModelObject) + ">";
	}
}
