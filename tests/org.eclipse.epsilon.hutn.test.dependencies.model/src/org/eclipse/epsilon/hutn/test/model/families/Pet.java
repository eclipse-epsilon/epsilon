/**
 * *******************************************************************************
 *  Copyright (c) 2008 The University of York.
 *  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which is available at https://www.eclipse.org/legal/epl-2.0/
 *  Contributors:
 *    Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.test.model.families;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Pet#isMale <em>Male</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getPet()
 * @model
 * @generated
 */
public interface Pet extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Male</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Male</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Male</em>' attribute.
	 * @see #setMale(boolean)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getPet_Male()
	 * @model
	 * @generated
	 */
	boolean isMale();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Pet#isMale <em>Male</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Male</em>' attribute.
	 * @see #isMale()
	 * @generated
	 */
	void setMale(boolean value);

} // Pet
