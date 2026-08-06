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

public class AbsoluteLinkElementTransformer extends AbsolutePathElementTransformer {
	
	public AbsoluteLinkElementTransformer(String elementName, String attributeName) {
		super(elementName, attributeName);
	}
	
	@Override
	protected boolean isValidAttribute(String attributeValue) {
		// An HTML element with an id can be used as a link
		return super.isValidAttribute(attributeValue) && !attributeValue.startsWith("#");
	}

}
