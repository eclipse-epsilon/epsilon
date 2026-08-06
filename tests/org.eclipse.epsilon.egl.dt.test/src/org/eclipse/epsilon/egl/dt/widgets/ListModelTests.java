/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.widgets;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class ListModelTests {

	private final ListModel<String> model = new ListModel<>();
	
	@Test
	public void addAnItem() {
		model.add("Foo");
		
		assertContentsOfModelEquals("Foo");
	}
	
	@Test
	public void removeAnItem() {
		initaliseModelWith("Foo", "Bar", "Baz");
		
		model.remove(1);
		
		assertContentsOfModelEquals("Foo", "Baz");
	}
	
	@Test
	public void removeOnlyItem() {
		initaliseModelWith("Foo");
		
		model.remove(0);
		
		assertModelIsEmpty();
	}
	
	@Test
	public void removeSeveralItems() {
		initaliseModelWith("Foo", "Bar", "Baz", "Baaz", "Baaaz");
		
		model.remove(new int[]{2,1,4});
		
		assertContentsOfModelEquals("Foo", "Baaz");
	}
	
	@Test
	public void moveUp() {
		initaliseModelWith("Foo", "Bar", "Baz");
		
		model.moveUp(1);
		
		assertContentsOfModelEquals("Bar", "Foo", "Baz");
	}
	
	@Test
	public void movingUpTopItemChangesNothing() {
		initaliseModelWith("Foo", "Bar", "Baz");
		
		model.moveUp(0);
		
		assertContentsOfModelEquals("Foo", "Bar", "Baz");
	}
	
	@Test
	public void moveUpSeveralItems() {
		initaliseModelWith("Foo", "Bar", "Baz", "Baaz", "Baaaz");
		
		model.moveUp(1,2,4);
		
		assertContentsOfModelEquals("Bar", "Baz", "Foo", "Baaaz", "Baaz");
	}
	
	@Test
	public void movingUpSeveralTopItemsChangesNothing() {
		initaliseModelWith("Foo", "Bar", "Baz", "Baaz", "Baaaz");
		
		model.moveUp(0,1,2);
		
		assertContentsOfModelEquals("Foo", "Bar", "Baz", "Baaz", "Baaaz");
	}
	
	@Test
	public void moveDown() {
		initaliseModelWith("Foo", "Bar", "Baz");
		
		model.moveDown(1);
		
		assertContentsOfModelEquals("Foo", "Baz", "Bar");
	}
	
	@Test
	public void movingDownBottomItemChangesNothing() {
		initaliseModelWith("Foo", "Bar", "Baz");
		
		model.moveDown(2);
		
		assertContentsOfModelEquals("Foo", "Bar", "Baz");
	}
	
	@Test
	public void moveDownSeveralItems() {
		initaliseModelWith("Foo", "Bar", "Baz", "Baaz", "Baaaz");
		
		model.moveDown(0,2,3);
		
		assertContentsOfModelEquals("Bar", "Foo", "Baaaz", "Baz", "Baaz");
	}
	
	@Test
	public void movingDownSeveralBottomItemsChangesNothing() {
		initaliseModelWith("Foo", "Bar", "Baz", "Baaz", "Baaaz");
		
		model.moveDown(2,3,4);
		
		assertContentsOfModelEquals("Foo", "Bar", "Baz", "Baaz", "Baaaz");
	}
	
	
	private void initaliseModelWith(String... items) {
		model.replaceAllWith(Arrays.asList(items));
	}
		
	private void assertModelIsEmpty() {
		assertContentsOfModelEquals(new String[]{});
	}
	
	private void assertContentsOfModelEquals(String... expected) {
		assertEquals(Arrays.asList(expected), model.getItems());
	}
}
