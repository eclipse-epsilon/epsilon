/*******************************************************************************
 * Copyright (c) 2013 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.editor;

import org.eclipse.epsilon.common.module.IModuleValidator;

public abstract class AbstractModuleValidator implements IModuleValidator {

	@Override
	public String getMarkerType() {
		return AbstractModuleEditor.PROBLEM_MARKER;
	}
}
