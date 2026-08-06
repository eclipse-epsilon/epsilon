/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.editor.contentassist;

import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

public class PreceedingWordTemplateVariableResolver extends TemplateVariableResolver {
	
	@Override
	public String getType() {
		return "preceeding_word";
	}
	
	@Override
	protected String resolve(TemplateContext context) {
		if (context instanceof DocumentTemplateContext) {
			DocumentTemplateContext dtc = (DocumentTemplateContext) context;
			return dtc.getKey();
		}
		else return "";
	}
	
	@Override
	protected boolean isUnambiguous(TemplateContext context) {
		return true;
	}
	
}
