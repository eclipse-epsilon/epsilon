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
 * $Id: HutnAntlrAstPackageImpl.java,v 1.6 2008/08/14 13:04:22 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.AntlrAstPackage;

import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.impl.AntlrAstPackageImpl;

import org.eclipse.epsilon.hutn.model.hutnAntlrAst.AdjectiveNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.AssociationInstanceNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.ClassifierLevelAttributeNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.FalseNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstFactory;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstPackage;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.NameNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.NullNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.NumericValueNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.ReferenceNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.TextualValueNode;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.TrueNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HutnAntlrAstPackageImpl extends EPackageImpl implements HutnAntlrAstPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adjectiveNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textualValueNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericValueNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nullNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass trueNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass falseNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierLevelAttributeNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationInstanceNodeEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private HutnAntlrAstPackageImpl() {
		super(eNS_URI, HutnAntlrAstFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static HutnAntlrAstPackage init() {
		if (isInited) return (HutnAntlrAstPackage)EPackage.Registry.INSTANCE.getEPackage(HutnAntlrAstPackage.eNS_URI);

		// Obtain or create and register package
		HutnAntlrAstPackageImpl theHutnAntlrAstPackage = (HutnAntlrAstPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof HutnAntlrAstPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new HutnAntlrAstPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		AntlrAstPackageImpl theAntlrAstPackage = (AntlrAstPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AntlrAstPackage.eNS_URI) instanceof AntlrAstPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AntlrAstPackage.eNS_URI) : AntlrAstPackage.eINSTANCE);

		// Create package meta-data objects
		theHutnAntlrAstPackage.createPackageContents();
		theAntlrAstPackage.createPackageContents();

		// Initialize created meta-data
		theHutnAntlrAstPackage.initializePackageContents();
		theAntlrAstPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theHutnAntlrAstPackage.freeze();

		return theHutnAntlrAstPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdjectiveNode() {
		return adjectiveNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextualValueNode() {
		return textualValueNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumericValueNode() {
		return numericValueNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNameNode() {
		return nameNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNullNode() {
		return nullNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTrueNode() {
		return trueNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFalseNode() {
		return falseNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReferenceNode() {
		return referenceNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassifierLevelAttributeNode() {
		return classifierLevelAttributeNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationInstanceNode() {
		return associationInstanceNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnAntlrAstFactory getHutnAntlrAstFactory() {
		return (HutnAntlrAstFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		adjectiveNodeEClass = createEClass(ADJECTIVE_NODE);

		textualValueNodeEClass = createEClass(TEXTUAL_VALUE_NODE);

		numericValueNodeEClass = createEClass(NUMERIC_VALUE_NODE);

		nameNodeEClass = createEClass(NAME_NODE);

		nullNodeEClass = createEClass(NULL_NODE);

		trueNodeEClass = createEClass(TRUE_NODE);

		falseNodeEClass = createEClass(FALSE_NODE);

		referenceNodeEClass = createEClass(REFERENCE_NODE);

		classifierLevelAttributeNodeEClass = createEClass(CLASSIFIER_LEVEL_ATTRIBUTE_NODE);

		associationInstanceNodeEClass = createEClass(ASSOCIATION_INSTANCE_NODE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		AntlrAstPackage theAntlrAstPackage = (AntlrAstPackage)EPackage.Registry.INSTANCE.getEPackage(AntlrAstPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		adjectiveNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		textualValueNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		numericValueNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		nameNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		nullNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		trueNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		falseNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		referenceNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		classifierLevelAttributeNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());
		associationInstanceNodeEClass.getESuperTypes().add(theAntlrAstPackage.getNode());

		// Initialize classes and features; add operations and parameters
		initEClass(adjectiveNodeEClass, AdjectiveNode.class, "AdjectiveNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(textualValueNodeEClass, TextualValueNode.class, "TextualValueNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(numericValueNodeEClass, NumericValueNode.class, "NumericValueNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nameNodeEClass, NameNode.class, "NameNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nullNodeEClass, NullNode.class, "NullNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(trueNodeEClass, TrueNode.class, "TrueNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(falseNodeEClass, FalseNode.class, "FalseNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(referenceNodeEClass, ReferenceNode.class, "ReferenceNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(classifierLevelAttributeNodeEClass, ClassifierLevelAttributeNode.class, "ClassifierLevelAttributeNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(associationInstanceNodeEClass, AssociationInstanceNode.class, "AssociationInstanceNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //HutnAntlrAstPackageImpl
