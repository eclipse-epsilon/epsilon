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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>District</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.District#getFamilies <em>Families</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.District#getDogs <em>Dogs</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getDistrict()
 * @model
 * @generated
 */
public interface District extends EObject {
	/**
	 * Returns the value of the '<em><b>Families</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.families.Family}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getDistrict <em>District</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Families</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Families</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getDistrict_Families()
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getDistrict
	 * @model opposite="district" containment="true" required="true"
	 * @generated
	 */
	EList<Family> getFamilies();

	/**
	 * Returns the value of the '<em><b>Dogs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.families.Dog}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.hutn.test.model.families.Dog#getDistrict <em>District</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dogs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dogs</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getDistrict_Dogs()
	 * @see org.eclipse.epsilon.hutn.test.model.families.Dog#getDistrict
	 * @model opposite="district" containment="true" required="true"
	 * @generated
	 */
	EList<Dog> getDogs();

} // District
