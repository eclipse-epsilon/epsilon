/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.dt.wizards;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;

/**
 * NewPinsetFileWizard.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class NewPinsetFileWizard extends AbstractNewFileWizard2 {

	@Override
	public String getTitle() {
		return "New Pinset file";
	}

	@Override
	public String getExtension() {
		return "pinset";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new Pinset file with *.pinset extension.";
	}

}
