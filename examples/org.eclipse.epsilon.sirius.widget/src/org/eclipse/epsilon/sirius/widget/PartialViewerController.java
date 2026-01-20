/*********************************************************************
* Copyright (c) 2021 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.sirius.widget;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.eef.EEFCustomWidgetDescription;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.controllers.AbstractEEFCustomWidgetController;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;

public class PartialViewerController extends AbstractEEFCustomWidgetController {

	private IEmbeddedWidget widget;

	public PartialViewerController(EEFCustomWidgetDescription description, IVariableManager variableManager,
			IInterpreter interpreter, EditingContextAdapter contextAdapter, IEmbeddedWidget widget) {
		super(description, variableManager, interpreter, contextAdapter);
		this.widget = widget;
	}

	@Override
	protected EEFCustomWidgetDescription getDescription() {
		return this.description;
	}

	public void load() {
		widget.load();
	}

	public IStatus saveText(final String text) {
		return this.editingContextAdapter.performModelChange(() -> {
			widget.save(text);
		});
	}
}
