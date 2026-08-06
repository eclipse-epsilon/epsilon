/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete.Delete;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete.DeleteAppliesToSubtypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete.DeleteCanExcludeSomeSubtypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete.DeleteFullyQualifiedType;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete.DeleteWithGuard;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete.DeleteWithInvalidSyntax;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete.StrictDeleteDoesNotApplyToSubtypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletecascade.DeleteCascade;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletecascade.DeleteCascadeToChildrenThatHaveSameTypeAsParentButDoNotSatisfyGuard;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletecascade.DeleteCascadeToGrandchildren;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletecascade.DeleteCascadeWithGuard;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletecascade.StrictDeleteCascade;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletepackage.DeletePackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletepackage.DeletePackageWithGuard;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletepackage.DeletePackageWithInvalidSyntax;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype.Retype;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype.RetypeAppliesToSubtypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype.RetypeCanExcludeSomeSubtypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype.RetypeFullyQualifiedTypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype.RetypeWithGuard;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype.RetypeWithInvalidSyntax;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype.StrictRetypeDoesNotApplyToSubtypes;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retypepackage.RetypePackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retypepackage.RetypePackageWithGuard;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retypepackage.RetypePackageWithInvalidSyntax;
import org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.OnlyTheFirstApplicableTypeMappingIsAppliedPerModelElement;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({Delete.class,
               DeleteWithGuard.class,
               DeleteAppliesToSubtypes.class,
               DeleteCanExcludeSomeSubtypes.class,
               DeleteWithInvalidSyntax.class,
               StrictDeleteDoesNotApplyToSubtypes.class,
               DeleteFullyQualifiedType.class,
               DeleteCascade.class,
               DeleteCascadeWithGuard.class,
               DeleteCascadeToChildrenThatHaveSameTypeAsParentButDoNotSatisfyGuard.class,
               DeleteCascadeToGrandchildren.class,
               StrictDeleteCascade.class,
               DeletePackage.class,
               DeletePackageWithGuard.class,
               DeletePackageWithInvalidSyntax.class,
               Retype.class,
               RetypeWithGuard.class,
               RetypeAppliesToSubtypes.class,
               RetypeCanExcludeSomeSubtypes.class,
               RetypeWithInvalidSyntax.class,
               StrictRetypeDoesNotApplyToSubtypes.class,
               RetypeFullyQualifiedTypes.class,
               RetypePackage.class,
               RetypePackageWithGuard.class,
               RetypePackageWithInvalidSyntax.class,
               OnlyTheFirstApplicableTypeMappingIsAppliedPerModelElement.class})
public class TypeMappingsSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(RulesSuite.class);
	}
}
