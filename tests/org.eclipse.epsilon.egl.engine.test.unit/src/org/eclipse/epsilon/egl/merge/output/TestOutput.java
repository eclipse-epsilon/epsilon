/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.merge.output;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.egl.merge.output.LocatedRegion;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestOutput {

	private static final List<Region> regions            = new LinkedList<>();
	private static final List<Region> noProtectedRegions = new LinkedList<>();
	private static final List<Region> empty              = new LinkedList<>();

	private static final List<LocatedRegion> protectedRegions = new LinkedList<>();
	
	@BeforeClass
	public static void setUpOnce() {
		final String preamble  = "Preamble";
		final String content   = "Content";
		final String postamble = "Postamble";		
		
		protectedRegions.add(new MockProtectedRegion("first",  true,  "First protected region"));
		protectedRegions.add(new MockProtectedRegion("second", true, "Second protected region"));
		
		regions.add(new Region(preamble));
		regions.add(protectedRegions.get(0));
		regions.add(new Region(content));
		regions.add(protectedRegions.get(1));
		regions.add(new Region(postamble));
		
		noProtectedRegions.add(new Region(preamble));
		noProtectedRegions.add(new Region(content));
		noProtectedRegions.add(new Region(postamble));
	}
	
	@Test
	public void testGetRegions() {
		assertEquals(regions, new Output(regions).getRegions());
	}
	
	@Test
	public void testGetRegionsEmpty() {
		assertEquals(empty, new Output().getRegions());
	}
	
	@Test
	public void testGetProtectedRegions() {
		assertEquals(protectedRegions, new Output(regions).getLocatedRegions());
	}
	
	@Test
	public void testGetProtectedRegionsNone() {
		final List<LocatedRegion> expected = new LinkedList<>();
		
		assertEquals(expected, new Output(noProtectedRegions).getLocatedRegions());
	}
	
	@Test
	public void testGetProtectedRegionsEmpty() {
		final List<LocatedRegion> expected = new LinkedList<>();
		
		assertEquals(expected, new Output().getLocatedRegions());
	}
	
	@Test
	public void testGetProtectedRegion() {
		final Output output = new Output(regions);
		
		for (LocatedRegion pr : protectedRegions) {
			assertEquals(pr, output.getLocatedRegion(pr.getId()));
		}
	}
	
	@Test
	public void testGetProtectedRegionIncorrectID() {
		assertNull(new Output(regions).getLocatedRegion("third"));
	}
	
	
	private static class MockProtectedRegion extends LocatedRegion {

		private MockProtectedRegion(String id, boolean enabled, String contents) {
			super(id, 0, enabled, contents);
		}

		@Override
		public String toString() {
			return getContents();
		}
		
	}
}
