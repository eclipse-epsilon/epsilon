/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.recording;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccessRecorder;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccesses;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccess;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessExecutionListener;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessRecorder;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccesses;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.junit.Before;
import org.junit.Test;

public class PropertyAccessRecording {

	private final IEolModule module = new EolModule();
	private final IPropertyAccessRecorder recorder = new PropertyAccessRecorder();
	private final Person enemy = new Person("Joe's Enemy", 21);
	private final Person person = new Person("Joe Bloggs", 42, enemy);
	private final JavaModel model = new JavaModel("People", Arrays.asList(person, enemy), Arrays.asList(Person.class));
	
	@Before
	public void setup() {
		module.getContext().getModelRepository().addModel(model);
		module.getContext().getExecutorFactory().addExecutionListener(new PropertyAccessExecutionListener(recorder));
	}
	
	@Test
	public void doesNotRecordAccessesByDefault() throws Exception {
		module.parse("var c = Person.all.first; var name = c.name;");
		module.execute();
		
		assertEquals(new PropertyAccesses(), recorder.getPropertyAccesses());
	}
	
	
	@Test
	public void recordsAccessesBetweenStartAndStop() throws Exception {
		module.parse("var c = Person.all.first; var name = c.name;");
		
		recorder.startRecording();
		module.execute();
		recorder.stopRecording();
		
		final IPropertyAccesses expectedPropertyAccesses = new PropertyAccesses(
			new PropertyAccess(person, "name")
		);
		
		assertEquals(expectedPropertyAccesses, recorder.getPropertyAccesses());
	}
	
	@Test
	public void recordsAccessesOfCollectionProperties() throws Exception {
		module.parse("Person.all.name;");
		
		recorder.startRecording();
		module.execute();
		recorder.stopRecording();
		
		final IPropertyAccesses expectedPropertyAccesses = new PropertyAccesses(
			new PropertyAccess(person, "name"),
			new PropertyAccess(enemy, "name")
		);
		
		assertEquals(expectedPropertyAccesses, recorder.getPropertyAccesses());
		
		// Check that the "var" variable from PrecomputedObjectPropertyCallExpression does not leak
		assertFalse(module.getContext().getFrameStack().contains("var"));
	}
	
	@Test
	public void recordsNestedAccessesBetweenStartAndStop() throws Exception {
		module.parse("var c = Person.all.first; var enemyName = c.enemy.name;");
		
		recorder.startRecording();
		module.execute();
		recorder.stopRecording();
		
		final IPropertyAccesses expectedPropertyAccesses = new PropertyAccesses(
			new PropertyAccess(person, "enemy"),
			new PropertyAccess(enemy, "name")
		);
		
		assertEquals(expectedPropertyAccesses, recorder.getPropertyAccesses());
	}

	@Test
	public void recordsAllAccessesBetweenStartAndStop() throws Exception {
		module.parse("var c = Person.all.first; var name = c.name; var age = c.age;");
		
		recorder.startRecording();
		module.execute();
		module.execute();
		recorder.stopRecording();
		
		final IPropertyAccesses expectedPropertyAccesses = new PropertyAccesses(
			new PropertyAccess(person, "name"),
			new PropertyAccess(person, "age"),
			new PropertyAccess(person, "name"),
			new PropertyAccess(person, "age")
		);
		
		assertEquals(expectedPropertyAccesses, recorder.getPropertyAccesses());
	}
	
	@SuppressWarnings("unused")
	private static class Person {
		private final String name;
		private final int age;
		private final Person enemy;
		
		public Person(String name, int age) {
			this(name, age, null);
		}
		
		public Person(String name, int age, Person enemy) {
			this.name = name;
			this.age = age;
			this.enemy = enemy;
		}
		
		public String getName() {
			return name;
		}
		
		public int getAge() {
			return age;
		}
		
		public Person getEnemy() {
			return enemy;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}
