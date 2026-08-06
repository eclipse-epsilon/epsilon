/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.workflow.tasks.emf;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;

/**
 * Standalone implementation of the epsilon.emf.register
 * ANT task that does not support permanent metamodel
 * registration and does not depend on emf.dt.
 * We use this class in tasks.xml and the other 
 * RegisterTask class from the src folder in plugin.xml
 */
public class RegisterTaskStandalone extends EpsilonTask {
	
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
			    warn("Permanent registration is not supported in the standalone mode");
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
