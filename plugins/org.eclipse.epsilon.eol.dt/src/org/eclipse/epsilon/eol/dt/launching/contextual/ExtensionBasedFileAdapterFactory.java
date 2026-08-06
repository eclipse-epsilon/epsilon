/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.launching.contextual;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;

public abstract class ExtensionBasedFileAdapterFactory implements IAdapterFactory {

	protected boolean supportsExtension(String extension) {
		return getExtension().equals(extension);
	}
	
	protected abstract String getExtension();

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof IFile) {
			IFile file = ((IFile) adaptableObject);
			if (supportsExtension(file.getFileExtension())) {
				return (T) new EolFile();
			}
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return null;
	}
	
}
