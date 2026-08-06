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
 * $Id: TranslatorSuite.java,v 1.6 2008/08/14 12:37:27 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.unit;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.translate.AssociationBlock;
import org.eclipse.epsilon.hutn.translate.Associations;
import org.eclipse.epsilon.hutn.translate.CaseInsensitiveSpec;
import org.eclipse.epsilon.hutn.translate.SpecWithModelFile;
import org.eclipse.epsilon.hutn.translate.Values;
import org.eclipse.epsilon.hutn.translate.ClassifierLevelAttributes;
import org.eclipse.epsilon.hutn.translate.Containment;
import org.eclipse.epsilon.hutn.translate.ContainmentRepeatedFeatureName;
import org.eclipse.epsilon.hutn.translate.DefaultValue;
import org.eclipse.epsilon.hutn.translate.EmptyAst;
import org.eclipse.epsilon.hutn.translate.Enumerations;
import org.eclipse.epsilon.hutn.translate.InferredAttributeValue;
import org.eclipse.epsilon.hutn.translate.InferredIdentifier;
import org.eclipse.epsilon.hutn.translate.InfixAssociation;
import org.eclipse.epsilon.hutn.translate.MultiValuedAttribute;
import org.eclipse.epsilon.hutn.translate.NonExistentConfigFile;
import org.eclipse.epsilon.hutn.translate.RepeatedFeatureWithDifferentTypes;
import org.eclipse.epsilon.hutn.translate.SingleClass;
import org.eclipse.epsilon.hutn.translate.SingleClassWithAdjectives;
import org.eclipse.epsilon.hutn.translate.SingleClassWithAttributes;
import org.eclipse.epsilon.hutn.translate.SinglePackage;
import org.eclipse.epsilon.hutn.translate.SinglePackageWithSpec;
import org.eclipse.epsilon.hutn.translate.TwoClasses;
import org.eclipse.epsilon.hutn.translate.TwoPackages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EmptyAst.class, SinglePackage.class, TwoPackages.class,
               SinglePackageWithSpec.class, SpecWithModelFile.class,
               SingleClass.class, CaseInsensitiveSpec.class, TwoClasses.class,
               SingleClassWithAdjectives.class, SingleClassWithAttributes.class,
               Values.class, MultiValuedAttribute.class, Associations.class,
               Containment.class, InferredAttributeValue.class, InferredIdentifier.class,
               NonExistentConfigFile.class, DefaultValue.class, Enumerations.class,
               ClassifierLevelAttributes.class, AssociationBlock.class,
               InfixAssociation.class,
               ContainmentRepeatedFeatureName.class, RepeatedFeatureWithDifferentTypes.class})
public class TranslatorSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(TranslatorSuite.class);
	}
}
