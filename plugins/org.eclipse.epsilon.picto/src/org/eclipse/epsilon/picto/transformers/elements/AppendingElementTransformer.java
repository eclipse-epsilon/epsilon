/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers.elements;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Sina Madani
 * @since 2.2
 */
public abstract class AppendingElementTransformer extends AbstractHtmlElementTransformer {

	@Override
	public final void transform(Element element) throws Exception {
		append(element, element.getOwnerDocument());
	}

	protected abstract void append(Element root, Document document) throws DOMException;
}
