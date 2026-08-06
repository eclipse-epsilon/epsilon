/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class ResourceFieldEditor extends StringButtonFieldEditor{
	
	protected String browseTitle;
	protected String browseMessage;
	protected String browseFilter;
	
	public ResourceFieldEditor(String name, String labelText, String browseTitle, String browseMessage, String browseFilter, Composite parent) {
		super(name, labelText, parent);
		this.browseFilter = browseFilter;
		this.browseMessage = browseMessage;
		this.browseTitle = browseTitle;
		setChangeButtonText("Browse workspace...");
	}

	@Override
	protected String changePressed() {
		IFile file = BrowseWorkspaceUtil.browseFile(getShell(), browseTitle, browseMessage, browseFilter, null);
		if (file != null) {
			return file.getFullPath().toOSString();
		}
		else {
			return null;
		}
		
	}
	
	
	
}
