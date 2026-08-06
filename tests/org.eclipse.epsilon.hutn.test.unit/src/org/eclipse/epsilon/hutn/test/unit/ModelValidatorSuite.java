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
 * $Id: ModelValidatorSuite.java,v 1.4 2008/10/09 11:54:05 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.unit;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.validation.model.ContainmentForReference;
import org.eclipse.epsilon.hutn.validation.model.IncorrectTypeForContainment;
import org.eclipse.epsilon.hutn.validation.model.ReferenceForContainment;
import org.eclipse.epsilon.hutn.validation.model.RequiredReferenceOppositeValueSpecified;
import org.eclipse.epsilon.hutn.validation.model.UnchangeableReferencesNeedNotSpecifyAValue;
import org.eclipse.epsilon.hutn.validation.model.DuplicateIdentifiers;
import org.eclipse.epsilon.hutn.validation.model.IncorrectTypeForBoolean;
import org.eclipse.epsilon.hutn.validation.model.IncorrectTypeForEnum;
import org.eclipse.epsilon.hutn.validation.model.IncorrectTypeForFloat;
import org.eclipse.epsilon.hutn.validation.model.IncorrectTypeForInteger;
import org.eclipse.epsilon.hutn.validation.model.IncorrectTypeForReference;
import org.eclipse.epsilon.hutn.validation.model.IncorrectTypeForString;
import org.eclipse.epsilon.hutn.validation.model.InstantiateEnum;
import org.eclipse.epsilon.hutn.validation.model.ReferenceWithSubtype;
import org.eclipse.epsilon.hutn.validation.model.RequiredReferenceValueNotSpecified;
import org.eclipse.epsilon.hutn.validation.model.TooManyValues;
import org.eclipse.epsilon.hutn.validation.model.Traceability;
import org.eclipse.epsilon.hutn.validation.model.UnrecognisedClassifier;
import org.eclipse.epsilon.hutn.validation.model.UnrecognisedFeature;
import org.eclipse.epsilon.hutn.validation.model.UnrecognisedIdentifier;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DuplicateIdentifiers.class, UnrecognisedClassifier.class,
               UnrecognisedFeature.class, UnrecognisedIdentifier.class,
               TooManyValues.class, IncorrectTypeForString.class,
               IncorrectTypeForInteger.class, IncorrectTypeForBoolean.class,
               IncorrectTypeForFloat.class, IncorrectTypeForEnum.class,
               IncorrectTypeForReference.class, ReferenceWithSubtype.class,
               IncorrectTypeForContainment.class, Traceability.class, InstantiateEnum.class,
               RequiredReferenceValueNotSpecified.class, RequiredReferenceOppositeValueSpecified.class,
               ModelValidatorExternalRefSuite.class, UnchangeableReferencesNeedNotSpecifyAValue.class,
               ReferenceForContainment.class, ContainmentForReference.class})
public class ModelValidatorSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(ModelValidatorSuite.class);
	}
}
