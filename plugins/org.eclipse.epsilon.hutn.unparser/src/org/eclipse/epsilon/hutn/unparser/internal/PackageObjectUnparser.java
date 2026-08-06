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
package org.eclipse.epsilon.hutn.unparser.internal;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;

class PackageObjectUnparser extends Unparser {

	private final PackageObject packageObject;
	
	public PackageObjectUnparser(PackageObject packageObject) {
		this.packageObject = packageObject;
	}

	public PackageObjectUnparser(PackageObject packageObject, StringBuilder builder) {
		super(builder);
		this.packageObject = packageObject;
	}

	@Override
	protected void doUnparse() {
		appendSignature(packageObject);
		appendSpace();
		unparseClassObjects();
	}

	private void unparseClassObjects() {
		builder.append('{');
		
		for (ClassObject classObject : packageObject.getClassObjects()) {
			unparseClassObject(classObject);
		}
		
		builder.append('}');
	}
	
	private void unparseClassObject(ClassObject classObject) {
		new ClassObjectUnparser(classObject, builder).unparse();
		appendNewLine();
	}
}
