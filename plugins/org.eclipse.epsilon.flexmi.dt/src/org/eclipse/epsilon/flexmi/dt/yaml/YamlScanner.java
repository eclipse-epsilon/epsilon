/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.dt.yaml;

import org.eclipse.epsilon.flexmi.dt.FlexmiHighlightingManager;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;

public class YamlScanner extends RuleBasedScanner {

	public YamlScanner(FlexmiHighlightingManager colorManager) {
		
		IRule[] rules = new IRule[5];
		rules [0] = new SingleLineRule("#", "", new Token(new TextAttribute(colorManager.getCommentColor(), null, SWT.NORMAL)), (char) -1, true);
		rules [1] = new YamlDoubleQuoteRule(new Token(new TextAttribute(colorManager.getStringColor(), null, SWT.NORMAL)));
		rules [2] = new YamlSingleQuoteRule(new Token(new TextAttribute(colorManager.getStringColor(), null, SWT.NORMAL)));
		rules [3] = new YamlMappingRule(new Token(new TextAttribute(colorManager.getTagColor(), null, SWT.NORMAL)));
		rules [4] = new YamlProcessingInstructionRule(new Token(new TextAttribute(colorManager.getProcInstrColor(), null, SWT.NORMAL)));
		
		setRules(rules);
	}
}
