/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
/**
 */
package org.eclipse.epsilon.emc.muddle;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.Feature#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.Feature#isMany <em>Many</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.Feature#isPrimary <em>Primary</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.Feature#isRuntime <em>Runtime</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.Feature#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.Feature#getOwningType <em>Owning Type</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.Feature#getSlots <em>Slots</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getFeature()
 * @model
 * @generated
 */
public interface Feature extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getFeature_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.Feature#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Many</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Many</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Many</em>' attribute.
	 * @see #setMany(boolean)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getFeature_Many()
	 * @model default="false"
	 * @generated
	 */
	boolean isMany();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.Feature#isMany <em>Many</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Many</em>' attribute.
	 * @see #isMany()
	 * @generated
	 */
	void setMany(boolean value);

	/**
	 * Returns the value of the '<em><b>Primary</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary</em>' attribute.
	 * @see #setPrimary(boolean)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getFeature_Primary()
	 * @model default="false"
	 * @generated
	 */
	boolean isPrimary();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.Feature#isPrimary <em>Primary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary</em>' attribute.
	 * @see #isPrimary()
	 * @generated
	 */
	void setPrimary(boolean value);

	/**
	 * Returns the value of the '<em><b>Runtime</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Runtime</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Runtime</em>' attribute.
	 * @see #setRuntime(boolean)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getFeature_Runtime()
	 * @model default="false"
	 * @generated
	 */
	boolean isRuntime();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.Feature#isRuntime <em>Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Runtime</em>' attribute.
	 * @see #isRuntime()
	 * @generated
	 */
	void setRuntime(boolean value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Type)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getFeature_Type()
	 * @model
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.Feature#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

	/**
	 * Returns the value of the '<em><b>Owning Type</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.muddle.MuddleElementType#getFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owning Type</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owning Type</em>' container reference.
	 * @see #setOwningType(MuddleElementType)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getFeature_OwningType()
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementType#getFeatures
	 * @model opposite="features" transient="false"
	 * @generated
	 */
	MuddleElementType getOwningType();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.Feature#getOwningType <em>Owning Type</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owning Type</em>' container reference.
	 * @see #getOwningType()
	 * @generated
	 */
	void setOwningType(MuddleElementType value);

	/**
	 * Returns the value of the '<em><b>Slots</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.emc.muddle.Slot}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.muddle.Slot#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slots</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slots</em>' reference list.
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getFeature_Slots()
	 * @see org.eclipse.epsilon.emc.muddle.Slot#getFeature
	 * @model opposite="feature"
	 * @generated
	 */
	EList<Slot> getSlots();

} // Feature
