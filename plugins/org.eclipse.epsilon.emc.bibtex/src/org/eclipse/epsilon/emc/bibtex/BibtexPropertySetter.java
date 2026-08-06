/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.bibtex;

import org.eclipse.epsilon.emc.bibtex.domain.Publication;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;

/**
 * 
 * @since 1.6
 */
public class BibtexPropertySetter extends AbstractPropertySetter {

	@Override
	public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {
		((Publication)object).setProperty(property, value);
	}
}
