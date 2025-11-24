/********************************************************************************
 * Copyright (c) 2024 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.validation;

import org.eclipse.epsilon.rdf.validation.RDFValidation.ValidationMode.RDFModelValidationReport;


public class RDFValidationException extends RuntimeException {

	private static final long serialVersionUID = -542046827985505503L;
	
	private final RDFModelValidationReport report;
	
	public RDFModelValidationReport getReport() {
		return report;
	}

	public RDFValidationException(String errorMessage) {
		super(errorMessage);
		this.report = null;
	}

	public RDFValidationException(String errorMessage, RDFModelValidationReport report) {
		super(errorMessage);
		this.report = report;
	}
}
