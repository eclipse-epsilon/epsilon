/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.editor;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class HutnPartitionScanner extends RuleBasedPartitionScanner {
		public final static String HUTN_COMMENT = "__hutn_comment";

		public HutnPartitionScanner() {
			final IToken hutnComment = new Token(HUTN_COMMENT);

			IPredicateRule[] rules = new IPredicateRule[1];

			rules[0] = new MultiLineRule("/*", "*/", hutnComment);

			setPredicateRules(rules);
		}
	}
