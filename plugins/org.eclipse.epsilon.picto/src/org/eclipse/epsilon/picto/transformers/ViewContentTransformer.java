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

import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;

public interface ViewContentTransformer {
	
	boolean canTransform(ViewContent content);
	
	String getLabel(ViewContent content);
	
	ViewContent transform(ViewContent content, PictoView pictoView) throws Exception;

}
