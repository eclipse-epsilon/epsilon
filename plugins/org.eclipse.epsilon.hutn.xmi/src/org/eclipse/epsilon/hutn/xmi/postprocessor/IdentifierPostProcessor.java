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
package org.eclipse.epsilon.hutn.xmi.postprocessor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.xmi.util.EmfUtil;

public class IdentifierPostProcessor {

	private final Spec spec;
	private final ClassObjectNamer classObjectNamer = new ClassObjectNamer();
	private final Map<String, String> renamings = new HashMap<>();
	
	public IdentifierPostProcessor(Spec spec) {
		this.spec = spec;
	}

	public void process() {
		renameClassObjects();
		updateReferenceSlots();
	}
	
	private void renameClassObjects() {
		for (ClassObject co : allClassObjects()) {
			renameClassObject(co, classObjectNamer.name(co));
		}
	}
	
	private List<ClassObject> allClassObjects() {
		return EmfUtil.getAllModelElementsOfType(spec, ClassObject.class);
	}
	
	private void renameClassObject(ClassObject co, String newIdentifier) {
		renamings.put(co.getIdentifier(), newIdentifier);
		co.setIdentifier(newIdentifier);
	}
	
	
	private void updateReferenceSlots() {
		for (ReferenceSlot slot : allReferenceSlots()) {
			final List<String> newValues = new LinkedList<>();
			
			for (String identifier : slot.getValues()) {
				final String newValue;
				
				if (renamings.containsKey(identifier)) {
					newValue = renamings.get(identifier);
				} else {
					newValue = identifier;
				}
				
				newValues.add(newValue);
			}
			
			slot.getValues().clear();
			slot.getValues().addAll(newValues);
		}
	}
	
	private List<ReferenceSlot> allReferenceSlots() {
		return EmfUtil.getAllModelElementsOfType(spec, ReferenceSlot.class);
	}
}
