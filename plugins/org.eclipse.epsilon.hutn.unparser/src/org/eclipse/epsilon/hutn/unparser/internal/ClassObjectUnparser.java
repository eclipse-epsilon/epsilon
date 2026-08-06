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
import org.eclipse.epsilon.hutn.model.hutn.Slot;

class ClassObjectUnparser extends Unparser {
	
	private final ClassObject classObject;
	
	public ClassObjectUnparser(ClassObject classObject) {
		this.classObject = classObject;
	}

	public ClassObjectUnparser(ClassObject classObject, StringBuilder builder) {
		super(builder);
		this.classObject = classObject;
	}

	protected void doUnparse() {
		appendSignature(classObject);
		appendSpace();
		unparseSlots();
	}
	
	private void unparseSlots() {
		builder.append('{');
		
		for (Slot<?> slot : classObject.getSlots()) {
			unparseSlot(slot);
		}
		
		builder.append('}');
	}

	private void unparseSlot(Slot<?> slot) {
		new SlotUnparser(slot, builder).unparse();
		appendNewLine();
	}
}
