/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dt.debug.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.eol.dap.EpsilonDebugAdapter;
import org.eclipse.epsilon.eol.dt.EolPlugin;
import org.eclipse.lsp4e.debug.DSPPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("restriction")
public class RemoteEpsilonConnectionConfigurationTab extends AbstractLaunchConfigurationTab implements ModifyListener, SelectionListener {

	private static final String OPTION_STOP_AT_EVERY_STATEMENT = "epsilon-dap-stop-every";

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 4040;
	private static final boolean DEFAULT_STOP_AT_EVERY_STATEMENT = false;

	private static final Map<String, String> DEFAULT_PARAMETERS = new HashMap<>();
	static {
		DEFAULT_PARAMETERS.put("request", "attach");
	}

	private Text txtHost;
	private Text txtPort;
	private Button btnStopAtEveryStatement;

	@Override
	public void createControl(Composite parent) {
		GridLayout parentLayout = new GridLayout();
		parent.setLayout(parentLayout);

		Composite control = new Composite(parent, SWT.NONE);
		setControl(control);

		GridLayout controlLayout = new GridLayout(1, false);
		control.setLayout(controlLayout);

		final Group connectionGroup = createGroup(control, "Connection properties:", 2);
		final Label lblHost = new Label(connectionGroup, SWT.NONE);
		lblHost.setText("Host:");

		txtHost = new Text(connectionGroup, SWT.BORDER);
		txtHost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtHost.addModifyListener(this);

		final Label lblPort = new Label(connectionGroup, SWT.NONE);
		lblPort.setText("Port:");

		txtPort = new Text(connectionGroup, SWT.BORDER);
		txtPort.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtPort.addModifyListener(this);

		final Group optionsGroup = createGroup(control, "Debugging options:", 2);
		btnStopAtEveryStatement = new Button(optionsGroup, SWT.CHECK);
		btnStopAtEveryStatement.setText("Line breakpoints stop at every statement on the line");
		GridData btnStopAtEveryStatementGD = new GridData(GridData.FILL_HORIZONTAL);
		btnStopAtEveryStatementGD.horizontalSpan = 2;
		btnStopAtEveryStatement.setLayoutData(btnStopAtEveryStatementGD);
		btnStopAtEveryStatement.addSelectionListener(this);

		control.setBounds(0, 0, 300, 300);
		control.layout();
		control.pack();

		canSave();
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DSPPlugin.ATTR_DSP_SERVER_HOST, DEFAULT_HOST);
		configuration.setAttribute(DSPPlugin.ATTR_DSP_SERVER_PORT, DEFAULT_PORT);
		configuration.setAttribute(DSPPlugin.ATTR_DSP_MODE, DSPPlugin.DSP_MODE_CONNECT);
		configuration.setAttribute(DSPPlugin.ATTR_DSP_PARAM, mapToJson(DEFAULT_PARAMETERS));
		configuration.setAttribute(OPTION_STOP_AT_EVERY_STATEMENT, DEFAULT_STOP_AT_EVERY_STATEMENT);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			txtHost.setText(configuration.getAttribute(DSPPlugin.ATTR_DSP_SERVER_HOST, DEFAULT_HOST));
			txtPort.setText(configuration.getAttribute(DSPPlugin.ATTR_DSP_SERVER_PORT, DEFAULT_PORT) + "");
			btnStopAtEveryStatement.setSelection(configuration.getAttribute(OPTION_STOP_AT_EVERY_STATEMENT, DEFAULT_STOP_AT_EVERY_STATEMENT));
		} catch (CoreException e) {
			LogUtil.log(e);
		}
		updateLaunchConfigurationDialog();
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DSPPlugin.ATTR_DSP_SERVER_HOST, txtHost.getText());
		configuration.setAttribute(DSPPlugin.ATTR_DSP_SERVER_PORT, Integer.parseInt(txtPort.getText()));
		configuration.setAttribute(OPTION_STOP_AT_EVERY_STATEMENT, btnStopAtEveryStatement.getSelection());

		Map<String, String> parameters = new HashMap<>(DEFAULT_PARAMETERS);
		if (btnStopAtEveryStatement.getSelection()) {
			parameters.put(EpsilonDebugAdapter.STOP_AT_EVERY_STATEMENT, Boolean.TRUE.toString());
		}
		configuration.setAttribute(DSPPlugin.ATTR_DSP_PARAM, mapToJson(parameters));
	}

	@Override
	public String getName() {
		return "Connect";
	}

	protected Group createGroup(Composite control, String name, int numberOfColumns) {
		final Group group = new Group(control, SWT.SHADOW_ETCHED_IN);
		group.setLayout(new GridLayout(numberOfColumns, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setText(name);
		return group;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		canSave();
		updateLaunchConfigurationDialog();
	}

	@Override
	public boolean canSave() {
		if (txtHost.getText().trim().length() == 0) {
			setErrorMessage("A host must be specified");
			return false;
		}

		try {
			int port = Integer.parseInt(txtPort.getText());
			if (port < 1 || port > 65535) {
				setErrorMessage("The port must be within the range of [1, 65535]");
				return false;
			}
		} catch (NumberFormatException ex) {
			setErrorMessage("The port must be an integer number");
			return false;
		}

		setErrorMessage(null);
		return true;
	}

	@Override
	public Image getImage() {
		return EolPlugin.getDefault().createImage("icons/connect.png");
	}

	protected static String mapToJson(Map<String, String> map) {
		List<String> entries = map.entrySet().stream()
			.map(e -> entryToJson(e))
			.collect(Collectors.toList());
		return "{" + String.join(", ", entries) + "}";
	}

	protected static String entryToJson(Entry<String, String> e) {
		return String.format("\"%s\": \"%s\"", e.getKey(), e.getValue());
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		canSave();
		updateLaunchConfigurationDialog();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
