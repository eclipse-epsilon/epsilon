/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.epl;

import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;
import org.eclipse.epsilon.evl.EvlModule;

public class EplEvlStandaloneExample {
	
	public static void main(String[] args) throws Exception {
		
		// Load a tree-like XML model
		PlainXmlModel xmlModel = new PlainXmlModel();
		xmlModel.setXml("<node value=\"1\"><node value=\"2\"/></node>");
		xmlModel.load();
		
		// Find parent-child pairs using EPL
		EplModule epl = new EplModule();
		epl.parse("pattern ParentChildPair parent : t_node, child : t_node from: parent.children {}");
		epl.getContext().getModelRepository().addModel(xmlModel);
		
		// The result of pattern matching is a model itself
		PatternMatchModel patternMatchModel = (PatternMatchModel) epl.execute();
		
		// Validate the pattern model with EVL
		EvlModule evl = new EvlModule();
		evl.parse("context ParentChildPair { constraint ParentValueGreaterThanChild { check : self.parent.i_value > self.child.i_value message : self.parent.i_value + ' must be greater than ' + self.child.i_value}}");
		
		// Make both the pattern model and the original model available to EVL
		evl.getContext().getModelRepository().addModel(patternMatchModel);
		evl.getContext().getModelRepository().addModel(xmlModel);
		
		// Execute the EVL constraint
		evl.execute();
		
		// The constraint should fail because the value of the top node is
		// greater than the value of its child
		evl.getContext().getUnsatisfiedConstraints().stream().forEach(uc -> {
			System.out.println(uc.getMessage());
		});
		
	}
	
}
