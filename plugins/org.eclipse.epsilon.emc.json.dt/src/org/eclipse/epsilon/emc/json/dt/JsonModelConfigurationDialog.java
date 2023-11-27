/*******************************************************************************
 * Copyright (c) 2012-2023 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - adaptation to JSON
 *     Antonio Garcia-Dominguez - minor refinements
 ******************************************************************************/
package org.eclipse.epsilon.emc.json.dt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.emc.json.JsonModel;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class JsonModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {

	@Override
	protected String getModelName() {
		return "JSON Document";
	}

	@Override
	protected String getModelType() {
		return "JSON";
	}
	
	protected Label fileTextLabel;
	protected Text fileText;
	protected Button browseModelFile;
	protected Button filebasedButton;

	protected Label uriTextLabel;
	protected Text uriText;
	protected TableViewer headersTable;

	public static class Header {
		public String name, value;

		public Header() { /* empty */ }

		public Header(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}
	
	protected List<Header> headers = new ArrayList<>();
	private Button btnAddHeader;
	private Button btnRemoveHeader;
	private Button btnClearHeaders;

	@Override
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createFilesGroup(control);
		createLoadStoreOptionsGroup(control);
		toggleEnabledFields();
	}
	
	protected void toggleEnabledFields() {
		final boolean isFileBased = filebasedButton.getSelection();

		// File-related fields
		fileTextLabel.setEnabled(isFileBased);
		fileText.setEnabled(isFileBased);
		storeOnDisposalCheckbox.setEnabled(isFileBased);
		if (!isFileBased) {
			fileText.setText("");
			storeOnDisposalCheckbox.setSelection(false);
		}

		// URI-related fields
		uriTextLabel.setEnabled(!isFileBased);
		uriText.setEnabled(!isFileBased);
		headersTable.getTable().setEnabled(!isFileBased);
		btnAddHeader.setEnabled(!isFileBased);
		btnRemoveHeader.setEnabled(!isFileBased);
		btnClearHeaders.setEnabled(!isFileBased);

		if (isFileBased) {
			uriText.setText("");
			headers.clear();
			headersTable.refresh();
		}
	}

	protected Composite createFilesGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "Files/URIs", 3);

		filebasedButton = new Button(groupContent, SWT.CHECK);
		GridData filebasedButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
		filebasedButtonGridData.horizontalSpan = 3;
		filebasedButton.setSelection(true);
		filebasedButton.setText("Workspace file");
		filebasedButton.setLayoutData(filebasedButtonGridData);
		filebasedButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				toggleEnabledFields();
			}
		});
		
		fileTextLabel = new Label(groupContent, SWT.NONE);
		fileTextLabel.setText("File: ");
		
		fileText = new Text(groupContent, SWT.BORDER);
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		browseModelFile = new Button(groupContent, SWT.NONE);
		browseModelFile.setText("Browse Workspace...");
		browseModelFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(fileText, "XML Documents in the workspace", "Select an XML document"));

		uriTextLabel = new Label(groupContent, SWT.NONE);
		uriTextLabel.setText("URI: ");
		
		uriText = new Text(groupContent, SWT.BORDER);
		GridData uriTextGridData = new GridData(GridData.FILL_HORIZONTAL);
		uriTextGridData.horizontalSpan = 2;
		uriText.setLayoutData(uriTextGridData);

		createHeadersTable(groupContent);

		groupContent.layout();
		groupContent.pack();
		return groupContent;
	}

	private void createHeadersTable(final Composite groupContent) {
		headersTable = new TableViewer(groupContent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);

		GridData headersTableGridData = new GridData(GridData.FILL_BOTH);
		headersTableGridData.horizontalSpan = 2;
		headersTable.getTable().setLayoutData(headersTableGridData);
		headersTable.getTable().setLinesVisible(true);
		headersTable.getTable().setHeaderVisible(true);

		createTableColumns();
		createHeadersTableButtons(groupContent);

		headersTable.setContentProvider(ArrayContentProvider.getInstance());
		headersTable.setInput(headers);
	}

	@SuppressWarnings("unchecked")
	private void createHeadersTableButtons(final Composite groupContent) {
		Composite headerButtons = new Composite(groupContent, SWT.NONE);
		final FillLayout headerButtonsLayout = new FillLayout(SWT.VERTICAL);
		headerButtons.setLayout(headerButtonsLayout);
		headerButtonsLayout.spacing = 4;
		GridData headerButtonsGridData = new GridData(GridData.FILL_HORIZONTAL);
		headerButtonsGridData.horizontalSpan = 1;
		headerButtons.setLayoutData(headerButtonsGridData);

		btnAddHeader = new Button(headerButtons, SWT.NONE);
		btnAddHeader.setText("Add");
		btnAddHeader.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			headers.add(new Header("header", "value"));
			headersTable.refresh();
		}));
		
		btnRemoveHeader = new Button(headerButtons, SWT.NONE);
		btnRemoveHeader.setText("Remove");
		btnRemoveHeader.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			headersTable.getStructuredSelection().forEach(o -> {
				headers.remove((Header) o);
			});
			headersTable.refresh();
		}));

		btnClearHeaders = new Button(headerButtons, SWT.NONE);
		btnClearHeaders.setText("Clear");
		btnClearHeaders.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			headers.clear();
			headersTable.refresh();
		}));
	}

	private void createTableColumns() {
		TableViewerColumn colHeader = new TableViewerColumn(headersTable, SWT.NONE);
		colHeader.getColumn().setWidth(200);
		colHeader.getColumn().setText("Header");
		colHeader.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Header) element).name;
			}
		});
		colHeader.setEditingSupport(new HeaderNameEditingSupport(headersTable));

		TableViewerColumn colValue = new TableViewerColumn(headersTable, SWT.NONE);
		colValue.getColumn().setWidth(400);
		colValue.getColumn().setText("Value");
		colValue.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Header) element).value;
			}
		});
		colValue.setEditingSupport(new HeaderValueEditingSupport(headersTable));
	}

	@Override
	protected void loadProperties() {
		super.loadProperties();
		if (properties == null) {
			return;
		}

		fileText.setText(properties.getProperty(JsonModel.PROPERTY_FILE));
		uriText.setText(properties.getProperty(JsonModel.PROPERTY_URI));

		headers.clear();
		for (Entry<Object, Object> e : properties.entrySet()) {
			if (e.getKey().toString().startsWith(JsonModel.PROPERTY_HEADER_PREFIX)) {
				final String nameValue = e.getValue().toString();
				final int indexOfSeparator = nameValue.indexOf(JsonModel.PROPERTY_HEADER_SEPARATOR);
				if (indexOfSeparator >= 0) {
					headers.add(new Header(nameValue.substring(0, indexOfSeparator), nameValue.substring(indexOfSeparator + 1)));
				}
			}
		}
		headersTable.refresh();

		filebasedButton.setSelection(properties.getProperty(JsonModel.PROPERTY_FILE, "").trim().length() > 0);
		toggleEnabledFields();
	}
	
	@Override
	protected void storeProperties() {
		super.storeProperties();

		properties.put(JsonModel.PROPERTY_URI, uriText.getText().strip());
		properties.put(JsonModel.PROPERTY_FILE, fileText.getText().strip());

		int iHeader = 0;
		for (Header header : headers) {
			properties.put(JsonModel.PROPERTY_HEADER_PREFIX + iHeader,
				String.format("%s%s%s", header.name.strip(), JsonModel.PROPERTY_HEADER_SEPARATOR, header.value.strip()));

			++iHeader;
		}
	}
}
