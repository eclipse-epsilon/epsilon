/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
/*******************************************************************************
 * Copyright (c) 2012 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.debug;

import java.util.Comparator;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.epsilon.common.dt.util.LogUtil;

/**
 * Compares IVariable instances using their names.
 */
final class IVariableNameComparator implements Comparator<IVariable> {
	@Override
	public int compare(IVariable o1, IVariable o2) {
		try {
			return o1.getName().compareTo(o2.getName());
		} catch (DebugException e) {
			LogUtil.log(e);
			return 0;
		}
	}
}
