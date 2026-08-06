/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.exceptions;

import org.eclipse.epsilon.common.module.ModuleElement;

/**
 * 
 * @author Sina Madani
 * @since 2.1
 */
public class EolNullPointerException extends EolRuntimeException {

	public EolNullPointerException(String featureName, ModuleElement ast) {
		super("Called feature '" + featureName + "' on undefined object", ast);
	}
}
