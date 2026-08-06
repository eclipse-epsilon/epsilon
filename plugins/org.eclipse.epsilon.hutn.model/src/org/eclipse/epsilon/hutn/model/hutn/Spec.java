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
 * $Id: Spec.java,v 1.3 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Spec</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getNsUris <em>Ns Uris</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getObjects <em>Objects</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getModelFile <em>Model File</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getSourceFile <em>Source File</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSpec()
 * @model
 * @generated
 */
public interface Spec extends EObject {
	/**
	 * Returns the value of the '<em><b>Ns Uris</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.model.hutn.NsUri}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ns Uris</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ns Uris</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSpec_NsUris()
	 * @model containment="true"
	 * @generated
	 */
	EList<NsUri> getNsUris();

	/**
	 * Returns the value of the '<em><b>Objects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.model.hutn.PackageObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objects</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSpec_Objects()
	 * @model containment="true"
	 * @generated
	 */
	EList<PackageObject> getObjects();

	/**
	 * Returns the value of the '<em><b>Model File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model File</em>' attribute.
	 * @see #setModelFile(String)
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSpec_ModelFile()
	 * @model
	 * @generated
	 */
	String getModelFile();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getModelFile <em>Model File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model File</em>' attribute.
	 * @see #getModelFile()
	 * @generated
	 */
	void setModelFile(String value);

	/**
	 * Returns the value of the '<em><b>Source File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source File</em>' attribute.
	 * @see #setSourceFile(String)
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSpec_SourceFile()
	 * @model
	 * @generated
	 */
	String getSourceFile();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getSourceFile <em>Source File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source File</em>' attribute.
	 * @see #getSourceFile()
	 * @generated
	 */
	void setSourceFile(String value);

} // Spec
