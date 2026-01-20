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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class EmbeddedResourceProvider implements IEditedResourceProvider {

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	private ResourceSet siriusResourceSet;
	
	public ResourceSet getSiriusResourceSet() {
		return siriusResourceSet;
	}

	public void setSiriusResourceSet(ResourceSet resourceSet) {
		this.siriusResourceSet = resourceSet;
	}
	
	@Override
	public XtextResource createResource() {
		URI uri = URI.createURI("_synthetic." + fileExtensionProvider.getPrimaryFileExtension());
		XtextResource xtextVirtualResource = (XtextResource) siriusResourceSet.createResource(uri);
		siriusResourceSet.getResources().add(xtextVirtualResource);
		return xtextVirtualResource;
	}

}
