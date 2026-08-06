/********************************************************************************
 * Copyright (c) 2025 University of York
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
package org.eclipse.epsilon.rdf.emf.config;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.rdf.validation.RDFValidation.ValidationMode;

public class RDFResourceConfiguration {

	private List<String> dataModels = new ArrayList<>();
	private List<String> schemaModels = new ArrayList<>();

	private String validationMode = ValidationMode.NONE.getId();
	private String multiValueAttributeMode = MultiValueAttributeMode.CONTAINER.getId();
	private String defaultModelNamespace;

	public String getDefaultModelNamespace() {
		return defaultModelNamespace;
	}

	public void setDefaultModelNamespace(String defaultModelNamespace) {
		this.defaultModelNamespace = defaultModelNamespace;
	}

	public String getMultiValueAttributeMode() {
		return multiValueAttributeMode;
	}

	public void setMultiValueAttributeMode(String multiValueAttributeMode) {
		this.multiValueAttributeMode = multiValueAttributeMode;
	}

	public MultiValueAttributeMode getRawMultiValueAttributeMode() {
		return MultiValueAttributeMode.fromValue(multiValueAttributeMode);
	}

	public List<String> getDataModels() {
		return dataModels;
	}

	public void setDataModels(List<String> dataModels) {
		this.dataModels = dataModels;
	}

	public List<String> getSchemaModels() {
		return schemaModels;
	}

	public void setSchemaModels(List<String> schemaModels) {
		this.schemaModels = schemaModels;
	}

	public String getValidationMode() {
		return validationMode;
	}
	
	public ValidationMode getRawValidationMode() {
		return ValidationMode.fromString(validationMode);
	}

	public void setValidationMode(String validationMode) {
		this.validationMode = validationMode;
	}
	
	@Override
	public String toString() {
		return "RDFResourceConfiguration" +
				" [ " +
				"dataModels=" + dataModels + 
				", schemaModels=" + schemaModels +
				", validationMode=" + validationMode + 
				", multiValueAttributeMode=" + multiValueAttributeMode +
				" ]";
	}



}
