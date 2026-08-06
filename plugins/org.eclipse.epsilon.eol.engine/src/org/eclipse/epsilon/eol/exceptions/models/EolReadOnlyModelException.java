/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.exceptions.models;

import org.eclipse.epsilon.eol.exceptions.EolReadOnlyPropertyException;

/**
 * 
 * @author Sina Madani
 * @since 2.2
 */
public class EolReadOnlyModelException extends EolReadOnlyPropertyException {

	public EolReadOnlyModelException() {
		setMessage("Model does not support modifications");
	}

}
