/*********************************************************************
* Copyright (c) 2021 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.sirius.widget.xtext;

import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.inject.Injector;

public interface IEmbeddedXtextWidget {
	
	public Injector getLanguageInjector();
	
	public EmbeddedResourceProvider getResourceProvider();
	
	public ResourceSet getSiriusResourceSet();
	
}
