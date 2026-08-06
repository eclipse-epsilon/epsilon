/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.unit;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.egl.merge.TestControlledRegionMerging;
import org.eclipse.epsilon.egl.merge.TestProtectedRegionMerging;
import org.eclipse.epsilon.egl.merge.output.TestOutput;
import org.eclipse.epsilon.egl.merge.partition.TestCompositePartitioner;
import org.eclipse.epsilon.egl.merge.partition.TestEndCommentOnlyPartitioner;
import org.eclipse.epsilon.egl.merge.partition.TestHTMLPartitioner;
import org.eclipse.epsilon.egl.merge.partition.TestJavaPartitioner;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestOutput.class,
               TestHTMLPartitioner.class,
               TestJavaPartitioner.class,
               TestEndCommentOnlyPartitioner.class,
               TestCompositePartitioner.class,
	           TestProtectedRegionMerging.class,
	           TestControlledRegionMerging.class})
public class MergeSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(MergeSuite.class);
	}
}
