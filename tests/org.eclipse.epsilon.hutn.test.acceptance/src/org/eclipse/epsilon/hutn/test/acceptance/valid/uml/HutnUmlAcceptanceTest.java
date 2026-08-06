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
 * $Id: HutnUmlAcceptanceTest.java,v 1.2 2008/08/07 12:44:24 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid.uml;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.BeforeClass;

public class HutnUmlAcceptanceTest extends HutnAcceptanceTest {

	@BeforeClass
	public static void registerUmlMetamodel() {
		EPackage.Registry.INSTANCE.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
	}
}
