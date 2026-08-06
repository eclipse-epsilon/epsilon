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
 * $Id: HutnTranslatorTest.java,v 1.7 2008/08/14 13:04:18 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstFactory;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.AntlrAstFactory;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;

public abstract class HutnTranslatorTest {
	
	protected static final String CONFIG_FILE = "../org.eclipse.epsilon.hutn.test.dependencies.model/models/org/eclipse/epsilon/hutn/test/models/FamiliesConfig.model";
	
	// 287042 - Must register a resource factory for EmfModelResourceFactory to work 
	@BeforeClass
	public static void registerDefaultResourceFactory() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
	} 
	
	protected static ModelWithEolAssertions translatorTest(Ast ast) throws Exception {
		final Spec spec = new HutnTranslator().createIntermediateModel(ast, null);
		
		final AbstractEmfModel specModel = new InMemoryEmfModel(EmfUtil.createResource(spec));
		
		final ModelWithEolAssertions model = new ModelWithEolAssertions(specModel);
		
		model.assertEquals(1, "Spec.allInstances().size()");
		model.setVariable("spec", "Spec.allInstances().first()");
		
		return model;
	}
	
	protected static Ast initialiseAst() {
		final Ast ast = AntlrAstFactory.eINSTANCE.createAst();
		EmfUtil.createResource(ast);
		
		return ast;
	}
	
	protected static Node createPackage(String name) {
		return createPackage(name, 1, 1);
	}
	
	protected static Node createPackage(String name, int line, int col) {
		final Node node = HutnAntlrAstFactory.eINSTANCE.createNameNode();
		return setNodeAttributes(node, name, line, col);
	}
	
	protected static Node createClass(String name) {
		return createClass(name, null);
	}
	
	protected static Node createClass(String name, String identifier, String... adjectives) {
		final Node cls = createPackage(name, 1, 1);
		
		for (String adjective : adjectives) {
			cls.getChildren().add(createAdjective(adjective));
		}
		
		if (identifier != null) {
			cls.getChildren().add(createIdentifier(identifier));
		}
		
		return cls;
	}
	
	protected static Node createClass(String name, String identifier, int line, int col) {
		final Node cls = createPackage(name, line, col);
		cls.getChildren().add(createIdentifier(identifier));
		return cls;
	}
	
	protected static Node createIdentifier(String identifier) {
		final Node node = HutnAntlrAstFactory.eINSTANCE.createTextualValueNode();
		return setNodeAttributes(node, '"' + identifier + '"', 1, 1);
	}
	
	protected static Node createNullAttribute(String name) {
		return wrapValueNodeWithAttribute(name, HutnAntlrAstFactory.eINSTANCE.createNullNode());
	}
	
	protected static Node createEnumerationAttribute(String name, String value) {
		final Node valueNode = HutnAntlrAstFactory.eINSTANCE.createNameNode();
		valueNode.setText(value);
		
		return wrapValueNodeWithAttribute(name, valueNode);
	}
	
	protected static Node createAttribute(String name, Object... values) {
		if (values.length > 0) {
			final List<Node> valueNodes = new LinkedList<>();
			
			for (Object value : values) {
				final Node valueNode = getNodeObjectFor(value);
				
				if (value instanceof String)
					valueNode.setText('"' + value.toString() + '"');
				else
					valueNode.setText(value.toString());
					
				valueNodes.add(valueNode);
			}
		
			return wrapValueNodeWithAttribute(name, valueNodes.toArray(new Node[]{}));
			
		} else {
			return null;
		}
	}
	
	protected static Node createClassifierLevelAttribute(String classifier, String attribute, Object... values) {
		final Node attributeNode = createAttribute(attribute, values);
		
		final Node classNode = createPackage(classifier);
		classNode.getChildren().add(attributeNode);
		
		final Node claNode = HutnAntlrAstFactory.eINSTANCE.createClassifierLevelAttributeNode();
		claNode.getChildren().add(classNode);
		
		return claNode;
	}
	
	protected static Node getNodeObjectFor(Object value) {
		if (value instanceof String) {
			return HutnAntlrAstFactory.eINSTANCE.createTextualValueNode();
		
		} else if (value instanceof Integer ||
		           value instanceof Long ||
		           value instanceof Float ||
		           value instanceof Double) {
			
			return HutnAntlrAstFactory.eINSTANCE.createNumericValueNode();
		
		} else if (value instanceof Boolean) {
			return ((Boolean)value) ? 
			       HutnAntlrAstFactory.eINSTANCE.createTrueNode() :
			       HutnAntlrAstFactory.eINSTANCE.createFalseNode();
			
		} else {
			throw new IllegalArgumentException("Node type not known for instances of " + value.getClass().getName());
		}
	}
	
	private static Node wrapValueNodeWithAttribute(String name, Node... valueNodes) {
		final Node node = HutnAntlrAstFactory.eINSTANCE.createNameNode();
		node.setText(name);
		
		for (Node valueNode : valueNodes)
			node.getChildren().add(valueNode);
		
		return node;
	}
	
	protected static Node createAdjective(String text) {
		final Node adjectiveNode = HutnAntlrAstFactory.eINSTANCE.createAdjectiveNode();
		adjectiveNode.setText(text);
		return adjectiveNode;
	}
	
	private static List<Node> createReferenceNodes(Reference... references) {
		final List<Node> referenceNodes = new LinkedList<>();
		
		for (Reference reference : references) {
			final Node identifierNode = HutnAntlrAstFactory.eINSTANCE.createTextualValueNode();
			identifierNode.setText('"' + reference.getIdentifier() + '"');
			
			final Node typeNode = HutnAntlrAstFactory.eINSTANCE.createReferenceNode();
			typeNode.setText(reference.getType());
			typeNode.getChildren().add(identifierNode);
			
			referenceNodes.add(typeNode);
		}
		
		return referenceNodes;
	}
	
	protected static Node createReference(String name, Reference... references) {
		if (references.length > 0) {
			return wrapValueNodeWithAttribute(name, createReferenceNodes(references).toArray(new Node[]{}));
			
		} else {
			return null;
		}
	}
	
	protected static Node createContainment(String name, Node... containedObjects) {
		if (containedObjects.length > 0) {		
			return wrapValueNodeWithAttribute(name, containedObjects);
		} else {
			return null;
		}
	}
	
	protected static Node createInfixAssociation(String name, Association association) {
		return createAssociationBlock(name, association);
	}
	
	protected static Node createAssociationBlock(String name, Association... associations) {
		final Node assocBlockNode = HutnAntlrAstFactory.eINSTANCE.createAssociationInstanceNode();
		assocBlockNode.setText(name);
		
		for (Association association : associations) {
			for (Node referenceNode : createReferenceNodes(association.getReferences())) {
				assocBlockNode.getChildren().add(referenceNode);
			}
		}	
		
		return assocBlockNode;
	}
	
	private static Node setNodeAttributes(Node node, String text, int line, int col) {
		node.setText(text);
		node.setLine(line);
		node.setColumn(col);
		return node;
	}
	
	
	protected static class Reference {
		private final String type;
		private final String identifier;
		
		public Reference(String type, String identifier) {
			this.type       = type;
			this.identifier = identifier;
		}
		
		public String getType() {
			return type;
		}
		
		public String getIdentifier() {
			return identifier;
		}
	}
	
	protected static class Association {
		private final Reference source;
		private final Reference destination;
		
		public Association(Reference source, Reference destination) {
			this.source      = source;
			this.destination = destination;
		}
		
		public Reference getSource() {
			return source;
		}
		
		public Reference getDestination() {
			return destination;
		}
		
		public Reference[] getReferences() {
			return new Reference[]{source, destination};
		}
	}
}
