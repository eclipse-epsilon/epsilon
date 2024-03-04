/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ecl.parse;

import java.util.Iterator;

import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.dom.IEclVisitor;
import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.eol.dom.Annotation;
import org.eclipse.epsilon.erl.dom.ExtensibleNamedRule;
import org.eclipse.epsilon.erl.parse.ErlUnparser;

public class EclUnparser extends ErlUnparser implements IEclVisitor {

	@Override
	public void visit(MatchRule matchRule) {
		if (matchRule.getAnnotationBlock() != null) {
			Iterator<Annotation> ann = matchRule.getAnnotationBlock().getAnnotations().iterator();

			while (ann.hasNext()) {
				buffer.append("@");
				buffer.append(ann.next().getName());
				if (ann.hasNext()) {
					comma();
				}
			}
		}
		buffer.append("rule ");
		buffer.append(matchRule.getName());
		newline();
		buffer.append("match ");
		matchRule.getLeftParameter().accept(this);
		if (matchRule.getLeftDomainBlock() != null) {
			print("in", matchRule.getLeftDomainBlock());
		}
		newline();
		buffer.append("with ");
		matchRule.getRightParameter().accept(this);
		if (matchRule.getRightDomainBlock() != null) {
			if (matchRule.isRightDomainDynamic()) {
				print("from", matchRule.getRightDomainBlock());
			}
			else {
				print("in", matchRule.getRightDomainBlock());
			}
		}

		if (matchRule.getSuperRules().size() > 0) {
			buffer.append("extends ");
			Iterator<ExtensibleNamedRule> li = matchRule.getSuperRules().iterator();

			while (li.hasNext()) {
				buffer.append(matchRule.getName());
				if (li.hasNext()) {
					comma();
				}
			}

		}
		spaceCurlybraceNewlineIndent();
		printGuard(matchRule.getGuardBlock());
		newline();
		print("compare", matchRule.getCompareBlock());
		newline();
		print("do", matchRule.getDoBlock());
		newlineUnindentCurlybrace();

	}

	@Override
	protected void unparseRules() {
		((EclModule) module).getDeclaredMatchRules().forEach(c -> {
			c.accept(this);
			newline();
		});

	}

}
