/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.engine.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.flock.engine.test.acceptance.rules.Rule;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.RuleAppliesToSubtypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.RuleCanExcludeSomeSubtypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.RuleForDeletedType;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.RuleFullyQualifiedType;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.RuleWithGuard;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.RuleWithInvalidSyntax;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.SeveralRulesCanBeAppliedPerModelElement;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.SeveralRulesRunInTheOrderThatTheyAppear;
import org.eclipse.epsilon.flock.engine.test.acceptance.rules.StrictRuleDoesNotApplyToSubtypes;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({Rule.class,
	           RuleWithGuard.class,
	           RuleAppliesToSubtypes.class,
	           RuleCanExcludeSomeSubtypes.class,
	           RuleWithInvalidSyntax.class,
	           RuleForDeletedType.class,
               SeveralRulesCanBeAppliedPerModelElement.class,
               SeveralRulesRunInTheOrderThatTheyAppear.class,
               StrictRuleDoesNotApplyToSubtypes.class,
               RuleFullyQualifiedType.class})
public class RulesSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(RulesSuite.class);
	}
}
