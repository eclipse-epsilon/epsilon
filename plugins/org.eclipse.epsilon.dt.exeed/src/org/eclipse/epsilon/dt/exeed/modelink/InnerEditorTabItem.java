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

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.IEditorPart;

public class InnerEditorTabItem extends CTabItem {
	
	protected IEditorPart editor;
	
	public InnerEditorTabItem(CTabFolder parent, int style, IEditorPart editor) {
		super(parent, style);
		this.editor = editor;
	}

	public IEditorPart getEditor() {
		return editor;
	}
	
}
