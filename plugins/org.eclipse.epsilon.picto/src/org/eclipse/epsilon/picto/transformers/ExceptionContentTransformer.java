/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers;

import java.util.Collections;

import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;

public class ExceptionContentTransformer implements ViewContentTransformer {

	@Override
	public boolean canTransform(ViewContent content) {
		return content.getFormat().equalsIgnoreCase("exception");
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) {
		return new ViewContent("html", pictoView.getViewRenderer().getVerbatim(content.getText()), content);
	}
	
	public ViewContent getViewContent(Exception ex, PictoView pictoView) {
		return transform(new ViewContent("exception", ex.getMessage(), null, Collections.emptyList(), Collections.emptyList(), Collections.emptySet()), pictoView);
	}
	
	@Override
	public String getLabel(ViewContent content) {
		return "Exception";
	}
	
}
