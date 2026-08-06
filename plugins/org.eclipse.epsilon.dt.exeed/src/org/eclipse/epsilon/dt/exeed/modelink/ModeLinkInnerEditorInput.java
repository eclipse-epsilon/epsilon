/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

public class ModeLinkInnerEditorInput extends FileEditorInput {

	protected ModelPosition position;
	
	public ModeLinkInnerEditorInput(IFile file, ModelPosition position) {
		super(file);
		setPosition(position);
	}
	
	public ModelPosition getPosition() {
		return position;
	}

	public void setPosition(ModelPosition position) {
		this.position = position;
	}

}
