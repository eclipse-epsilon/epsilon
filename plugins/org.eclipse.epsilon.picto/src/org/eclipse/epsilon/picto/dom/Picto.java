/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
/**
 */
package org.eclipse.epsilon.picto.dom;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Picto</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.picto.dom.Picto#getTransformation <em>Transformation</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.Picto#getFormat <em>Format</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.Picto#isStandalone <em>Standalone</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.Picto#getModels <em>Models</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.Picto#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.Picto#getCustomViews <em>Custom Views</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getPicto()
 * @model
 * @generated
 */
public interface Picto extends EObject {
	/**
	 * Returns the value of the '<em><b>Transformation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformation</em>' attribute.
	 * @see #setTransformation(String)
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getPicto_Transformation()
	 * @model
	 * @generated
	 */
	String getTransformation();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.picto.dom.Picto#getTransformation <em>Transformation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformation</em>' attribute.
	 * @see #getTransformation()
	 * @generated
	 */
	void setTransformation(String value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see #setFormat(String)
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getPicto_Format()
	 * @model
	 * @generated
	 */
	String getFormat();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.picto.dom.Picto#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(String value);

	/**
	 * Returns the value of the '<em><b>Standalone</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Standalone</em>' attribute.
	 * @see #setStandalone(boolean)
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getPicto_Standalone()
	 * @model
	 * @generated
	 */
	boolean isStandalone();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.picto.dom.Picto#isStandalone <em>Standalone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Standalone</em>' attribute.
	 * @see #isStandalone()
	 * @generated
	 */
	void setStandalone(boolean value);

	/**
	 * Returns the value of the '<em><b>Models</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.picto.dom.Model}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Models</em>' containment reference list.
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getPicto_Models()
	 * @model containment="true"
	 * @generated
	 */
	EList<Model> getModels();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.picto.dom.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getPicto_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Custom Views</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.picto.dom.CustomView}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Views</em>' containment reference list.
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getPicto_CustomViews()
	 * @model containment="true"
	 * @generated
	 */
	EList<CustomView> getCustomViews();

} // Picto
