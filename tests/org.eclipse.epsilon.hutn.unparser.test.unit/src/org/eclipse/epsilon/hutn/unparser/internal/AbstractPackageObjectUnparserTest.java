/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.unparser.internal;

import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;

public abstract class AbstractPackageObjectUnparserTest extends AbstractObjectUnparserTest {

	public static void packageObjectUnparserTest(PackageObject po) {
		EmfUtil.createResource(po);
		
		expected = po;
		unparsed = new PackageObjectUnparser(po).unparse();
	}
	
	protected static int numberOfClassObjects() {
		if (!bodyContents().contains("\n")) {
			return 0;
		}
		
		return bodyContents().split("\\n").length;
	}
	
	protected static String classObject(int index) {
		return bodyContents().split("\\n")[index].trim();
	}
}
