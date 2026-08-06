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

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiEditorInput;

public class ModeLinkEditorInput extends MultiEditorInput {

	protected FileEditorInput originalInput;
	protected boolean threeWay = true;
	
	public ModeLinkEditorInput(String[] editorIDs, IEditorInput[] innerEditors, FileEditorInput originalInput) {
		super(editorIDs, innerEditors);
		this.originalInput = originalInput;
	}

	public FileEditorInput getOriginalInput() {
		return originalInput;
	}

	public void setOriginalInput(FileEditorInput originalInput) {
		this.originalInput = originalInput;
	}

	public boolean isThreeWay() {
		return threeWay;
	}

	public void setThreeWay(boolean threeWay) {
		this.threeWay = threeWay;
	}

	@Override
	public String getName() {
		return originalInput.getName();
	}
	
}
