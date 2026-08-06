/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import java.util.Collection;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyAssignmentException;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;

public class EmfPropertySetter extends AbstractPropertySetter implements IReflectivePropertySetter {

	@Override
	public Object coerce(Object target, String property, Object value, IEolContext context) throws EolIllegalPropertyException {
		EStructuralFeature sf = getEStructuralFeature(target, property, value, context);
		if (sf.isMany() && !(value instanceof Collection)) {
			return CollectionUtil.asList(value);
		}
		return value;
	}
	
	@Override
	public boolean conforms(Object target, String property, Object value, IEolContext context) throws EolIllegalPropertyException {
		EStructuralFeature sf = getEStructuralFeature(target, property, value, context);
		if (!sf.isChangeable()) {
			return false;
		}
		if (value instanceof Collection) {
			final Collection<?> collection = ((Collection<?>)value);
			return sf.isMany() &&
			       isConformantSizeForProperty(collection, sf) && 
			       allAreConformantTypeForProperty(collection, sf);
		}
		else {
			return sf.getEType().isInstance(value);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
		EStructuralFeature sf = getEStructuralFeature(target, property, value, context);
		if (sf.isMany()) {
			if (value != null) {
				Collection<Object> sourceValues = (Collection<Object>) getEObject(target, property, value, context).eGet(sf);
				if (value instanceof Collection) {	
					copyCollectionValues(((Collection<?>)value), sourceValues);
				}
				else {
					throw new EolIllegalPropertyAssignmentException(property, context);
				}
			}
		}
		else {
			getEObject(target, property, value, context).eSet(sf, value);
		}
	}
	
	protected void copyCollectionValues(Collection<?> source, Collection<Object> target) {
		target.clear();
		target.addAll(source);
	}
	
	protected EObject getEObject(Object object, String property, Object value, IEolContext context) throws EolIllegalPropertyException {
		if (object instanceof EObject)
			return (EObject) object;
		else
			throw new EolIllegalPropertyException(object, property, context);
	}
	
	private EStructuralFeature getEStructuralFeature(Object object, String property, Object value, IEolContext context) throws EolIllegalPropertyException {
		final EStructuralFeature sf = EmfUtil.getEStructuralFeature(getEObject(object, property, value, context).eClass(), property);	
		if (sf == null) {
			throw new EolIllegalPropertyException(object, property, context);
		}
		return sf;
	}
	
	private boolean isConformantSizeForProperty(Collection<?> values, EStructuralFeature sf) throws EolIllegalPropertyException {
		int size = values.size();
		int lowerbound = sf.getLowerBound();
		int upperbound = sf.getUpperBound();
		if (upperbound == ETypedElement.UNBOUNDED_MULTIPLICITY) {
			upperbound = Integer.MAX_VALUE;
		}
		return lowerbound <= size && size <= upperbound;
	}
	
	private boolean allAreConformantTypeForProperty(Collection<?> values, EStructuralFeature sf) throws EolIllegalPropertyException {
		EClassifier eType = sf.getEType();
		for (Object value : values) {
			if (!eType.isInstance(value))
				return false;
		}		
		return true;
	}
}
