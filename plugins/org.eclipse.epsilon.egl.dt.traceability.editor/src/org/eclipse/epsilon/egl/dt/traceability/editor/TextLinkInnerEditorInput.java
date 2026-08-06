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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.EmfModelLocation;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation;
import org.eclipse.ui.part.FileEditorInput;

public abstract class TextLinkInnerEditorInput<T> extends FileEditorInput {

	private final boolean isSource;
	protected final T resource;
	
	private TextLinkInnerEditorInput(IFile file, boolean isSource, T resource) {
		super(file);
		this.isSource = isSource;
		this.resource = resource;
	}

	public static TextLinkInnerModelEditorInput createEditorInputForSource(IFile file, Resource resource) {
		return new TextLinkInnerModelEditorInput(file, resource);
	}
	
	public static TextLinkInnerTextEditorInput createEditorInputForDestination(IFile file, String destination) {
		return new TextLinkInnerTextEditorInput(file, destination);
	}
	
	public boolean isSource() {
		return isSource;
	}
	
	public abstract boolean contains(Object o);
	
	
	private static class TextLinkInnerModelEditorInput extends TextLinkInnerEditorInput<Resource> {
		
		private TextLinkInnerModelEditorInput(IFile file, Resource resource) {
			super(file, true, resource);
		}
		
		@Override
		public boolean contains(Object o) {
			if (!(o instanceof EmfModelLocation))
				return false;
			
			final EmfModelLocation location = (EmfModelLocation)o;
			
			return resource.getURI().equals(location.getModelElement().eResource().getURI());
		}
	}
	
	private static class TextLinkInnerTextEditorInput extends TextLinkInnerEditorInput<String> {
		
		private TextLinkInnerTextEditorInput(IFile file, String resource) {
			super(file, false, resource);
		}
		
		@Override
		public boolean contains(Object o) {
			if (!(o instanceof TextLocation))
				return false;
			
			final TextLocation destination = (TextLocation)o;
			
			return resource != null && resource.equals(destination.getResource());
		}
	}
}
