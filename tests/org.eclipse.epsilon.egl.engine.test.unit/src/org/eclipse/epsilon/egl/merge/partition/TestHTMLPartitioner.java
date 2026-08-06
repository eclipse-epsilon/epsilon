/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.merge.partition;

import org.junit.Test;

public class TestHTMLPartitioner extends TestCommentBlockPartitioner {

	public TestHTMLPartitioner() {
		super(new CommentBlockPartitioner("<!--", "-->"));
	}
	
	@Test @Override
	public void testOutputToString() {
		super.testOutputToString();
	}
	
	@Test @Override
	public void testEmptyOutputToString() {
		super.testEmptyOutputToString();
	}
	
	@Test @Override
	public void testPartitionSingleRegion() {
		super.testPartitionSingleRegion();
	}
	
	@Test @Override
	public void testPartitionSingleProtectedRegion() {
		super.testPartitionSingleProtectedRegion();
	}

	@Test @Override
	public void testPartitionSingleProtectedRegionWithComplexId() {
		super.testPartitionSingleProtectedRegionWithComplexId();
	}
	
	@Test @Override
	public void testPartitionSingleProtectedRegionOff() {
		super.testPartitionSingleProtectedRegionOff();
	}
	
	@Test @Override
	public void testPartitionSingleProtectedRegionEmpty() {
		super.testPartitionSingleProtectedRegionEmpty();
	}
	
	@Test @Override
	public void testPartitionSingleProtectedRegionWhiteSpace() {
		super.testPartitionSingleProtectedRegionWhiteSpace();
	}
	
	@Test @Override
	public void testPartitionRegionThenProtectedRegion() {
		super.testPartitionRegionThenProtectedRegion();
	}
	
	@Test @Override
	public void testPartitionProtectedRegionThenRegion() {
		super.testPartitionProtectedRegionThenRegion();
	}
	
	@Test @Override
	public void testPartitionTwoProtectedRegions() {
		super.testPartitionTwoProtectedRegions();
	}
	
	@Test @Override
	public void testPartitionComplex() {
		super.testPartitionComplex();
	}

}
