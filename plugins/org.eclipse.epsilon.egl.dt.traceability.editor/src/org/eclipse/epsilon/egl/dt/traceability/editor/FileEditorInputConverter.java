/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.editor;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PlatformUI;

class FileEditorInputConverter {

	private final TextLinkModel textlinkModel;
	
	public FileEditorInputConverter(TextLinkModel textlinkModel) throws EolModelLoadingException {
		this.textlinkModel = textlinkModel;
	}
	
	public TextLinkEditorInput convert() throws EolRuntimeException {
		final List<String> editorIDs = new LinkedList<>();
		final List<IEditorInput> editorInputs = new LinkedList<>();
		
		for (Resource source : textlinkModel.getSources()) {
			if (source == null) continue;
			final IFile sourceFile = resourceToFile(source);
			editorIDs.add(getModelEditorIdFor(sourceFile));
			editorInputs.add(TextLinkInnerEditorInput.createEditorInputForSource(sourceFile, source));
		}
		
		for (String destination : textlinkModel.getDestinations()) {
			final IFile destinationFile = resourceToFile(destination);
			editorIDs.add(getTextEditorIdFor(destinationFile));
			editorInputs.add(TextLinkInnerEditorInput.createEditorInputForDestination(destinationFile, destination));
		}
				
		return new TextLinkEditorInput(editorIDs, editorInputs, textlinkModel);
	}
	
	private IFile resourceToFile(Resource resource) {
		final URI uri = resource.getURI();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		if (uri.isPlatformResource()) {
			final String path = uri.toPlatformString(true);
			 return (IFile)root.findMember(path);
			
		} else if (uri.toFileString() != null) {
			return root.getFileForLocation(new Path(uri.toFileString()));
		}
		
		return null;
	}
	
	private IFile resourceToFile(String path) {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		// Resolve path from the .textlink model folder
		final File textlinkFolder = textlinkModel.getFile().getParentFile();
		final String resolvedPath = textlinkFolder.toPath().resolve(path)
			.toAbsolutePath().toString();

		final IFile file = root.getFileForLocation(new Path(resolvedPath));
		return file;
	}
	
	private String getModelEditorIdFor(IFile file) {
		return getEditorIdFor(file, "org.eclipse.epsilon.dt.exeed.ExeedEditor");
	}
	
	private String getEditorIdFor(IFile file, String defaultEditorId) {
		final IEditorRegistry editorRegistry = PlatformUI.getWorkbench().getEditorRegistry();
		final IEditorDescriptor editor = editorRegistry.getDefaultEditor(file.getName());
		
		return editor == null ? defaultEditorId : editor.getId();
	}
	
	private String getTextEditorIdFor(IFile file) {
		return "org.eclipse.ui.DefaultTextEditor"; // Force DefaultTextEditor so that hyperlink detector is available
	}
}
