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
package org.eclipse.epsilon.hutn.parse.spec;

import static org.eclipse.epsilon.hutn.util.StringUtil.stripQuotes;

import java.util.Collection;
import java.util.LinkedList;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.eclipse.epsilon.hutn.model.hutn.Spec;

public class HutnSpecAstToSpec {

	private final CommonTree ast;
	
	public HutnSpecAstToSpec(CommonTree ast) {
		this.ast = ast;
	}
	
	public Spec transformPreamble() {
		final Spec spec = HutnFactory.eINSTANCE.createSpec();
		
		for (CommonTree nsUriValueNode : getNsUriValueNodes()) {
			spec.getNsUris().add(createNsUri(stripQuotes(nsUriValueNode.getText())));
		}
			
		return spec;
	}
	
	private Collection<CommonTree> getNsUriValueNodes() {
		final Collection<CommonTree> nsUriValueNodes = new LinkedList<>();
		
		for (Object metamodel : ast.getChildren()) {
			for (Object metamodelFeature : ((CommonTree)metamodel).getChildren()) {
				if ("nsUri".equals(((CommonTree)metamodelFeature).getText())) {
					for (Object nsUriValueNode : ((CommonTree)metamodelFeature).getChildren()) {
						nsUriValueNodes.add((CommonTree)nsUriValueNode);
					}
				}
			}
		}
		
		return nsUriValueNodes;
	}
	
	private static NsUri createNsUri(String value) {
		final NsUri nsUri = HutnFactory.eINSTANCE.createNsUri();
		nsUri.setValue(value);
		return nsUri;
	}
}
