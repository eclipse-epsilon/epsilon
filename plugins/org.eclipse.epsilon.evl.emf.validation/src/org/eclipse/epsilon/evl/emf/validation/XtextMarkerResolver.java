/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.emf.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.evl.execute.FixInstance;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

public class XtextMarkerResolver extends EmfMarkerResolver {
	
	@Override
	public boolean canResolve(IMarker marker) {
		try {
			return marker.getAttribute("URI_KEY") != null;
		} catch (CoreException e) {
			LogUtil.log(e);
			return false;
		}
	}
	
	@Override
	public String getAbsoluteElementId(IMarker marker) {
		return marker.getAttribute("URI_KEY", "");
	}
	
	@Override
	public void run(IMarker marker, EvlMarkerResolution resolution) {
		final String filePath = getElementResourceLocation(marker);
		
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
		String editorId = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(filePath).getId();
				
		IEditorPart part = null;
		
		try {
			part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(file), editorId, false);
		} catch (PartInitException e) {
			LogUtil.log(e);
			return;
		}
		
		if (part instanceof XtextEditor) {
			((XtextEditor) part).getDocument().modify(new IUnitOfWork.Void<XtextResource>() {
				@Override
				public void process(XtextResource state) throws Exception {
					
					Resource resource = state;
					
					Object self = resource.getEObject(getRelativeElementId(marker));
					InMemoryEmfModel model = new InMemoryEmfModel(resolution.getModelName(), resource, resolution.getePackageUri());
					
					FixInstance fix = resolution.getFix();
					try {
						fix.setSelf(self);
						fix.getContext().getModelRepository().addModel(model);
						fix.perform();
						marker.delete();			
					} catch (Exception e) {
						LogUtil.log(e);
					} finally {
						fix.getContext().getModelRepository().removeModel(model);
						model.dispose();
					}
				}
			});
		}
	}
	
}
