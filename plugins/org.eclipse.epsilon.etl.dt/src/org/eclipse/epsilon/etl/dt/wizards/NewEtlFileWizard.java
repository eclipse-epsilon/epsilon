/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.dt.wizards;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;

public class NewEtlFileWizard extends AbstractNewFileWizard2 {

	@Override
	public String getTitle() {
		return "New ETL Transformation";
	}

	@Override
	public String getExtension() {
		return "etl";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new ETL transformation file with *.etl extension";
	}
	
	/*
	public ImageDescriptor getImageDescriptor() {
		return EtlPlugin.getImageDescriptor("icons/etl.gif");
	}
	*/
}
