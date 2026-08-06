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

import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;
import org.w3c.dom.Element;

public interface HtmlElementTransformer {
	
	public String getXPath();
	
	public void transform(Element element) throws Exception;
	
	public void setPictoView(PictoView picto);
	
	public void setViewContent(ViewContent viewContent);
	
}