/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.types;

import org.eclipse.epsilon.eol.execute.introspection.IUndefined;

/**
 * Represents <code>null</code> which can be used by the engine to represent the absence
 * of a variable, useful for the isDefined() operation.
 *
 * @author Sina Madani
 * @since 1.6
 */
public final class EolUndefined extends EolAny implements IUndefined {
	
	private EolUndefined() {}
	
	public static final EolUndefined INSTANCE = new EolUndefined();
	
	public boolean isDefined() {
		return false;
	}
	
	public boolean isUndefined() {
		return true;
	}
}
