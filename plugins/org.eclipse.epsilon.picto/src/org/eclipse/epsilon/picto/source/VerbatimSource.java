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

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.epsilon.picto.ViewTree;
import org.eclipse.ui.IEditorPart;

public abstract class VerbatimSource extends SimpleSource {
	
	@Override
	public ViewTree getViewTree(IEditorPart editor) throws Exception {
		IPath iPath = waitForPath(editor);
		if (iPath == null) return createEmptyViewTree();
		else {
			// If the filename ends with .picto.svg, treat the file as an EGL template
			if (iPath.toOSString().endsWith(".picto." + getFileExtension())) return super.getViewTree(editor);
			// else use its verbatim contents
			else return new ViewTree(new File(iPath.toOSString()), getFormat());
		}
	}
	
}
