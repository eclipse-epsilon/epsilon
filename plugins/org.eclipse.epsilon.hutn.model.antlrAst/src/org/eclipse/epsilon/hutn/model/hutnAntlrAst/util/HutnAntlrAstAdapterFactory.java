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
 * $Id: HutnAntlrAstAdapterFactory.java,v 1.6 2008/08/14 13:04:22 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutnAntlrAst.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;

import org.eclipse.epsilon.hutn.model.hutnAntlrAst.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstPackage
 * @generated
 */
public class HutnAntlrAstAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static HutnAntlrAstPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnAntlrAstAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = HutnAntlrAstPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HutnAntlrAstSwitch<Adapter> modelSwitch =
		new HutnAntlrAstSwitch<Adapter>() {
			@Override
			public Adapter caseAdjectiveNode(AdjectiveNode object) {
				return createAdjectiveNodeAdapter();
			}
			@Override
			public Adapter caseTextualValueNode(TextualValueNode object) {
				return createTextualValueNodeAdapter();
			}
			@Override
			public Adapter caseNumericValueNode(NumericValueNode object) {
				return createNumericValueNodeAdapter();
			}
			@Override
			public Adapter caseNameNode(NameNode object) {
				return createNameNodeAdapter();
			}
			@Override
			public Adapter caseNullNode(NullNode object) {
				return createNullNodeAdapter();
			}
			@Override
			public Adapter caseTrueNode(TrueNode object) {
				return createTrueNodeAdapter();
			}
			@Override
			public Adapter caseFalseNode(FalseNode object) {
				return createFalseNodeAdapter();
			}
			@Override
			public Adapter caseReferenceNode(ReferenceNode object) {
				return createReferenceNodeAdapter();
			}
			@Override
			public Adapter caseClassifierLevelAttributeNode(ClassifierLevelAttributeNode object) {
				return createClassifierLevelAttributeNodeAdapter();
			}
			@Override
			public Adapter caseAssociationInstanceNode(AssociationInstanceNode object) {
				return createAssociationInstanceNodeAdapter();
			}
			@Override
			public Adapter caseNode(Node object) {
				return createNodeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.AdjectiveNode <em>Adjective Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.AdjectiveNode
	 * @generated
	 */
	public Adapter createAdjectiveNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.TextualValueNode <em>Textual Value Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.TextualValueNode
	 * @generated
	 */
	public Adapter createTextualValueNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.NumericValueNode <em>Numeric Value Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.NumericValueNode
	 * @generated
	 */
	public Adapter createNumericValueNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.NameNode <em>Name Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.NameNode
	 * @generated
	 */
	public Adapter createNameNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.NullNode <em>Null Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.NullNode
	 * @generated
	 */
	public Adapter createNullNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.TrueNode <em>True Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.TrueNode
	 * @generated
	 */
	public Adapter createTrueNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.FalseNode <em>False Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.FalseNode
	 * @generated
	 */
	public Adapter createFalseNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.ReferenceNode <em>Reference Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.ReferenceNode
	 * @generated
	 */
	public Adapter createReferenceNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.ClassifierLevelAttributeNode <em>Classifier Level Attribute Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.ClassifierLevelAttributeNode
	 * @generated
	 */
	public Adapter createClassifierLevelAttributeNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.hutn.model.hutnAntlrAst.AssociationInstanceNode <em>Association Instance Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.hutn.model.hutnAntlrAst.AssociationInstanceNode
	 * @generated
	 */
	public Adapter createAssociationInstanceNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node
	 * @generated
	 */
	public Adapter createNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //HutnAntlrAstAdapterFactory
