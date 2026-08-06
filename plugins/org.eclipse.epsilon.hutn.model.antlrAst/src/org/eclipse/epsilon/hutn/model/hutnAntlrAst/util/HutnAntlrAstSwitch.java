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
 * $Id: HutnAntlrAstSwitch.java,v 1.6 2008/08/14 13:04:22 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutnAntlrAst.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;

import org.eclipse.epsilon.hutn.model.hutnAntlrAst.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstPackage
 * @generated
 */
public class HutnAntlrAstSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static HutnAntlrAstPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnAntlrAstSwitch() {
		if (modelPackage == null) {
			modelPackage = HutnAntlrAstPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case HutnAntlrAstPackage.ADJECTIVE_NODE: {
				AdjectiveNode adjectiveNode = (AdjectiveNode)theEObject;
				T result = caseAdjectiveNode(adjectiveNode);
				if (result == null) result = caseNode(adjectiveNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.TEXTUAL_VALUE_NODE: {
				TextualValueNode textualValueNode = (TextualValueNode)theEObject;
				T result = caseTextualValueNode(textualValueNode);
				if (result == null) result = caseNode(textualValueNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.NUMERIC_VALUE_NODE: {
				NumericValueNode numericValueNode = (NumericValueNode)theEObject;
				T result = caseNumericValueNode(numericValueNode);
				if (result == null) result = caseNode(numericValueNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.NAME_NODE: {
				NameNode nameNode = (NameNode)theEObject;
				T result = caseNameNode(nameNode);
				if (result == null) result = caseNode(nameNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.NULL_NODE: {
				NullNode nullNode = (NullNode)theEObject;
				T result = caseNullNode(nullNode);
				if (result == null) result = caseNode(nullNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.TRUE_NODE: {
				TrueNode trueNode = (TrueNode)theEObject;
				T result = caseTrueNode(trueNode);
				if (result == null) result = caseNode(trueNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.FALSE_NODE: {
				FalseNode falseNode = (FalseNode)theEObject;
				T result = caseFalseNode(falseNode);
				if (result == null) result = caseNode(falseNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.REFERENCE_NODE: {
				ReferenceNode referenceNode = (ReferenceNode)theEObject;
				T result = caseReferenceNode(referenceNode);
				if (result == null) result = caseNode(referenceNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.CLASSIFIER_LEVEL_ATTRIBUTE_NODE: {
				ClassifierLevelAttributeNode classifierLevelAttributeNode = (ClassifierLevelAttributeNode)theEObject;
				T result = caseClassifierLevelAttributeNode(classifierLevelAttributeNode);
				if (result == null) result = caseNode(classifierLevelAttributeNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HutnAntlrAstPackage.ASSOCIATION_INSTANCE_NODE: {
				AssociationInstanceNode associationInstanceNode = (AssociationInstanceNode)theEObject;
				T result = caseAssociationInstanceNode(associationInstanceNode);
				if (result == null) result = caseNode(associationInstanceNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adjective Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adjective Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdjectiveNode(AdjectiveNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Textual Value Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Textual Value Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextualValueNode(TextualValueNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Numeric Value Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Numeric Value Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNumericValueNode(NumericValueNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Name Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Name Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNameNode(NameNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Null Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Null Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNullNode(NullNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>True Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>True Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrueNode(TrueNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>False Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>False Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFalseNode(FalseNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reference Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reference Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferenceNode(ReferenceNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Classifier Level Attribute Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Classifier Level Attribute Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassifierLevelAttributeNode(ClassifierLevelAttributeNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association Instance Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association Instance Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationInstanceNode(AssociationInstanceNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //HutnAntlrAstSwitch
