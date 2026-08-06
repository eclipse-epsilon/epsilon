/*******************************************************************************
 * Copyright (c) 2016 Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.examples.testlang.dt.wizard;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;
import org.eclipse.ui.INewWizard;

public class NewTestLangWizard extends AbstractNewFileWizard2 implements INewWizard {

	@Override
	public String getTitle() {
		return "New TestLang Program";
	}

	@Override
	public String getExtension() {
		return "eolt";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new script with *.eolt extension.";
	}

}
