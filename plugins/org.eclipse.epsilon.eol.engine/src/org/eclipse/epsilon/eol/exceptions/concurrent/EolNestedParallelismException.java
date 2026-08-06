/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.exceptions.concurrent;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EolNestedParallelismException extends EolRuntimeException {

	public EolNestedParallelismException() {
		this((ModuleElement) null);
	}
	
	public EolNestedParallelismException(String message) {
		this.reason = message;
	}

	public EolNestedParallelismException(ModuleElement ast) {
		this.ast = ast;
		this.reason = "Illegal nesting of parallel operations!";
	}
}
