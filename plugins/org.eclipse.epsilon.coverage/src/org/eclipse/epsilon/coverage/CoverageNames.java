/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.erl.dom.NamedRule;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.flock.model.domain.common.ClassifierTypedConstruct;
import org.eclipse.epsilon.flock.model.domain.common.PackageTypedConstruct;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageRetyping;
import org.eclipse.epsilon.flock.model.domain.typemappings.Retyping;
import org.eclipse.epsilon.pinset.DatasetRule;

/**
 * Identifies the named elements of Epsilon programs (operations, rules,
 * constraints etc.), which group the coverage information of the
 * statements and expressions they contain (e.g. in the Coverage view).
 *
 * @since 2.10
 */
public class CoverageNames {

	public static final CoverageNames INSTANCE = new CoverageNames();

	/**
	 * @return Whether the element is a named element, i.e. one of:
	 * <ul>
	 * <li>EOL operations (in all languages)</li>
	 * <li>Named rules, i.e. ETL/ECL/EML/EGX rules, EVL constraints and critiques,
	 * EPL/EMG patterns, and pre/post blocks of all rule-based languages</li>
	 * <li>EVL constraint contexts</li>
	 * <li>Pinset dataset rules</li>
	 * <li>Flock migrate rules, retypings and deletions</li>
	 * </ul>
	 */
	public boolean isNamed(ModuleElement element) {
		return element instanceof Operation || element instanceof NamedRule || element instanceof ConstraintContext
			|| element instanceof DatasetRule || element instanceof ClassifierTypedConstruct
			|| element instanceof PackageTypedConstruct;
	}

	/**
	 * @return The name of a named element, or null if it is not a
	 * named element or if it has no name (e.g. anonymous pre/post blocks)
	 */
	public String getName(ModuleElement element) {
		String name = null;
		if (element instanceof Operation) {
			name = ((Operation) element).getName();
		}
		else if (element instanceof NamedRule) {
			name = ((NamedRule) element).getName();
		}
		else if (element instanceof ConstraintContext) {
			name = ((ConstraintContext) element).getTypeName();
		}
		else if (element instanceof DatasetRule) {
			name = ((DatasetRule) element).getName();
		}
		else if (element instanceof Retyping) {
			name = ((Retyping) element).getOriginalType() + " to " + ((Retyping) element).getEvolvedType();
		}
		else if (element instanceof ClassifierTypedConstruct) {
			name = ((ClassifierTypedConstruct) element).getOriginalType();
		}
		else if (element instanceof PackageRetyping) {
			name = ((PackageRetyping) element).getOriginalPackage() + " to " + ((PackageRetyping) element).getEvolvedPackage();
		}
		else if (element instanceof PackageTypedConstruct) {
			name = ((PackageTypedConstruct) element).getOriginalPackage();
		}
		return name == null || name.trim().isEmpty() ? null : name.trim();
	}
}
