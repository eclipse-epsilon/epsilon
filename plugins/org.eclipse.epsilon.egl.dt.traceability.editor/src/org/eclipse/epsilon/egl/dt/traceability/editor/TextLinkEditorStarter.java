/*******************************************************************************
 * Copyright (c) 2008-2022 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - fix issues when opening an already opened file
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.editor;


import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextlinkPackage;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfModelFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

// FIXME duplication with ModelLinkStarter

public class TextLinkEditorStarter extends EditorPart {

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		startTraceAwareEditor();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {

	}

	protected void startTraceAwareEditor() throws PartInitException {
		final TextLinkEditorStarter starter = this;
		
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				EmfModel textlinkModel = null;
				
				final IWorkbenchPage page = getSite().getPage();
				page.closeEditor(starter, false);
				try {
					final FileEditorInput editorInput = (FileEditorInput)starter.getEditorInput();
					textlinkModel = EmfModelFactory.getInstance().loadEmfModel("Textlink",
					                                                           new File(editorInput.getPath().toOSString()),
					                                                           TextlinkPackage.eINSTANCE);
					
					final TextLinkEditorInput convertedInput = new FileEditorInputConverter(new TextLinkModel(textlinkModel, new File(editorInput.getPath().toOSString()))).convert();
					IEditorPart existingEditor = page.findEditor(convertedInput);
					if (existingEditor == null) {
						page.openEditor(convertedInput, "org.eclipse.epsilon.egl.dt.traceability.editor.EglTraceAwareEditor");
					} else {
						/*
						 * This is already open: unload the .textlink model so we do not end up caching
						 * an old copy forever.
						 */
						textlinkModel.dispose();
						page.activate(existingEditor);
					}

				} catch (Exception e) {
					if (textlinkModel != null) textlinkModel.dispose();
					throw new IllegalArgumentException("Error encountered whilst loading TextLink model at: " + starter.getEditorInput(), e);
				}
			}
		});
	}

	@Override
	public void setFocus() {

	}

}
