/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.xmi;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;

class PartialXMIHander extends SAXXMIHandler {
	
	protected PartialXMILoadConfiguration configuration = null;
	protected String feature = null;
	
	public PartialXMIHander(XMLResource xmiResource, XMLHelper helper, Map<?, ?> options) {
		super(xmiResource, helper, options);
		this.configuration = (PartialXMILoadConfiguration) options
				.get(PartialXMIResource.OPTION_PARTIAL_LOADING_CONFIGURATION);
	}

	@Override
	protected void setAttribValue(EObject object, String name, String value) {
		if (configuration != null && configuration.getPlaceholders().contains(object)) return;
		if (configuration == null || configuration.shouldSetValue(object, name)) {
			super.setAttribValue(object, name, value);
		}
	}
	
	@Override
	protected void handleFeature(String prefix, String name) {
		this.feature = name;
		super.handleFeature(prefix, name);
		this.feature = null;
	}
	
	@Override
	protected EObject createObjectFromFactory(EFactory factory, String typeName) {
		EClassifier eClassifier = factory.getEPackage().getEClassifier(typeName);
		if (configuration != null && eClassifier instanceof EClass) {
			EClass eClass = (EClass) eClassifier;
			if (configuration.shouldCreateObject(eClass, objects.peekEObject(), feature)) {
				return super.createObjectFromFactory(factory, typeName);
			}
			else {
				return configuration.getPlaceholder(eClass);
			}
		}
		else {
			return super.createObjectFromFactory(factory, typeName);
		}
	}
}