/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at:
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.epsilon.emc.rdf.emf.dt;

import org.eclipse.epsilon.emc.emf.dt.EmfModelConfigurationDialog;

public class RdfEmfModelConfigurationDialog extends EmfModelConfigurationDialog {

	@Override
	protected String getModelName() {
		return "RDF-based EMF model";
	}

	@Override
	protected String getModelType() {
		return "RDF-EMF";
	}

	@Override
	protected String getModelExtension() {
		return "rdfres";
	}
	
}
