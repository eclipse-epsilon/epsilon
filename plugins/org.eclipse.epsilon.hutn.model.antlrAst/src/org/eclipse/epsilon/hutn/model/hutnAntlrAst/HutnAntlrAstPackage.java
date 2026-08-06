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
 * $Id: HutnAntlrAstPackage.java,v 1.6 2008/08/14 13:04:22 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutnAntlrAst;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.AntlrAstPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstFactory
 * @model kind="package"
 * @generated
 */
public interface HutnAntlrAstPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "hutnAntlrAst";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/gmt/epsilon/hutnAntlrAst";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "hutnAntlrAst";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HutnAntlrAstPackage eINSTANCE = org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.AdjectiveNodeImpl <em>Adjective Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.AdjectiveNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getAdjectiveNode()
	 * @generated
	 */
	int ADJECTIVE_NODE = 0;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJECTIVE_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJECTIVE_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJECTIVE_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJECTIVE_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJECTIVE_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>Adjective Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJECTIVE_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.TextualValueNodeImpl <em>Textual Value Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.TextualValueNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getTextualValueNode()
	 * @generated
	 */
	int TEXTUAL_VALUE_NODE = 1;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXTUAL_VALUE_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXTUAL_VALUE_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXTUAL_VALUE_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXTUAL_VALUE_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXTUAL_VALUE_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>Textual Value Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXTUAL_VALUE_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NumericValueNodeImpl <em>Numeric Value Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NumericValueNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getNumericValueNode()
	 * @generated
	 */
	int NUMERIC_VALUE_NODE = 2;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>Numeric Value Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NameNodeImpl <em>Name Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NameNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getNameNode()
	 * @generated
	 */
	int NAME_NODE = 3;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>Name Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NullNodeImpl <em>Null Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NullNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getNullNode()
	 * @generated
	 */
	int NULL_NODE = 4;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>Null Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.TrueNodeImpl <em>True Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.TrueNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getTrueNode()
	 * @generated
	 */
	int TRUE_NODE = 5;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>True Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.FalseNodeImpl <em>False Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.FalseNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getFalseNode()
	 * @generated
	 */
	int FALSE_NODE = 6;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FALSE_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FALSE_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FALSE_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FALSE_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FALSE_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>False Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FALSE_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.ReferenceNodeImpl <em>Reference Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.ReferenceNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getReferenceNode()
	 * @generated
	 */
	int REFERENCE_NODE = 7;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>Reference Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.ClassifierLevelAttributeNodeImpl <em>Classifier Level Attribute Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.ClassifierLevelAttributeNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getClassifierLevelAttributeNode()
	 * @generated
	 */
	int CLASSIFIER_LEVEL_ATTRIBUTE_NODE = 8;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_LEVEL_ATTRIBUTE_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_LEVEL_ATTRIBUTE_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_LEVEL_ATTRIBUTE_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_LEVEL_ATTRIBUTE_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_LEVEL_ATTRIBUTE_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>Classifier Level Attribute Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_LEVEL_ATTRIBUTE_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.AssociationInstanceNodeImpl <em>Association Instance Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.AssociationInstanceNodeImpl
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getAssociationInstanceNode()
	 * @generated
	 */
	int ASSOCIATION_INSTANCE_NODE = 9;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_INSTANCE_NODE__TEXT = AntlrAstPackage.NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_INSTANCE_NODE__LINE = AntlrAstPackage.NODE__LINE;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_INSTANCE_NODE__COLUMN = AntlrAstPackage.NODE__COLUMN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_INSTANCE_NODE__PARENT = AntlrAstPackage.NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_INSTANCE_NODE__CHILDREN = AntlrAstPackage.NODE__CHILDREN;

	/**
	 * The number of structural features of the '<em>Association Instance Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_INSTANCE_NODE_FEATURE_COUNT = AntlrAstPackage.NODE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.AdjectiveNode <em>Adjective Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adjective Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.AdjectiveNode
	 * @generated
	 */
	EClass getAdjectiveNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.TextualValueNode <em>Textual Value Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Textual Value Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.TextualValueNode
	 * @generated
	 */
	EClass getTextualValueNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.NumericValueNode <em>Numeric Value Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Value Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.NumericValueNode
	 * @generated
	 */
	EClass getNumericValueNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.NameNode <em>Name Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.NameNode
	 * @generated
	 */
	EClass getNameNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.NullNode <em>Null Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Null Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.NullNode
	 * @generated
	 */
	EClass getNullNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.TrueNode <em>True Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>True Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.TrueNode
	 * @generated
	 */
	EClass getTrueNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.FalseNode <em>False Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>False Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.FalseNode
	 * @generated
	 */
	EClass getFalseNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.ReferenceNode <em>Reference Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.ReferenceNode
	 * @generated
	 */
	EClass getReferenceNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.ClassifierLevelAttributeNode <em>Classifier Level Attribute Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classifier Level Attribute Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.ClassifierLevelAttributeNode
	 * @generated
	 */
	EClass getClassifierLevelAttributeNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.AssociationInstanceNode <em>Association Instance Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Association Instance Node</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.AssociationInstanceNode
	 * @generated
	 */
	EClass getAssociationInstanceNode();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HutnAntlrAstFactory getHutnAntlrAstFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.AdjectiveNodeImpl <em>Adjective Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.AdjectiveNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getAdjectiveNode()
		 * @generated
		 */
		EClass ADJECTIVE_NODE = eINSTANCE.getAdjectiveNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.TextualValueNodeImpl <em>Textual Value Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.TextualValueNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getTextualValueNode()
		 * @generated
		 */
		EClass TEXTUAL_VALUE_NODE = eINSTANCE.getTextualValueNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NumericValueNodeImpl <em>Numeric Value Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NumericValueNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getNumericValueNode()
		 * @generated
		 */
		EClass NUMERIC_VALUE_NODE = eINSTANCE.getNumericValueNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NameNodeImpl <em>Name Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NameNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getNameNode()
		 * @generated
		 */
		EClass NAME_NODE = eINSTANCE.getNameNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NullNodeImpl <em>Null Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.NullNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getNullNode()
		 * @generated
		 */
		EClass NULL_NODE = eINSTANCE.getNullNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.TrueNodeImpl <em>True Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.TrueNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getTrueNode()
		 * @generated
		 */
		EClass TRUE_NODE = eINSTANCE.getTrueNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.FalseNodeImpl <em>False Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.FalseNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getFalseNode()
		 * @generated
		 */
		EClass FALSE_NODE = eINSTANCE.getFalseNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.ReferenceNodeImpl <em>Reference Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.ReferenceNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getReferenceNode()
		 * @generated
		 */
		EClass REFERENCE_NODE = eINSTANCE.getReferenceNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.ClassifierLevelAttributeNodeImpl <em>Classifier Level Attribute Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.ClassifierLevelAttributeNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getClassifierLevelAttributeNode()
		 * @generated
		 */
		EClass CLASSIFIER_LEVEL_ATTRIBUTE_NODE = eINSTANCE.getClassifierLevelAttributeNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.AssociationInstanceNodeImpl <em>Association Instance Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.AssociationInstanceNodeImpl
		 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstPackageImpl#getAssociationInstanceNode()
		 * @generated
		 */
		EClass ASSOCIATION_INSTANCE_NODE = eINSTANCE.getAssociationInstanceNode();

	}

} //HutnAntlrAstPackage
