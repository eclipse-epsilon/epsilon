/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.source;

import org.eclipse.epsilon.picto.dom.Picto;
import org.eclipse.ui.IEditorPart;

public class StandalonePictoSource extends SimpleSource {

	@Override
	public String getFormat() {
		return null;
	}

	@Override
	public String getFileExtension() {
		return "picto";
	}
	
	@Override
	public Picto getRenderingMetadata(IEditorPart editorPart) {
		return new EditingDomainProviderSource().getRenderingMetadata(getFile(editorPart));
	}
	
	@Override
	public boolean supports(IEditorPart editorPart) {
		if (!super.supports(editorPart)) return false;
		Picto picto = new EditingDomainProviderSource().getRenderingMetadata(getFile(editorPart));
		return picto != null && (picto.isStandalone() || !picto.getModels().isEmpty());
	}
}
