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
 * $Id: HutnAntlrAstFactoryImpl.java,v 1.6 2008/08/14 13:04:22 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.epsilon.hutn.model.hutnAntlrAst.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HutnAntlrAstFactoryImpl extends EFactoryImpl implements HutnAntlrAstFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HutnAntlrAstFactory init() {
		try {
			HutnAntlrAstFactory theHutnAntlrAstFactory = (HutnAntlrAstFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/gmt/epsilon/hutnAntlrAst"); 
			if (theHutnAntlrAstFactory != null) {
				return theHutnAntlrAstFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HutnAntlrAstFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnAntlrAstFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case HutnAntlrAstPackage.ADJECTIVE_NODE: return createAdjectiveNode();
			case HutnAntlrAstPackage.TEXTUAL_VALUE_NODE: return createTextualValueNode();
			case HutnAntlrAstPackage.NUMERIC_VALUE_NODE: return createNumericValueNode();
			case HutnAntlrAstPackage.NAME_NODE: return createNameNode();
			case HutnAntlrAstPackage.NULL_NODE: return createNullNode();
			case HutnAntlrAstPackage.TRUE_NODE: return createTrueNode();
			case HutnAntlrAstPackage.FALSE_NODE: return createFalseNode();
			case HutnAntlrAstPackage.REFERENCE_NODE: return createReferenceNode();
			case HutnAntlrAstPackage.CLASSIFIER_LEVEL_ATTRIBUTE_NODE: return createClassifierLevelAttributeNode();
			case HutnAntlrAstPackage.ASSOCIATION_INSTANCE_NODE: return createAssociationInstanceNode();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdjectiveNode createAdjectiveNode() {
		AdjectiveNodeImpl adjectiveNode = new AdjectiveNodeImpl();
		return adjectiveNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextualValueNode createTextualValueNode() {
		TextualValueNodeImpl textualValueNode = new TextualValueNodeImpl();
		return textualValueNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValueNode createNumericValueNode() {
		NumericValueNodeImpl numericValueNode = new NumericValueNodeImpl();
		return numericValueNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameNode createNameNode() {
		NameNodeImpl nameNode = new NameNodeImpl();
		return nameNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullNode createNullNode() {
		NullNodeImpl nullNode = new NullNodeImpl();
		return nullNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrueNode createTrueNode() {
		TrueNodeImpl trueNode = new TrueNodeImpl();
		return trueNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FalseNode createFalseNode() {
		FalseNodeImpl falseNode = new FalseNodeImpl();
		return falseNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceNode createReferenceNode() {
		ReferenceNodeImpl referenceNode = new ReferenceNodeImpl();
		return referenceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassifierLevelAttributeNode createClassifierLevelAttributeNode() {
		ClassifierLevelAttributeNodeImpl classifierLevelAttributeNode = new ClassifierLevelAttributeNodeImpl();
		return classifierLevelAttributeNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssociationInstanceNode createAssociationInstanceNode() {
		AssociationInstanceNodeImpl associationInstanceNode = new AssociationInstanceNodeImpl();
		return associationInstanceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnAntlrAstPackage getHutnAntlrAstPackage() {
		return (HutnAntlrAstPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HutnAntlrAstPackage getPackage() {
		return HutnAntlrAstPackage.eINSTANCE;
	}

} //HutnAntlrAstFactoryImpl
