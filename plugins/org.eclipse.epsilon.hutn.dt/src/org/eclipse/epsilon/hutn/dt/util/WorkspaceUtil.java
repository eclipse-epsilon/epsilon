/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.util;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

public abstract class WorkspaceUtil {

	private WorkspaceUtil() {}
	
	/**
	 * Returns a java.io.file for a workspace resource.
	 * 
	 * @param file 	A resource in the workspace.
	 */
	public static File getAbsolutePath(IResource resource) {
		final File workspace = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile();
		return new File(workspace, resource.getFullPath().toPortableString());
	}
}
