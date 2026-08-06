/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.editor;

import java.util.ResourceBundle;

import org.eclipse.epsilon.emf.dt.EmfRegistryManager;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

public class HutnEditor extends TextEditor {
		 
	private final HutnSourceViewerConfiguration configuration = new HutnSourceViewerConfiguration(this);
	
	public HutnEditor() {
		setSourceViewerConfiguration(configuration);
		setDocumentProvider(new HutnDocumentProvider());
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		
		// Force registration of meta-models
		EmfRegistryManager.getInstance();
		
		// Detect keywords and syntax highlight document
		configuration.reconcile(getDocument());
	}
	
	private IDocument getDocument() {
		return getEditorInput() == null ? null : getDocumentProvider().getDocument(getEditorInput());
	}
	
	
	@Override
	protected void createActions() {
		super.createActions();
		createContentAssistanceAction();
	}
	
	
	private void createContentAssistanceAction() {
		final ResourceBundle resourceBundle = ResourceBundle.getBundle("org.eclipse.epsilon.hutn.dt.editor.ContentAssistance");
		
		final Action action = new ContentAssistAction(resourceBundle, "ContentAssistProposal.", this); 
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction("ContentAssistProposal", action); 
		markAsStateDependentAction("ContentAssistProposal", true);
	}
}
