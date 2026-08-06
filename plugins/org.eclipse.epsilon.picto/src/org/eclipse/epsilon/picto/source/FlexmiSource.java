/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.source;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.flexmi.EObjectLocation;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import org.eclipse.epsilon.flexmi.dt.FlexmiEditor;
import org.eclipse.epsilon.picto.dom.Picto;
import org.eclipse.epsilon.picto.dom.PictoFactory;
import org.eclipse.ui.IEditorPart;

public class FlexmiSource extends EglPictoSource {

	@Override
	public Picto getRenderingMetadata(IEditorPart editorPart) {
		FlexmiResource resource = (FlexmiResource) getResource(editorPart);
		if (resource == null) return null;
		
		return resource
			.getProcessingInstructions().stream()
			.filter(p -> p.getTarget().startsWith("render-"))
			.findFirst()
			.map(renderProcessingInstruction -> {
				Picto metadata = PictoFactory.eINSTANCE.createPicto();
				metadata.setFormat(renderProcessingInstruction.getTarget().substring("render-".length()));
				metadata.setTransformation(renderProcessingInstruction.getData().trim());
				return metadata;
			})
			.orElse(null);
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {	
		IPath iPath = waitForPath(editorPart);
		if (iPath == null) return null;
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new FlexmiResourceFactory());
		Resource resource = resourceSet.createResource(URI.createFileURI(iPath.toOSString()));
		try {
			resource.load(null);
			return resource;
		} catch (IOException e) {
			LogUtil.log(e);
		}
		return null;
	}

	@Override
	public boolean supportsEditorType(IEditorPart editorPart) {
		return editorPart instanceof FlexmiEditor;
	}

	@Override
	public IFile getFile(IEditorPart editorPart) {
		return ((FlexmiEditor) editorPart).getFile();
	}
	
	@Override
	public void showElement(String id, String uri, IEditorPart editor) {
		EObject eObject = getResource(editor).getEObject(id);
		FlexmiResource resource = (FlexmiResource) eObject.eResource();
		EObjectLocation location = resource.getEObjectTraceManager().getLine(eObject);
		
		try {
			EclipseUtil.openEditorAt(new File(new java.net.URI(location.getUri().toString())), location.getLine(), 0, false);
		} catch (URISyntaxException e) {
			LogUtil.log(e);
		}
		
	}
	
}
