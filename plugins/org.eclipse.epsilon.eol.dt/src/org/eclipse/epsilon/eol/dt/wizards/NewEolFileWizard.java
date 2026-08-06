/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.wizards;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;

public class NewEolFileWizard extends AbstractNewFileWizard2 {

	@Override
	public String getTitle() {
		return "New EOL Program";
	}

	@Override
	public String getExtension() {
		return "eol";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new EOL program file with *.eol extension";
	}
	
	/*
	@Override
	public ImageDescriptor getImageDescriptor() {
		return EolPlugin.getImageDescriptor("icons/eol.big.gif");
	}
	*/
}
