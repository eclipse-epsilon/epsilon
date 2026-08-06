/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt.ui;

import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.jface.action.Action;

abstract class OpenEOLEditorAction extends Action {
	public OpenEOLEditorAction() {
		super("Open in EOL editor");
	}

	protected void openInEOLEditor(ModuleElement astNode) {
		EclipseUtil.openEditorAt(astNode);
	}
}
