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
package org.eclipse.epsilon.hutn.xmi.postprocessor.fragment;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createClassObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createContainmentSlot;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createPackageObject;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.xmi.postprocessor.UriFragmentResolver;
import org.junit.Test;

public class UriFragmentResolverTest {

	private static Spec createModel(ClassObject... contents) {
		return createModel("contents", contents);
	}
	
	private static Spec createModel(String containingFeature, ClassObject... contents) {
		return createSpec(createClassObject("Model", createContainmentSlot(containingFeature, contents)));
	}
	
	private static Spec createSpec(ClassObject... contents) {
		return org.eclipse.epsilon.hutn.test.util.HutnUtil.createSpec(createPackageObject(contents)); 
	}
	
	private static ClassObject resolve(String uriFragment, Spec spec) {
		return new UriFragmentResolver(spec).resolve(uriFragment).iterator().next();
	}
	
	private void resolveTest(String uriFragment, Spec spec, ClassObject expected) {
		assertEquals(expected, resolve(uriFragment, spec));
	}
	
	@Test
	public void root() {
		final Spec spec = createModel();
		
		resolveTest("#//", spec, spec.getObjects().get(0).getClassObjects().get(0));
	}
	
	@Test
	public void single() {
		final ClassObject pet = createClassObject("Pet");

		resolveTest("#//@contents.0", createModel(pet), pet);
	}
	
	@Test
	public void singleWithoutHash() {
		final ClassObject pet = createClassObject("Pet");

		resolveTest("//@contents.0", createModel(pet), pet);
	}
	
	@Test
	public void secondElement() {
		final ClassObject pet = createClassObject("Pet");
		
		resolveTest("#//@contents.1", createModel(createClassObject("Family"), pet), pet);
	}
	
	@Test
	public void unknownElement() {
		resolveTest("#//@contents.0", createModel(), null);
	}
	
	@Test
	public void negativeIndex() {
		resolveTest("#//@contents.-1", createModel(), null);
	}
	
	@Test
	public void unknownFeature() {
		resolveTest("#//@unknowns.0", createModel(createClassObject("Pet")), null);
	}
	
	@Test
	public void differentFeature() {
		final ClassObject pet = createClassObject("Pet");

		resolveTest("#//@elements.0", createModel("elements", pet), pet);
	}
	
	@Test
	public void nested() {
		final ClassObject person = createClassObject("Person");
		
		final Spec spec = createModel(createClassObject("Family",
		                              	createContainmentSlot("members",
		                              			person)));

		resolveTest("#//@contents.0/@members.0", spec, person);
	}
	
	
	@Test
	public void nestedWithUnknownParentIndex() {
		final Spec spec = createModel(createClassObject("Family",
		                              	createContainmentSlot("members",
		                              			createClassObject("Person"))));

		resolveTest("#//@contents.1/@members.0", spec, null);
	}
	
	@Test
	public void nestedWithUnknownParent() {
		final Spec spec = createModel(createClassObject("Family",
		                              	createContainmentSlot("members",
		                              			createClassObject("Person"))));

		resolveTest("#//@unknowns.0/@members.0", spec, null);
	}
	
	@Test
	public void nestedWithUnknownFeature() {
		final Spec spec = createModel(createClassObject("Family",
		                              	createContainmentSlot("members",
		                              			createClassObject("Person"))));

		resolveTest("#//@contents.0/@unknowns.0", spec, null);
	}
	
	@Test
	public void topLevel() {
		final ClassObject pet = createClassObject("Pet");
		
		final Spec spec = createSpec(createClassObject("Model"),
		                             createClassObject("Model",
		                             	createContainmentSlot("contents", pet)));
		
		resolveTest("#/1/@contents.0", spec, pet);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void tooShortToBeAURIFragment() {
		resolve("a", createSpec());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void notAValidURIFragment() {
		resolve("foobar", createSpec());
	}
	
	// topLevelWithUnknownIndex
}
