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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.diagram.ui.tools.internal.resource.NavigationMarkerConstants;
import org.eclipse.sirius.ui.business.api.session.SessionEditorInput;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

@SuppressWarnings("restriction")
public class SiriusMarkerResolver extends EmfMarkerResolver {
	
	@Override
	public boolean canResolve(IMarker marker) {
		try {
			return marker.getType().equals("org.eclipse.sirius.diagram.ui.diagnostic");
		} catch (CoreException e) {
			return false;
		}
	}
	
	protected Session getSession(IMarker marker) {
		try {
			IEditorPart editor = IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), marker);
			
			if (editor != null) {
	            IEditorInput editorInput = editor.getEditorInput();
	            if (editorInput instanceof SessionEditorInput) {
	                Session editorInputSession = ((SessionEditorInput) editorInput).getSession();
	                if (editorInputSession != null && editorInputSession.isOpen()) {
	                    return editorInputSession;
	                }
	            }
			}
		}
		catch (Exception ex) {
			LogUtil.log(ex);
		}
		return null;
	}
	
	@Override
	public EObject resolve(IMarker marker) {
		return getSemanticElement(getMarkedView(getSession(marker), marker));		
	}
	
	@Override
	public EditingDomain getEditingDomain(IMarker marker) {
		return getSession(marker).getTransactionalEditingDomain();
	}
	
	private View getMarkedView(Session session, IMarker marker) {
        String elementID = marker.getAttribute(org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID, null);
        String diagramURI = marker.getAttribute("DIAGRAM_DESCRIPTOR_URI", null);
        
        if (diagramURI == null || elementID == null) {
            return null;
        }

        ResourceSet set = session.getTransactionalEditingDomain().getResourceSet();
        if (set != null) {
            EObject markedDiagram = set.getEObject(URI.createURI(diagramURI), true);
            EObject markerTarget = markedDiagram.eResource().getEObject(elementID);
            if (markerTarget instanceof View) {
                return (View) markerTarget;
            }
        }
        return null;
    }
	
	private EObject getSemanticElement(View markedView) {
        EObject fixTarget = markedView.getElement();
        if (fixTarget instanceof DSemanticDecorator) {
            fixTarget = ((DSemanticDecorator) fixTarget).getTarget();
        }
        return fixTarget;
    }
	
	@Override
	public String getAbsoluteElementId(IMarker marker) {
		return marker.getAttribute(NavigationMarkerConstants.SEMANTIC_URI, "");
	}
	
}
