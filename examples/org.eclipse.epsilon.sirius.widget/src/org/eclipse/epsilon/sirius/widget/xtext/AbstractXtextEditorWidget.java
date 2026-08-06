/*********************************************************************
* Copyright (c) 2021 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.sirius.widget.xtext;

import org.eclipse.epsilon.sirius.widget.AbstractEmbeddedWidget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;

import com.google.inject.Injector;

@SuppressWarnings("restriction")
public abstract class AbstractXtextEditorWidget extends AbstractEmbeddedWidget implements IEmbeddedXtextWidget {

	private EmbeddedEditorModelAccess access;
	private EmbeddedEditor editor;

	@Override
	public void createControl(Composite parent) {
		Injector xtextInjector = getLanguageInjector();
		EmbeddedResourceProvider resProvider = xtextInjector.getInstance(EmbeddedResourceProvider.class);
		resProvider.setSiriusResourceSet(getSiriusResourceSet());
		EmbeddedEditorFactory factory = xtextInjector.getInstance(EmbeddedEditorFactory.class);
		editor = factory.newEditor(resProvider).showErrorAndWarningAnnotations().withParent(parent);
		access = editor.createPartialEditor();
		Control control = editor.getViewer().getControl();
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.heightHint = 14 * 12;
		gridData.widthHint = 300;
		gridData.horizontalIndent = 5;
		control.setLayoutData(gridData);
	}

	@Override
	public Control getControl() {
		return editor.getViewer().getControl();
	}

	@Override
	public String getText() {
		return access.getEditablePart();
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.editor.getViewer().setEditable(enabled);
	}

	@Override
	public void dispose() {
		editor.getViewer().getControl().dispose();
	}
}
