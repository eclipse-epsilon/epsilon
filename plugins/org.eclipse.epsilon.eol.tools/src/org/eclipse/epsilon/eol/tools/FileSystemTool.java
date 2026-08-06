/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.tools;

import java.io.File;

import org.eclipse.epsilon.common.util.FileUtil;

public class FileSystemTool {
	
	public boolean exists(String path) {
		return toFile(path).exists();
	}
	
	public boolean isDirectory(String path) {
		return toFile(path).isDirectory();
	}
	
	public String getContents(String path) throws Exception {
		return FileUtil.getFileContents(toFile(path));
	}

	private File toFile(String path) {
		return new File(path);
	}
}
