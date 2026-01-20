/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio Garc??a-Dom??nguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garc??a-Dom??nguez - fix DND in Eclipse Juno
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;

public class ExeedEditingDomainViewerDropAdapter extends EditingDomainViewerDropAdapter implements DisposeListener {

	protected Image setReferenceValueImage = null;
	protected Image addReferenceValueImage = null;
	
	public ExeedEditingDomainViewerDropAdapter(EditingDomain domain, Viewer viewer, ExeedPlugin plugin) {
		super(domain, viewer);
		addReferenceValueImage = plugin.getImageDescriptor("icons/add.gif").createImage();
		setReferenceValueImage = plugin.getImageDescriptor("icons/set.gif").createImage();
	}

	@Override
	public void dragOver(DropTargetEvent event) {
		super.dragOver(event);
		if (event.detail == DND.DROP_NONE && event.getSource() !=null && !listQualifiedReferences(event).isEmpty()) {
			event.detail = DND.DROP_LINK;
			event.feedback = getAutoFeedback() | DND.FEEDBACK_SELECT;
		}
	}
	
	// Works on Windows - see bug 288616
	@Override
	public void dropAccept(DropTargetEvent event) {
		super.dropAccept(event);
		handleDrop(event);
	}
	
	// Works on Mac/Linux - see bug 288616
	@Override
	public void drop(DropTargetEvent event) {
		super.drop(event);
		handleDrop(event);
	}
	
	public void handleDrop(final DropTargetEvent event) {
		source = getDragSource(event);
		if (source == null) return;
		final List<EReference> qualifiedReferences = listQualifiedReferences(event);
		if (!qualifiedReferences.isEmpty()) {
			Menu m = new Menu(viewer.getControl());

			for (EReference reference : qualifiedReferences) {
				final Command command;
				Image image = null;

				if (reference.isMany()) {
					command = new AddReferenceValuesCommand(
							(EObject) event.item.getData(), source, reference);
					image = addReferenceValueImage;
				} else {
					command = new SetReferenceValueCommand(
							(EObject) event.item.getData(), (EObject) source
									.iterator().next(), reference);
					image = setReferenceValueImage;
				}

				MenuItem mi = new MenuItem(m, SWT.PUSH);
				mi.setText(command.getLabel());
				mi.setImage(image);
				mi.addListener(SWT.Selection, event1 -> domain.getCommandStack().execute(command));
			}

			final Display display = Display.getCurrent();
			m.setLocation(event.x, event.y);
			m.setVisible(true);
			while (!m.isDisposed () && m.isVisible ()) {
				if (!display.readAndDispatch ()) display.sleep ();
			}
			m.dispose ();
			qualifiedReferences.clear();
			source = null;
		}
	}

	private List<EReference> listQualifiedReferences(DropTargetEvent event) {
		final List<EReference> qualifiedReferences = new ArrayList<>();
	
		TreeItem item = (TreeItem) event.item;
		if (item == null || !(item.getData() instanceof EObject)) {
			return qualifiedReferences;
		}

		EObject targetEObject = (EObject) item.getData();
		Iterator<EReference> rit = targetEObject.eClass().getEAllReferences().iterator();

		while (rit.hasNext()) {
			final EReference ref = rit.next();
			if (!ref.isMany() && source.size() > 1) continue;
			if (ref.isContainment()) continue; // Containment references are handled by the reflective editor
			
			boolean areInstancesOf = true;
			final Iterator<?> it = source.iterator();
			final EClassifier eType = ref.getEType();
			while (it.hasNext() && areInstancesOf) {
				Object next = it.next();
				areInstancesOf = eType.isInstance(next);
			}
			if (areInstancesOf) {
				qualifiedReferences.add(ref);
			}
		}

		return qualifiedReferences;
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		if (setReferenceValueImage != null) setReferenceValueImage.dispose();
		if (addReferenceValueImage != null) addReferenceValueImage.dispose();
	}

}
