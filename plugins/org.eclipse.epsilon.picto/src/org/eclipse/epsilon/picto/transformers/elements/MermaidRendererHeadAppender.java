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
public class MermaidRendererHeadAppender extends AppendingElementTransformer {

	@Override
	public String getXPath() {
		return "//head[1]";
	}
	
	@Override
	protected void append(Element root, Document document) throws DOMException {
		String cdn = "https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js";
		
		Element include = document.createElement("script");
		include.setAttribute("src", cdn);
		root.appendChild(include);
		
		Element init = document.createElement("script");
		init.setTextContent("mermaid.initialize({startOnLoad:true});");
		root.appendChild(init);
	}

}
