/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: PostProcessorSuite.java,v 1.3 2008/08/12 12:59:13 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.unit;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.parse.postprocessor.EmptyAst;
import org.eclipse.epsilon.hutn.parse.postprocessor.ExplicitPositiveAdjectiveIsDeprecated;
import org.eclipse.epsilon.hutn.parse.postprocessor.NodeTypes;
import org.eclipse.epsilon.hutn.parse.postprocessor.NodeWithChild;
import org.eclipse.epsilon.hutn.parse.postprocessor.NodeWithChildren;
import org.eclipse.epsilon.hutn.parse.postprocessor.SingleNode;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EmptyAst.class, SingleNode.class, NodeTypes.class,
               NodeWithChild.class, NodeWithChildren.class, 
               ExplicitPositiveAdjectiveIsDeprecated.class})
public class PostProcessorSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(PostProcessorSuite.class);
	}
}
