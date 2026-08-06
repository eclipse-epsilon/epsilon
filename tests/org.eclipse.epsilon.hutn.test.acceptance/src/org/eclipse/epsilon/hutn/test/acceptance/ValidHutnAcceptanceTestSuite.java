/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: ValidHutnAcceptanceTestSuite.java,v 1.9 2008/08/14 13:04:24 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.test.acceptance.valid.Adjectives;
import org.eclipse.epsilon.hutn.test.acceptance.valid.AssociationBlock;
import org.eclipse.epsilon.hutn.test.acceptance.valid.Associations;
import org.eclipse.epsilon.hutn.test.acceptance.valid.CaseInsensitiveSpec;
import org.eclipse.epsilon.hutn.test.acceptance.valid.ClassifierLevelAttributePrecedence;
import org.eclipse.epsilon.hutn.test.acceptance.valid.ClassifierLevelAttributes;
import org.eclipse.epsilon.hutn.test.acceptance.valid.Comments;
import org.eclipse.epsilon.hutn.test.acceptance.valid.Containment;
import org.eclipse.epsilon.hutn.test.acceptance.valid.ContainmentAnonymous;
import org.eclipse.epsilon.hutn.test.acceptance.valid.ContainmentEmpty;
import org.eclipse.epsilon.hutn.test.acceptance.valid.ContainmentRepeatedFeatureName;
import org.eclipse.epsilon.hutn.test.acceptance.valid.DefaultValue;
import org.eclipse.epsilon.hutn.test.acceptance.valid.DifferentFile;
import org.eclipse.epsilon.hutn.test.acceptance.valid.EmptyString;
import org.eclipse.epsilon.hutn.test.acceptance.valid.Enumerations;
import org.eclipse.epsilon.hutn.test.acceptance.valid.ExternalObjectReference;
import org.eclipse.epsilon.hutn.test.acceptance.valid.ExternalObjectReferenceRelative;
import org.eclipse.epsilon.hutn.test.acceptance.valid.InferEmptyInstanceForRequiredReference;
import org.eclipse.epsilon.hutn.test.acceptance.valid.Inference;
import org.eclipse.epsilon.hutn.test.acceptance.valid.InfixAssociation;
import org.eclipse.epsilon.hutn.test.acceptance.valid.NullValues;
import org.eclipse.epsilon.hutn.test.acceptance.valid.ObjectShorthands;
import org.eclipse.epsilon.hutn.test.acceptance.valid.Simple;
import org.eclipse.epsilon.hutn.test.acceptance.valid.SpecOnly;
import org.eclipse.epsilon.hutn.test.acceptance.valid.StringContainingQuotes;
import org.eclipse.epsilon.hutn.test.acceptance.valid.TwoMetamodels;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({SpecOnly.class, DifferentFile.class,
               Simple.class, ObjectShorthands.class,
               EmptyString.class, StringContainingQuotes.class, CaseInsensitiveSpec.class, 
               Comments.class, Adjectives.class,
               Associations.class, Containment.class, ContainmentEmpty.class, ContainmentAnonymous.class,
               Inference.class, DefaultValue.class, Enumerations.class, 
               ClassifierLevelAttributes.class, ClassifierLevelAttributePrecedence.class, 
               AssociationBlock.class, InfixAssociation.class,
               ExternalObjectReference.class, ExternalObjectReferenceRelative.class, TwoMetamodels.class,
               ContainmentRepeatedFeatureName.class, InferEmptyInstanceForRequiredReference.class,
               NullValues.class})
public class ValidHutnAcceptanceTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(ValidHutnAcceptanceTestSuite.class);
	}
}
