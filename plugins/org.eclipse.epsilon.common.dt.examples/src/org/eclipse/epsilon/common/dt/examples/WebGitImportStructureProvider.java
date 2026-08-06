/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.examples;

import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

public class WebGitImportStructureProvider implements IImportStructureProvider {
	
	@Override
	public List<?> getChildren(Object element) {
		if (element instanceof WebGitFolder) {
			try {
				return ((WebGitFolder) element).getChildren();
			} catch (Exception e) {
				LogUtil.log(e);
			}
		}
		
		return Collections.emptyList();
		
	}

	@Override
	public InputStream getContents(Object element) {
		if (element.getClass() == WebGitFile.class) {
			WebGitFile file = (WebGitFile) element;
			try {
				return new URL(file.getServer() + file.getUrl()).openConnection().getInputStream();
			} catch (Exception e) {
				LogUtil.log(e);
			}
		}
		return null;
	}

	@Override
	public String getFullPath(Object element) {
		return ((WebGitFile) element).getRelativePath();
	}

	@Override
	public String getLabel(Object element) {
		return ((WebGitFile) element).getName();
	}

	@Override
	public boolean isFolder(Object element) {
		return element instanceof WebGitFolder;
	}

}
