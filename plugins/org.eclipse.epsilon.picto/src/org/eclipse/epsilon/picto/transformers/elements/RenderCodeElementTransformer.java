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

import org.eclipse.epsilon.picto.ViewContent;
import org.w3c.dom.Element;

public class RenderCodeElementTransformer extends ReplacingElementTransformer {
	
	@Override
	public String getXPath() {
		return "//code[starts-with(@class,'language-')]";
	}
	
	@Override
	public void transform(Element element) {
		
		String text = element.getChildNodes().item(0).getTextContent();
		String format = element.getAttribute("class").substring("language-".length());
		String render = "render-";
		if (format.startsWith(render)) {
			format = format.substring(render.length());
		}
		ViewContent lastContent = null;
		boolean iframe = true;

		for (ViewContent viewContent = new ViewContent(format, text); viewContent != null; viewContent = viewContent.getNext(picto)) {
			lastContent = viewContent;
			if (viewContent.isImage()) {
				iframe = false;
				break;
			}
		}
		
		if (element.getParentNode() != null && element.getParentNode().getNodeName().equalsIgnoreCase("pre")) {
			element = (Element) element.getParentNode();
		}
		
		replace(element, lastContent, iframe);
	}
}