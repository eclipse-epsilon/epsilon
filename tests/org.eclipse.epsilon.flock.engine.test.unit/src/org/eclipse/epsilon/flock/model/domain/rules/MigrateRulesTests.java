/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.model.domain.rules;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.MigrateRuleContext;
import org.junit.Test;

public class MigrateRulesTests {
	
	private static final MigrateRuleContext equivalenceAndContext = mock(MigrateRuleContext.class, "dummyEquivalenceAndContext");

	@Test
	public void shouldDelegateToRulesWhetherTheyAreApplicableOrNot() throws Exception {
		final MigrateRule firstApplicableRule  = ruleWithApplicabilty(true);
		final MigrateRule secondApplicableRule = ruleWithApplicabilty(true);
		final MigrateRule inapplicableRule     = ruleWithApplicabilty(false);
				
		final MigrateRules rules = new MigrateRules(firstApplicableRule, inapplicableRule, secondApplicableRule);
		
		rules.applyTo(equivalenceAndContext);
		
		verify(firstApplicableRule).applyTo(equivalenceAndContext);
		verify(secondApplicableRule).applyTo(equivalenceAndContext);
		verify(inapplicableRule).applyTo(equivalenceAndContext);
	}

	private static MigrateRule ruleWithApplicabilty(boolean applicable) throws EolRuntimeException {
		final MigrateRule rule = mock(MigrateRule.class);
		when(rule.applyTo(equivalenceAndContext)).thenReturn(applicable);
		return rule;
	}
}
