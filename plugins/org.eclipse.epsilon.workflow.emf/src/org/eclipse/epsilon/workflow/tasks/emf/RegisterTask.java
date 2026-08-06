/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.emf;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emf.dt.EmfRegistryManager;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;

public class RegisterTask extends EpsilonTask {
	
	protected File file;
	protected boolean permanently = false;
	
	@Override
	public void executeImpl() throws BuildException {
		ResourceFactoryRegistryManager.configure();
		if (!file.exists()) {
			fail("File " + file.getAbsolutePath() + " doesn't exist", null);
		}
		
		try {
			EmfUtil.register(file, EPackage.Registry.INSTANCE);
			if (permanently) {
			    // Map the absolute path to this file to an IFile
			    IFile[] workspaceFiles = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(file.toURI());
			    if (workspaceFiles.length == 0) {
			    	fail("File " + file.getAbsolutePath() + " is not available in the current workspace", null);
			    }
			    else {
			    	// getFullPath() returns the workspace relative path
			    	// addMetamodel needs (as it uses URI#createPlatformResourceURI)
			    	EmfRegistryManager.getInstance().addMetamodel(workspaceFiles[0].getFullPath().toString());
			    }
			}
		}
		catch (Exception e) {
			fail(e.getMessage(), e);
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isPermanently() {
		return permanently;
	}

	public void setPermanently(boolean permanently) {
		this.permanently = permanently;
	}
}
