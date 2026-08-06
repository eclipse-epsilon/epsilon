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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.picto.ViewContent;
import org.eclipse.epsilon.picto.ViewTree;
import org.w3c.dom.Element;

public class PictoViewElementTransformer extends ReplacingElementTransformer {
	
	@Override
	public String getXPath() {
		return "//*[local-name() = 'picto-view']";
	}
	
	@Override
	public void transform(Element element) {
		String path = element.getAttribute("path");
		if (path != null) {
			ViewTree viewTree = picto.getViewTree().forPath(
				Arrays.stream(("," + path).split(","))
					.map(String::trim)
					.collect(Collectors.toList())
			);
			if (viewTree != null) {

				List<ViewContent> contents = viewTree.getContents(picto);

				ViewContent svgContent = contents.stream()
					.filter(c -> c.getFormat().equals("svg"))
					.findAny().orElse(null);
				
				if (svgContent != null) {
					replace(element, svgContent);
				}
				else {
					replace(element, contents.get(contents.size() - 1), false);
				}
			}
		}
		
	}
	
	
}