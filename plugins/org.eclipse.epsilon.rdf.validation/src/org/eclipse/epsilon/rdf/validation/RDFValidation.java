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

import java.util.Iterator;

import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.ValidityReport.Report;
import org.apache.jena.ontology.OntModel;

public class RDFValidation {
	
	public enum ValidationMode {
		NONE("none") {
			@Override
			public RDFModelValidationReport validate(OntModel modelToValidate) {
				// Do nothing return true so as not to throw an exception fault
				return new RDFModelValidationReport(true, "Validation set to NONE.");
			}
		},
		JENA_VALID("jena-valid") {
			@Override
			public RDFModelValidationReport validate(OntModel modelToValidate) {
				ValidityReport jenaValidationReport = modelToValidate.validate(); // Calls Jena's Validation API
				String reportText = getJenaValidityModelString(jenaValidationReport);
				if (jenaValidationReport.isValid() && jenaValidationReport.getReports().hasNext()) {
					System.err.println(reportText); // Send warning messages
				}
				return new RDFModelValidationReport(jenaValidationReport.isValid(), reportText);
			}
		},
		JENA_CLEAN("jena-clean") {
			@Override
			public RDFModelValidationReport validate(OntModel modelToValidate) {
				ValidityReport jenaValidationReport = modelToValidate.validate(); // Calls Jena's Validation API
				return new RDFModelValidationReport(jenaValidationReport.isClean(),
						getJenaValidityModelString(jenaValidationReport));
			}
		};

		public abstract RDFModelValidationReport validate(OntModel modelToValidate);

		public class RDFModelValidationReport {
			public RDFModelValidationReport(boolean isValid, String text) {
				super();
				this.text = text;
				this.isValid = isValid;
			}

			private final String text;
			private final boolean isValid;

			public boolean isValid() {
				return isValid;
			}

			public String getText() {
				return text;
			}
		}

		private final String id;

		ValidationMode(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public final static ValidationMode fromString(String newId) {
			for (ValidationMode mode : ValidationMode.values()) {
				if (mode.id.equalsIgnoreCase(newId)) {
					return mode;
				}
			}
			throw new IllegalArgumentException("Validation mode not found: " + newId);
		}

		// Format Jena's Validation report to a String
		protected String getJenaValidityModelString(ValidityReport modelValidityReport) {
			StringBuilder sb = new StringBuilder("The loaded model is ");

			if (!modelValidityReport.isValid()) {
				sb.append("not ");
			}
			sb.append("valid");

			if (this.equals(ValidationMode.JENA_CLEAN)) {
				sb.append(" and ");
				if (!modelValidityReport.isClean()) {
					sb.append("not ");
				}
				sb.append("clean");
			}

			sb.append("\n");

			// Build report string (valid models still report warnings)
			int i = 1;
			for (Iterator<Report> o = modelValidityReport.getReports(); o.hasNext();) {
				ValidityReport.Report report = (ValidityReport.Report) o.next();
				sb.append(String.format("%d. %s", i, report.toString()));
				i++;
			}
			return sb.toString();
		}
	}
}
