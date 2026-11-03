/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.annotations;

import java.net.URI;

import org.eclipse.epsilon.coverage.dt.CoverageFiles;
import org.eclipse.epsilon.coverage.dt.CoverageSessionManager;
import org.eclipse.epsilon.coverage.dt.ICoverageSessionListener;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Attaches {@link CoverageAnnotationModel}s to the open text editors whose files
 * are part of the active coverage session (works with any text editor, including
 * all Epsilon editors), and keeps them up to date as editors are opened and as the
 * active session changes. Must be used from the UI thread.
 */
public class CoverageAnnotationManager implements ICoverageSessionListener {
	
	private static final CoverageAnnotationManager INSTANCE = new CoverageAnnotationManager();
	
	protected boolean started = false;
	
	public static CoverageAnnotationManager getInstance() {
		return INSTANCE;
	}
	
	protected final IPartListener2 partListener = new IPartListener2() {
		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			update(partRef);
		}
		
		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
			update(partRef);
		}
		
		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			update(partRef);
		}
	};
	
	protected final IWindowListener windowListener = new IWindowListener() {
		@Override
		public void windowOpened(IWorkbenchWindow window) {
			window.getPartService().addPartListener(partListener);
		}
		
		@Override
		public void windowClosed(IWorkbenchWindow window) {
			window.getPartService().removePartListener(partListener);
		}
		
		@Override
		public void windowActivated(IWorkbenchWindow window) {}
		
		@Override
		public void windowDeactivated(IWorkbenchWindow window) {}
	};
	
	public synchronized void start() {
		if (started || !PlatformUI.isWorkbenchRunning()) return;
		started = true;
		PlatformUI.getWorkbench().addWindowListener(windowListener);
		for (IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			window.getPartService().addPartListener(partListener);
		}
		CoverageSessionManager.getInstance().addListener(this);
		updateAll();
	}
	
	protected void update(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof IEditorPart) update((IEditorPart) part);
	}
	
	public void updateAll() {
		for (IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			for (IWorkbenchPage page : window.getPages()) {
				for (IEditorReference reference : page.getEditorReferences()) {
					IEditorPart editor = reference.getEditor(false);
					if (editor != null) update(editor);
				}
			}
		}
	}
	
	protected void update(IEditorPart editorPart) {
		ITextEditor editor = editorPart instanceof ITextEditor ? (ITextEditor) editorPart : editorPart.getAdapter(ITextEditor.class);
		if (editor == null) return;
		
		IEditorInput input = editor.getEditorInput();
		IDocumentProvider provider = editor.getDocumentProvider();
		if (input == null || provider == null) return;
		IAnnotationModel model = provider.getAnnotationModel(input);
		if (!(model instanceof IAnnotationModelExtension)) return;
		IAnnotationModelExtension extension = (IAnnotationModelExtension) model;
		
		SourceFile sourceFile = CoverageFiles.getSourceFile(CoverageSessionManager.getInstance().getActiveSession(), getUri(input));
		
		IAnnotationModel existing = extension.getAnnotationModel(CoverageAnnotationModel.KEY);
		if (existing instanceof CoverageAnnotationModel && ((CoverageAnnotationModel) existing).getSourceFile() == sourceFile) {
			return;
		}
		if (existing != null) {
			extension.removeAnnotationModel(CoverageAnnotationModel.KEY);
		}
		if (sourceFile != null) {
			extension.addAnnotationModel(CoverageAnnotationModel.KEY, new CoverageAnnotationModel(sourceFile));
		}
	}
	
	protected URI getUri(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			return ((IFileEditorInput) input).getFile().getLocationURI();
		}
		else if (input instanceof IURIEditorInput) {
			return ((IURIEditorInput) input).getURI();
		}
		return null;
	}
	
	@Override
	public void sessionsChanged() {}
	
	@Override
	public void activeSessionChanged(CoverageSession session) {
		updateAll();
	}
}
