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
package org.eclipse.epsilon.hutn.xmi.util.hutn;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectImpl;

public class ClassObjectStub extends ClassObjectImpl {

	private final EClass metaClass;

	public ClassObjectStub(EClass metaClass) {
		this.metaClass = metaClass;
	}

	@Override
	public boolean hasEClass() {
		return true;
	}

	@Override
	public EClass getEClass() {
		return metaClass;
	}
}
