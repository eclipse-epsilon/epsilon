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
package org.eclipse.epsilon.hutn.xmi.postprocessor;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.xmi.util.EmfUtil;

public class UriFragmentPostProcessor {

	private final Spec spec;
	private final UriFragmentResolver resolver;
	
	public UriFragmentPostProcessor(Spec spec) {
		this.spec     = spec;
		this.resolver = new UriFragmentResolver(spec);
	}

	public void process() {	
		resolveUriFragmentValuesInReferenceSlots();
		resolveUriFragmentValuesInAttributeSlots();
	}
	
	private void resolveUriFragmentValuesInReferenceSlots() {
		for (ReferenceSlot slot : allReferenceSlots()) {
			final EList<String> newValues = new BasicEList<>();
			
			for (String value : slot.getValues()) {
				newValues.add(resolveOrReturn(value));
			}
			
			slot.setValues(newValues);
		}
	}
	
	private void resolveUriFragmentValuesInAttributeSlots() {
		for (AttributeSlot slot : allAttributeSlots()) {
			final EList<Object> newValues = new BasicEList<>();
				
			for (Object value : slot.getValues()) {
				newValues.add(resolveOrReturn(value));
			}
			
			slot.setValues(newValues);
		}
	}
	
	private List<ReferenceSlot> allReferenceSlots() {
		return EmfUtil.getAllModelElementsOfType(spec, ReferenceSlot.class);
	}
	
	private List<AttributeSlot> allAttributeSlots() {
		return EmfUtil.getAllModelElementsOfType(spec, AttributeSlot.class);
	}
	
	private Object resolveOrReturn(Object potentialUriFragment) {
		if (potentialUriFragment instanceof String) {
			return resolveOrReturn((String)potentialUriFragment);
	
		} else {
			return potentialUriFragment;
		}
	}
	
	private String resolveOrReturn(String potentialUriFragment) {
		if (UriFragmentResolver.isUriFragment(potentialUriFragment)) {
			return resolveUriFragment(potentialUriFragment);
	
		} else {
			return potentialUriFragment;
		}
	}
	
	private String resolveUriFragment(String uriFragment) {			
		final StringBuilder identifiersOfResolvedObjects = new StringBuilder();
		
		for (ClassObject resolvedObject : resolver.resolve(uriFragment)) {
			final String identifierOfResolvedObject;
			
			if (isAnUnknownClassObject(resolvedObject)) {
				identifierOfResolvedObject = uriFragment;
			
			} else {
				identifierOfResolvedObject = determineIdentifier(resolvedObject);
			}
			
			identifiersOfResolvedObjects.append(identifierOfResolvedObject);
			identifiersOfResolvedObjects.append(' ');
		}
		
		return identifiersOfResolvedObjects.toString().trim();
	}

	private boolean isAnUnknownClassObject(ClassObject object) {
		return object == null;
	}
	
	private String determineIdentifier(ClassObject object) {
		if (object.getIdentifier() == null) {
			object.setIdentifier(EcoreUtil.generateUUID());
		}
		
		return object.getIdentifier();
	}
}
