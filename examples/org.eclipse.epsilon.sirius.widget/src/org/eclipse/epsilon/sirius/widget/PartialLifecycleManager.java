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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.eef.EEFContainerDescription;
import org.eclipse.eef.EEFCustomWidgetDescription;
import org.eclipse.eef.EEFWidgetDescription;
import org.eclipse.eef.common.ui.api.IEEFFormContainer;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.controllers.IEEFWidgetController;
import org.eclipse.eef.ide.ui.api.widgets.AbstractEEFWidgetLifecycleManager;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class PartialLifecycleManager extends AbstractEEFWidgetLifecycleManager {

	private EEFCustomWidgetDescription description;

	private PartialViewerController controller;
	private IEmbeddedWidget widget;
	private static final String IWIDGET_ID = "org.eclipse.epsilon.sirius.widget";
	private String languageName;

	public PartialLifecycleManager(EEFCustomWidgetDescription controlDescription, IVariableManager variableManager,
			IInterpreter interpreter, EditingContextAdapter contextAdapter) {
		super(variableManager, interpreter, contextAdapter);
		this.description = controlDescription;
	}

	@Override
	protected void createMainControl(Composite parent, IEEFFormContainer formContainer) {
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(IWIDGET_ID);

		if (description.eContainer() instanceof EEFContainerDescription) {
			languageName = ((EEFContainerDescription) description.eContainer()).getIdentifier();
		}

		for (IConfigurationElement e : config) {
			try {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IEmbeddedWidget) {
					widget = (IEmbeddedWidget) o;
					if (widget.getLanguageName().equals(languageName)) {
						widget.setVariableManager(variableManager);
						widget.setEditingContextAdapter(editingContextAdapter);
						widget.createControl(parent);
						break;
					}
				}
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}

		this.controller = new PartialViewerController(description, variableManager, interpreter, editingContextAdapter,
				widget);

	}

	@Override
	public void aboutToBeShown() {
		super.aboutToBeShown();
		controller.load();
	}

	@Override
	protected IEEFWidgetController getController() {
		return this.controller;
	}

	@Override
	protected EEFWidgetDescription getWidgetDescription() {
		return this.description;
	}

	@Override
	protected Control getValidationControl() {
		return widget.getControl();
	}

	@Override
	public void dispose() {
		if (widget != null) {
			controller.saveText(widget.getText());
			widget.dispose();
		}
		super.dispose();
	}

	@Override
	protected void setEnabled(boolean enabled) {
		this.widget.setEnabled(enabled);
	}

}
