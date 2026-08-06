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
 * $Id: HutnAntlrAstFactory.java,v 1.6 2008/08/14 13:04:22 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutnAntlrAst;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstPackage
 * @generated
 */
public interface HutnAntlrAstFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HutnAntlrAstFactory eINSTANCE = org.eclipse.epsilon.hutn.model.hutnAntlrAst.impl.HutnAntlrAstFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Adjective Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adjective Node</em>'.
	 * @generated
	 */
	AdjectiveNode createAdjectiveNode();

	/**
	 * Returns a new object of class '<em>Textual Value Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Textual Value Node</em>'.
	 * @generated
	 */
	TextualValueNode createTextualValueNode();

	/**
	 * Returns a new object of class '<em>Numeric Value Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numeric Value Node</em>'.
	 * @generated
	 */
	NumericValueNode createNumericValueNode();

	/**
	 * Returns a new object of class '<em>Name Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Name Node</em>'.
	 * @generated
	 */
	NameNode createNameNode();

	/**
	 * Returns a new object of class '<em>Null Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Null Node</em>'.
	 * @generated
	 */
	NullNode createNullNode();

	/**
	 * Returns a new object of class '<em>True Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>True Node</em>'.
	 * @generated
	 */
	TrueNode createTrueNode();

	/**
	 * Returns a new object of class '<em>False Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>False Node</em>'.
	 * @generated
	 */
	FalseNode createFalseNode();

	/**
	 * Returns a new object of class '<em>Reference Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reference Node</em>'.
	 * @generated
	 */
	ReferenceNode createReferenceNode();

	/**
	 * Returns a new object of class '<em>Classifier Level Attribute Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Classifier Level Attribute Node</em>'.
	 * @generated
	 */
	ClassifierLevelAttributeNode createClassifierLevelAttributeNode();

	/**
	 * Returns a new object of class '<em>Association Instance Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Association Instance Node</em>'.
	 * @generated
	 */
	AssociationInstanceNode createAssociationInstanceNode();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	HutnAntlrAstPackage getHutnAntlrAstPackage();

} //HutnAntlrAstFactory
