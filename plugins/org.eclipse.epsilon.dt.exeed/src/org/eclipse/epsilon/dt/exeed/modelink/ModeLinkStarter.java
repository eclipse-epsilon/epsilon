/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio García-Domínguez
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - use ModeLink instead of SAX parsing, honor the isForceExeed flags
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.epsilon.dt.exeed.ExeedPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class ModeLinkStarter extends EditorPart {

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		startModeLinkEditor();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing
	}

	@Override
	public void doSaveAs() {
		// do nothing
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
		// do nothing
	}

	@Override
	public void setFocus() {
		// do nothing
	}

	private void startModeLinkEditor() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = getSite().getPage();
				page.closeEditor(ModeLinkStarter.this, false);
				try {
					page.openEditor(
						new FileEditorInputToModeLinkEditorInputConverter()
							.convert((FileEditorInput) ModeLinkStarter.this.getEditorInput()),
						ExeedPlugin.MODELINK_EDITOR_ID);
				} catch (PartInitException e) {
					ExeedPlugin.getDefault().getLog().log(new Status(
						IStatus.ERROR, ExeedPlugin.PLUGIN_ID,
						"Error while starting the ModeLink editor", e));
				}
			}
		});
	}

	private class FileEditorInputToModeLinkEditorInputConverter {
		public ModeLinkEditorInput convert(FileEditorInput input) {
			IEditorInput[] inputs = null;
			String[] editorIds = null;
			boolean threeWay = true;

			try {
				final ModeLink ml = new ModeLink();
				ml.load(input.getFile());
				threeWay = ml.isThreeWay();

				final int size = ml.getLinkedModels().size();
				inputs = new IEditorInput[size];
				editorIds = new String[size];

				for (int i = 0; i < size; ++i) {
					final LinkedModel linkedModel = ml.getLinkedModels().get(i);
					final String path = linkedModel.getPath();
					final ModelPosition pos = ml.getLinkedModels().get(i).getPosition();

					final IEditorInput editorInput = new ModeLinkInnerEditorInput(
						ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path)), pos);
					final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));

					String editorId = ExeedPlugin.EXEED_EDITOR_ID;
					if (!ml.isForceExeed(pos)) {
						final IEditorDescriptor editorDescriptor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(path, IDE.getContentType(file));
						if (editorDescriptor != null)
							editorId = editorDescriptor.getId();
					}

					editorIds[i] = editorId;
					inputs[i] = editorInput;
				}
			} catch (Exception ex) {
				ExeedPlugin.getDefault().getLog().log(new Status(
					IStatus.ERROR, ExeedPlugin.PLUGIN_ID, "Error while reading the ModeLink configuratio", ex));
			}

			ModeLinkEditorInput modeLinkEditorInput = new ModeLinkEditorInput(
					editorIds, inputs, (FileEditorInput) getEditorInput());
			modeLinkEditorInput.setThreeWay(threeWay);
			return modeLinkEditorInput;
		}
	}

}
