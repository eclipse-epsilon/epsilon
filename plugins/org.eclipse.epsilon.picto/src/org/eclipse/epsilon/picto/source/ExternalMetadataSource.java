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

import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import org.eclipse.epsilon.picto.ViewTree;
import org.eclipse.epsilon.picto.dom.Picto;
import org.eclipse.epsilon.picto.dom.PictoFactory;
import org.eclipse.ui.IEditorPart;

public abstract class ExternalMetadataSource extends EglPictoSource {

	@Override
	public Picto getRenderingMetadata(IEditorPart editorPart) {
		IFile file = getFile(editorPart);
		if (file == null) return null;
		IFile renderingMetadataFile = file.getParent().getFile(Path.fromPortableString(file.getName() + ".picto"));
		return getRenderingMetadata(renderingMetadataFile);
	}
	
	public Picto getRenderingMetadata(IFile file) {
		if (file.exists()) {
			Picto picto = getFromFlexmi(file);
			if (picto == null) {
				picto = getFromProperties(file);
			}
			return picto;
		}
		return null;
	}
	
	@Override
	public ViewTree getViewTree(IEditorPart editor) throws Exception {
		ViewTree viewTree = super.getViewTree(editor);
		java.net.URI editorLocationURI = getFile(editor).getLocationURI();
		viewTree.getBaseUris().add(editorLocationURI);
		viewTree.getBaseUris().add(editorLocationURI.resolve("./icons/"));
		return viewTree;
	}
	
	protected Picto getFromProperties(IFile file) {
		Properties properties = new Properties();
		try {
			properties.load(file.getContents(true));
			Picto picto = PictoFactory.eINSTANCE.createPicto();
			picto.setFormat(properties.getProperty("format", "text"));
			picto.setTransformation(properties.getProperty("file"));
			if (properties.contains("template")) picto.setTransformation(properties.getProperty("template"));
			return picto;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	protected Picto getFromFlexmi(IFile file) {
		try {
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new FlexmiResourceFactory());
			Resource resource = resourceSet.getResource(URI.createFileURI(file.getLocation().toOSString()), true);
			resource.load(null);
			return (Picto) resource.getContents().iterator().next();
		}
		catch (Exception ex) {
			return null;
		}
	}

}
