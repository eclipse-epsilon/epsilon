/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.dialogs;

import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractCachedModelConfigurationDialog extends AbstractModelConfigurationDialog {
	
	protected Button isCachedButton, isConcurrentButton;
	
	@Override
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createPerformanceGroup(control);
	}
	
	protected void createPerformanceGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Performance", 3);
		
		isCachedButton = new Button(groupContent, SWT.CHECK);
		isCachedButton.setText("Cache model elements to improve execution time");
		isCachedButton.setSelection(true);
		
		isConcurrentButton = new Button(groupContent, SWT.CHECK);
		isConcurrentButton.setSelection(false);
		isConcurrentButton.setText("Thread-safe cache");
		
		GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);
		buttonData.horizontalSpan = 2;
		isCachedButton.setLayoutData(buttonData);
		isConcurrentButton.setLayoutData(buttonData);
		
		groupContent.layout();
		groupContent.pack();
	}
	
	@Override
	protected void loadProperties() {
		super.loadProperties();
		if (properties == null) return;
		if (isCachedButton != null) {
			isCachedButton.setSelection(properties.getBooleanProperty(CachedModel.PROPERTY_CACHED, true));
		}
		if (isConcurrentButton != null) {
			isConcurrentButton.setSelection(properties.getBooleanProperty(CachedModel.PROPERTY_CONCURRENT, false));
		}
	}
	
	@Override
	protected void storeProperties() {
		super.storeProperties();
		if (isCachedButton != null) {
			properties.put(CachedModel.PROPERTY_CACHED, isCachedButton.getSelection() + "");
		}
		if (isConcurrentButton != null) {
			properties.put(CachedModel.PROPERTY_CONCURRENT, isConcurrentButton.getSelection() + "");
		}
	}
}
