/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.editor;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class SelectTextDestinationDialogue {

	private final List<TextLocation> candidates;
	private final ISelectedTextDestinationHandler handler;
	private final Shell dialogue;
	private final org.eclipse.swt.widgets.List list;
	private final Button ok;

	public static void promptForSelectionFrom(Shell parent, List<TextLocation> candidates, ISelectedTextDestinationHandler handler) {
		new SelectTextDestinationDialogue(parent, candidates, handler);
	}
	
	public static interface ISelectedTextDestinationHandler {
		public void reactToSelectionOf(TextLocation destination);
	}
	
	private SelectTextDestinationDialogue(Shell parent, final List<TextLocation> candidates, ISelectedTextDestinationHandler handler) {
		this.candidates = candidates;
		this.handler = handler;
		
		dialogue = createDialogue(parent);
		
		list = createListOfCandidateDestinations(dialogue);
		
		ok = createButton(dialogue, "Ok");
		createButton(dialogue, "Cancel");
				
		dialogue.setDefaultButton(ok);
		dialogue.pack();
		dialogue.open();
	}

	private Shell createDialogue(Shell parent) {
		final Shell dialogue = new Shell(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		dialogue.setText("Navigate");
		dialogue.setMinimumSize(400, 100);
		dialogue.setLayout(new GridLayout(2, false));
		return dialogue;
	}

	private org.eclipse.swt.widgets.List createListOfCandidateDestinations(Composite parent) {
		final Group listboxFrame = new Group(parent, SWT.SHADOW_ETCHED_IN);
		listboxFrame.setText("Select a destination:");
		listboxFrame.setLayout(new GridLayout(1, false));
		
		final GridData listboxFrameGridData = new GridData();
		listboxFrameGridData.horizontalSpan = 2;
		listboxFrameGridData.horizontalAlignment = SWT.FILL;
		listboxFrameGridData.grabExcessHorizontalSpace = true;
		listboxFrame.setLayoutData(listboxFrameGridData);
		
		
		final org.eclipse.swt.widgets.List listOfCandidateDestinations = new org.eclipse.swt.widgets.List(listboxFrame, SWT.NONE);
		final GridData listboxGridData = new GridData();
		listboxGridData.horizontalAlignment = SWT.FILL;
		listboxGridData.grabExcessHorizontalSpace = true;
		listOfCandidateDestinations.setLayoutData(listboxGridData);
		
		for (TextLocation destination : candidates) {
			listOfCandidateDestinations.add(labelFor(destination));
		}
		listOfCandidateDestinations.select(0);
		
		return listOfCandidateDestinations;
	}

	private String labelFor(TextLocation destination) {
		return resourceLabelFor(destination) + "@" + destination.getRegion().getOffset();
	}

	private String resourceLabelFor(TextLocation destination) {
		final IFile resourceInWorkspace = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(Path.fromOSString(destination.getResource()));
		
		if (resourceInWorkspace == null) {
			return destination.getResource().substring(destination.getResource().lastIndexOf(File.separator) + 1);
		} else {
			return resourceInWorkspace.getProjectRelativePath().toOSString();
		}
	}

	private Button createButton(Composite parent, String label) {
		final Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.addSelectionListener(new ButtonSelectionListener());
		return button;
	}
	
	private void selectAndRevealCurrentDestination() {
		handler.reactToSelectionOf(candidates.get(list.getSelectionIndex()));
	}
	
	private class ButtonSelectionListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (ok == e.getSource()) selectAndRevealCurrentDestination();
			dialogue.close();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
		
	}
}
