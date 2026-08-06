/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.antlr.postprocessor;

import java.util.Collections;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.AntlrAstFactory;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.eclipse.epsilon.common.parse.AST;


public abstract class AbstractAstPostProcessor {

	private static List<? extends AST> asList(AST ast) {
		// AST will be nil if there are many packages
		if (ast.isNil()) {
			return ast.getChildren();
			
		// Otherwise, it will contain a single package
		// and hence a single token
		} else {
			return Collections.singletonList(ast);
		}
	}
	
	private static Ast initialiseAstModel() {
		final Ast astModel = AntlrAstFactory.eINSTANCE.createAst();
		
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());

		final Resource resource = resourceSet.createResource(URI.createFileURI("default.ecore"));
		resource.getContents().add(astModel);
		
		return astModel;
	}
	
	public Ast process(AST ast) {
		final Ast astModel = initialiseAstModel();
		
		if (ast != null) {
			for (CommonTree root : asList(ast)) {
				final Node node = createNodeFor(root);
				
				if (node != null) {
					astModel.getRoots().add(node); 
				}
			}
		}
		
		return astModel;
	}

	private Node createNodeFor(CommonTree ast) {
		if (ast == null) return null;
		
		final Node node = createNodeObjectFor(ast);
				
		if (node != null) {
			node.setText(ast.getText());
			node.setLine(ast.getLine());
			node.setColumn(ast.getCharPositionInLine());
			
			if (ast.getChildCount() > 0) {
				for (Object child : ast.getChildren()) {
					node.getChildren().add(createNodeFor((CommonTree)child));
				}
			}
		}
		
		return node;
	}

	protected abstract Node createNodeObjectFor(CommonTree ast);
}
