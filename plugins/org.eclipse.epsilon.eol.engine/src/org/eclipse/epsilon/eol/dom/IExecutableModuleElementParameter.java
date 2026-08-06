/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

/**
 * Executable ModuleElement which can be invoked with a parameter.
 * 
 * @author Sina Madani
 * @since 1.6
 * @see {@link IExecutableModuleElement}
 */
public interface IExecutableModuleElementParameter extends ModuleElement {
	
	Object execute(IEolContext context, Object parameter) throws EolRuntimeException;
	
}
