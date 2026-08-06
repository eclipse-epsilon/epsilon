/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.dt.editor.outline;

import org.eclipse.epsilon.eol.dt.editor.outline.EolModuleElementLabelProvider;
import org.eclipse.epsilon.pinset.DatasetRule;
import org.eclipse.epsilon.pinset.dt.PinsetPlugin;
import org.eclipse.swt.graphics.Image;

/**
 * PinsetModuleElementLabelProvider.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetModuleElementLabelProvider extends EolModuleElementLabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof DatasetRule) {
			return PinsetPlugin.getDefault().createImage("icons/process-rule.png");
		}
		else {
			return super.getImage(element);
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof DatasetRule) {
			DatasetRule processRule = (DatasetRule) element;
			return processRule.getParameter().getName() + " : " +
					processRule.getParameter().getTypeName();
		}
		return super.getText(element);
	}
}
