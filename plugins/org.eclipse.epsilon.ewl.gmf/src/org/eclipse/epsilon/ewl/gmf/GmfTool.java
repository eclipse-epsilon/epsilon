/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.gmf;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.epsilon.eol.tools.AbstractTool;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.EditPartService;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class GmfTool extends AbstractTool{
	
	
	public EditPart createEditPart(EObject eObject) {
		return EditPartService.getInstance().createEditPart(
			getEditPart(eObject.eContainer()), null);
	}
	
	public Collection<EditPart> getEditParts(EObject eObject) {
		IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (part instanceof DiagramEditor) {
			DiagramEditor editor = (DiagramEditor) part;
			ConnectionEditPart a;
			
			//editor.getDiagramGraphicalViewer().
			//a.getConnectionFigure().repaint();
			
			//a.addNotify()
			
			return editor.getDiagramGraphicalViewer().findEditPartsForElement(getElementId(eObject), IGraphicalEditPart.class);
		}
		return Collections.emptyList();
	}
	
	public EditPart getEditPart(EObject eObject) {
		Iterator<EditPart> it = getEditParts(eObject).iterator();
		if (it.hasNext()) return it.next();
		else return null;
	}
	
	protected String getElementId(Object instance) {
		EObject eObject = (EObject) instance;
		
		if (eObject.eResource() instanceof XMIResource){
			String id = ((XMIResource) eObject.eResource()).getID(eObject);
			if (id != null && id.trim().length() > 0) return id;
		}
		
		if (eObject.eResource() instanceof XMLResource){
			return ((XMLResource) eObject.eResource()).getURIFragment(eObject);
		}
		
		return "";
	}
	
}
