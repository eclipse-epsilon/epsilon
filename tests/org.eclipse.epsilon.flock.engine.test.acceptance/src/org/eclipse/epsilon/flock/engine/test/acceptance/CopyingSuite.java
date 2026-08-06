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

import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopyEnumerationValue;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopyListWhenLowerboundDecreases;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopyListWhenUpperboundIncreases;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopyModelElementsThatHaveARule;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopyModelElementsThatHaveNoRules;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopyNestedModelElements;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopyPersistsIdentity;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopySingleValueToList;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.CopyValueFromAnotherModel;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.DoNotCopyEnumerationLiteralsThatDoNotExistInTheTargetMetamodel;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.DoNotCopyListToSingleValue;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.DoNotCopyContainedObjectWithUnknownMigratedClass;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.DoNotCopyListWhenLowerboundIncreases;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.DoNotCopyListWhenUpperboundDecreases;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.DoNotCopyTypesThatDoNotBelongToTheOriginalMetamodel;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CopyModelElementsThatHaveNoRules.class,
               CopyModelElementsThatHaveARule.class,
               CopyNestedModelElements.class,
               DoNotCopyContainedObjectWithUnknownMigratedClass.class,
               CopySingleValueToList.class, DoNotCopyListToSingleValue.class,
               CopyListWhenUpperboundIncreases.class, DoNotCopyListWhenUpperboundDecreases.class,
               CopyListWhenLowerboundDecreases.class, DoNotCopyListWhenLowerboundIncreases.class,
               CopyValueFromAnotherModel.class,
               CopyPersistsIdentity.class,
               DoNotCopyTypesThatDoNotBelongToTheOriginalMetamodel.class,
               CopyEnumerationValue.class,
               DoNotCopyEnumerationLiteralsThatDoNotExistInTheTargetMetamodel.class})
public class CopyingSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(CopyingSuite.class);
	}
}
