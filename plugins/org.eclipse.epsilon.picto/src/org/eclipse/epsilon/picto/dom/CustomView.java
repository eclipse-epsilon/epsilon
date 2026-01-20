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
 * A representation of the model object '<em><b>Custom View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getPath <em>Path</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getIcon <em>Icon</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getFormat <em>Format</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getLayers <em>Layers</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getPatches <em>Patches</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.CustomView#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView()
 * @model
 * @generated
 */
public interface CustomView extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute list.
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Path()
	 * @model
	 * @generated
	 */
	EList<String> getPath();

	/**
	 * Returns the value of the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Icon</em>' attribute.
	 * @see #setIcon(String)
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Icon()
	 * @model
	 * @generated
	 */
	String getIcon();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.picto.dom.CustomView#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon</em>' attribute.
	 * @see #getIcon()
	 * @generated
	 */
	void setIcon(String value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format</em>' attribute.
	 * @see #setFormat(String)
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Format()
	 * @model
	 * @generated
	 */
	String getFormat();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.picto.dom.CustomView#getFormat <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' attribute.
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.picto.dom.CustomView#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Source()
	 * @model
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.picto.dom.CustomView#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

	/**
	 * Returns the value of the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' attribute.
	 * @see #setPosition(Integer)
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Position()
	 * @model
	 * @generated
	 */
	Integer getPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.picto.dom.CustomView#getPosition <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' attribute.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(Integer value);

	/**
	 * Returns the value of the '<em><b>Layers</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layers</em>' attribute list.
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Layers()
	 * @model
	 * @generated
	 */
	EList<String> getLayers();

	/**
	 * Returns the value of the '<em><b>Patches</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.picto.dom.Patch}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patches</em>' containment reference list.
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Patches()
	 * @model containment="true"
	 * @generated
	 */
	EList<Patch> getPatches();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.picto.dom.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.epsilon.picto.dom.PictoPackage#getCustomView_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

} // CustomView
