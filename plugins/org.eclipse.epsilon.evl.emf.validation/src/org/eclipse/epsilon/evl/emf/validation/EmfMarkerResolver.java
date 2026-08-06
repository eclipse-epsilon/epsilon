/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.emf.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.evl.execute.FixInstance;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class EmfMarkerResolver implements IEvlMarkerResolver {

	@Override
	public boolean canResolve(IMarker marker) {
		try {
			String eCoreDiagnosticMarker = "org.eclipse.emf.ecore.diagnostic";
			return marker.getType().equals(eCoreDiagnosticMarker) ||
				marker.isSubtypeOf(eCoreDiagnosticMarker);
		} catch (CoreException e) {
			return false;
		}
	}

	@Override
	public EObject resolve(IMarker marker) {
		EObject self = null;
		ResourceSet resourceSet = getEditingDomain(marker).getResourceSet();
		for (Resource resource : resourceSet.getResources()) {
			EObject temp = resource.getEObject(getRelativeElementId(marker));
			if (temp != null) {
				self = temp;
				break;
			}
		}
		return self;
	}
	
	@Override
	public EditingDomain getEditingDomain(IMarker marker) {	
		final String filePath = getElementResourceLocation(marker);
		
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
		String editorId = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(filePath).getId();
				
		IEditorPart part = null;
		
		try {
			part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(file), editorId, false);
		} catch (PartInitException e1) {
			return null;
		}
		
		return getEditingDomain(part);

	}
	
	public EditingDomain getEditingDomain(IEditorPart editor) {
		if (editor instanceof IEditingDomainProvider) {
			return ((IEditingDomainProvider) editor).getEditingDomain();
		}
		else {
			return null;
		}		
	}
	
    protected String getElementResourceLocation(IMarker marker) {
           final String location = getAbsoluteElementId(marker).split("#")[0];

           URI uri = URI.createURI(location);     
           if (uri.isPlatform()) {
                  return uri.toPlatformString(true);
           } else {
                  return location;
           }
    }
	
	public String getRelativeElementId(IMarker marker) {
		String[] parts = getAbsoluteElementId(marker).split("#");
		return parts[1];
	}
	
	@Override
	public void run(IMarker marker, EvlMarkerResolution resolution) {
		EObject self = EvlMarkerResolverManager.INSTANCE.resolve(marker); //getEObject(elementId);
		
		Resource resource = self.eResource();
		InMemoryEmfModel model = new InMemoryEmfModel(resolution.getModelName(), resource, resolution.getePackageUri());
		
		FixInstance fix = resolution.getFix();
		try {
			fix.setSelf(self);
			fix.getContext().getModelRepository().addModel(model);
			EvlMarkerResolverManager.INSTANCE.getEditingDomain(marker).getCommandStack().execute(new ExecuteEvlFixCommand(fix, model));
			
			// 286126 - save resource so that any open GMF diagram editors are automatically refreshed
			// see also: http://dev.eclipse.org/newslists/news.eclipse.modeling.gmf/msg04508.html
			//self.eResource().save(Collections.EMPTY_MAP);
			resource.setModified(true);
			marker.delete();			
		} catch (Exception e) {
			LogUtil.log(e);
		} finally {
			fix.getContext().getModelRepository().removeModel(model);
			model.dispose();
		}
	}
	
	@Override
	public String getAbsoluteElementId(IMarker marker) {
		return marker.getAttribute("uri", "");
	}

	@Override
	public String getMessage(IMarker marker) {
		return marker.getAttribute("message", "");
	}
	
}
