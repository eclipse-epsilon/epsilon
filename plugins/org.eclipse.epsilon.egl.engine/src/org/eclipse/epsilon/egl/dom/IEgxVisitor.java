/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.dom;

import org.eclipse.epsilon.erl.dom.IErlVisitor;

public interface IEgxVisitor extends IErlVisitor {

	public void visit(GenerationRule rule);
	
}
