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

import java.util.HashMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorPart;

public class GmfMarkerResolver extends EmfMarkerResolver {

	@Override
	public boolean canResolve(IMarker marker) {
		return getEditingDomain(marker)!= null && !super.canResolve(marker);
	}

	@Override
	public String getAbsoluteElementId(IMarker marker) {
		EObject resolved = resolve(marker);
		return "platform:/resource" + resolved.eResource().getURI().toPlatformString(true) + "#" + resolved.eResource().getURIFragment(resolved);
	}
	
	public HashMap<IEditorPart, EditingDomain> domains = new HashMap<>();
	
	@Override
	public EditingDomain getEditingDomain(IEditorPart editor) {
		
		EditingDomain domain = null;
		
		if (domains.get(editor) != null) {
			domain = domains.get(editor);
		}
		else {
			if (editor instanceof DiagramEditor) {
				domain = ((DiagramEditor) editor).getDiagramEditPart().getEditingDomain();
				domains.put(editor, domain);
			}
			else {
				domain = super.getEditingDomain(editor);
			}
		}
		return domain;
	}

	@Override
	public EditingDomain getEditingDomain(IMarker marker) {
		return super.getEditingDomain(marker);
	}

	@Override
	public String getElementResourceLocation(IMarker marker) {
		return marker.getResource().getFullPath().toString();
	}

	@Override
	public String getMessage(IMarker marker) {
		// TODO Auto-generated method stub
		return super.getMessage(marker);
	}

	@Override
	public String getRelativeElementId(IMarker marker) {
		EObject resolved = resolve(marker);
		return resolved.eResource().getURIFragment(resolved);
	}

	@Override
	public EObject resolve(IMarker marker) {
		Object view = null;
		String viewId = marker.getAttribute("elementId", "");
		
		for (Resource resource : getEditingDomain(marker).getResourceSet().getResources()) {
			if ((view = resource.getEObject(viewId)) != null)
				break;
		}
		
		if (view instanceof View) {
			return ((View) view).getElement();
		}
		else {
			return null;
		}
	}
}
