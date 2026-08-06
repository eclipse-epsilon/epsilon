/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.parse;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.dom.IEgxVisitor;
import org.eclipse.epsilon.erl.parse.ErlUnparser;

public class EgxUnparser extends ErlUnparser implements IEgxVisitor {

	@Override
	protected void unparseRules() {
		((EgxModule) module).getDeclaredGenerationRules().forEach(gr -> {gr.accept(this); newline();});
	}

	@Override
	public void visit(GenerationRule rule) {
		unparseAnnotations(rule);
		buffer.append("rule ").append(rule.getName());
		if (rule.getTransformSource() != null) {
			buffer.append(" transform ").append(rule.getTransformSource());
			if (rule.getDomainBlock() != null) {
				buffer.append(" in : ").append(rule.getDomainBlock().getText());
			}
		}
		spaceCurlybraceNewlineIndent();
		
		printGuard(rule.getGuardBlock());
		print("pre", rule.getPreBlock());
		print("overwrite", rule.getOverwriteBlock());
		print("merge", rule.getMergeBlock());
		print("template", rule.getTemplateBlock());
		print("parameters", rule.getParametersBlock());
		print("target", rule.getTargetBlock());
		print("post", rule.getPostBlock());
		print("formatter", rule.getFormatterBlock());
		
		newlineUnindentCurlybrace();
	}
}
