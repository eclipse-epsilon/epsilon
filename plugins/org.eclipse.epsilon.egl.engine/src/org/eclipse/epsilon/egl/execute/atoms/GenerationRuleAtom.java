/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.execute.atoms;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.execute.data.ExecutableRuleAtom;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class GenerationRuleAtom extends ExecutableRuleAtom<GenerationRule> {

	public GenerationRuleAtom(GenerationRule construct, Object modelElement, IEgxContext context) {
		super(construct, modelElement, context);
	}

	public GenerationRuleAtom(GenerationRule construct, Object modelElement) {
		super(construct, modelElement);
	}

	public static final ArrayList<GenerationRuleAtom> getAllJobs(IEgxModule module) throws EolRuntimeException {
		IEgxContext context = module.getContext();
		final ArrayList<GenerationRuleAtom> atoms = new ArrayList<>();
		
		for (GenerationRule rule : module.getGenerationRules()) {
			Collection<?> allElements = rule.getAllElements(context);
			atoms.ensureCapacity(atoms.size() + allElements.size());
			for (Object element : allElements) {
				atoms.add(new GenerationRuleAtom(rule, element, context));
			}
		}
		
		return atoms;
	}

}
