/*******************************************************************************
 * Copyright (c) 2018 Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.dt.epackageregistryexplorer;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.epsilon.emc.emf.CachedResourceSet.Cache.CacheItem;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CacheItemDialog extends TitleAreaDialog {

	private static final class StackTraceLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			StackTraceElement[] stackTrace = (StackTraceElement[]) element;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			for (StackTraceElement elem : stackTrace) {
				pw.println(elem.toString());
			}
			return sw.toString();
		}
	}

	private final CacheItem cItem;

	public CacheItemDialog(Shell shell, CacheItem cItem) {
		super(shell);
		this.cItem = cItem;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);

		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout containerLayout = new GridLayout();
		container.setLayout(containerLayout);
		containerLayout.numColumns = 1;

		final Label lblCheckedIn = new Label(container, SWT.NONE);
		lblCheckedIn.setText("Checked in from:");
		
		final ListViewer lstCheckedIn = new ListViewer(container);
		lstCheckedIn.setContentProvider(new ArrayContentProvider());
		lstCheckedIn.setLabelProvider(new StackTraceLabelProvider());
		lstCheckedIn.setInput(cItem.getCheckedInFrom().toArray());

		final Label lblCheckedOut = new Label(container, SWT.NONE);
		lblCheckedOut.setText("Checked out from:");
		final ListViewer lstCheckedOut = new ListViewer(container);
		lstCheckedOut.setContentProvider(new ArrayContentProvider());
		lstCheckedOut.setLabelProvider(new StackTraceLabelProvider());
		lstCheckedOut.setInput(cItem.getCheckedOutFrom().toArray());

		return area;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Cache item details");
		setMessage("Check where the cache item was acquired and released from");
	}

}
