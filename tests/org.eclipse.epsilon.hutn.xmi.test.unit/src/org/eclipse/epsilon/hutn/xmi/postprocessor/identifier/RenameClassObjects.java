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
package org.eclipse.epsilon.hutn.xmi.postprocessor.identifier;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.xmi.postprocessor.IdentifierPostProcessor;
import org.junit.Test;

public class RenameClassObjects {

	private void identifierTest(Map<ClassObject, String> data) {
		final Spec spec = createSpec(createPackageObject(data.keySet().toArray(new ClassObject[] {})));
				
		new IdentifierPostProcessor(spec).process();
		
		for (Map.Entry<ClassObject, String> entry : data.entrySet()) {
			assertEquals(entry.getValue(), entry.getKey().getIdentifier());
		}
	}
	
	@Test
	public void singleObject() {
		final Map<ClassObject, String> data = new HashMap<>();
		data.put(createClassObject("Person"), "Person1");
		identifierTest(data);
	}
	
	@Test
	public void noType() {
		final Map<ClassObject, String> data = new HashMap<>();
		data.put(createClassObject(), "UnknownType1");
		identifierTest(data);
	}
	
	@Test
	public void overwritesExistingIdentifier() {
		final Map<ClassObject, String> data = new HashMap<>();
		data.put(createClassObject("fred", "Person"), "Person1");
		identifierTest(data);
	}
	
	@Test
	public void twoObjectsOfSameType() {
		final Map<ClassObject, String> data = new LinkedHashMap<>();
		data.put(createClassObject("Person"), "Person1");
		data.put(createClassObject("Person"), "Person2");
		identifierTest(data);
	}
	
	@Test
	public void mixedTypes() {
		final Map<ClassObject, String> data = new LinkedHashMap<>();
		data.put(createClassObject("Person"), "Person1");
		data.put(createClassObject("Family"), "Family1");
		data.put(createClassObject("Person"), "Person2");
		
		identifierTest(data);
	}
	
	@Test
	public void preferNameWhenAvailable() {
		final Map<ClassObject, String> data = new LinkedHashMap<>();
		data.put(createClassObject("Person", createAttributeSlot("name", "John Doe")), "John Doe");
		
		identifierTest(data);
	}
	
	@Test
	public void onlyUseAttributeTypedNameAsIdentifier() {
		final Map<ClassObject, String> data = new LinkedHashMap<>();
		data.put(createClassObject("Person", createContainmentSlot("name", createClassObject("Person"))), "Person1");
		
		identifierTest(data);
	}
}
