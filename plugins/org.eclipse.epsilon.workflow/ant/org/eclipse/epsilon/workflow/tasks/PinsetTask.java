/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.pinset.PinsetModule;

public class PinsetTask extends ExecutableModuleTask {

	protected File outputFolder;

	@Override
	protected void initialize() throws Exception {
	}

	@Override
	protected void examine() throws Exception {
	}

	@Override
	protected IEolModule createDefaultModule() throws Exception {
		return new PinsetModule();
	}

	@Override
	protected void configureModule() throws EolModelNotFoundException, BuildException, EolModelLoadingException {
		super.configureModule();

		if (outputFolder != null) {
			((PinsetModule) module).setOutputFolder(outputFolder.getAbsolutePath());
		}
	}

	public File getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}
}
