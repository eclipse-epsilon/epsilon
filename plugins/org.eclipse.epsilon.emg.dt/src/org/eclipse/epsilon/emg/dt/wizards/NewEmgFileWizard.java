/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Saheed Popoola - initial API and implementation
 *     Horacio Hoyos - aditional functionality
 ******************************************************************************/
package org.eclipse.epsilon.emg.dt.wizards;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;
public class NewEmgFileWizard extends AbstractNewFileWizard2{

	@Override
	public String getTitle() {
		return "New EMG Program";
	}

	@Override
	public String getExtension() {
		return "emg";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new EMG program file with *.emg extension";
	}

}
