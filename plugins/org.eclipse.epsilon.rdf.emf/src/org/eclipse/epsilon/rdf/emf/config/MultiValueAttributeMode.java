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

public enum MultiValueAttributeMode {
	LIST("List") {
		@Override
		public boolean useLists() {
			return true;
		}
		
	}, CONTAINER("Container") {
		@Override
		public boolean useLists() {
			return false;
		}
	};

	private final String id;

	public String getId() {
		return id;
	}

	MultiValueAttributeMode(String value) {
		this.id = value;
	}

	public abstract boolean useLists();

	public static MultiValueAttributeMode fromValue(String value) {
		for (MultiValueAttributeMode mode : values()) {
			if (mode.id.equalsIgnoreCase(value)) {
				return mode;
			}
		}
		return null; // or throw an exception if not found
	}

}