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
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;

/**
 * 
 * @since 1.6
 */
public class BibtexPropertyGetter extends AbstractPropertyGetter {

	@Override
	public boolean hasProperty(Object object, String property, IEolContext context) {
		return "id".equals(property) || ((Publication)object).hasProperty(property);
	}

	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		final Publication publication = (Publication)object;
		return "id".equals(property) ? publication.id : publication.getProperty(property);
	}
}
