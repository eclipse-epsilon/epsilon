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
 * A representation of the model object '<em><b>Dog</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Dog#isLoud <em>Loud</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Dog#getBreed <em>Breed</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Dog#getDistrict <em>District</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getDog()
 * @model
 * @generated
 */
public interface Dog extends Pet {
	/**
	 * Returns the value of the '<em><b>Loud</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Loud</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Loud</em>' attribute.
	 * @see #setLoud(boolean)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getDog_Loud()
	 * @model
	 * @generated
	 */
	boolean isLoud();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Dog#isLoud <em>Loud</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Loud</em>' attribute.
	 * @see #isLoud()
	 * @generated
	 */
	void setLoud(boolean value);

	/**
	 * Returns the value of the '<em><b>Breed</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.epsilon.hutn.test.model.families.DogBreed}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Breed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Breed</em>' attribute.
	 * @see org.eclipse.epsilon.hutn.test.model.families.DogBreed
	 * @see #setBreed(DogBreed)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getDog_Breed()
	 * @model required="true"
	 * @generated
	 */
	DogBreed getBreed();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Dog#getBreed <em>Breed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Breed</em>' attribute.
	 * @see org.eclipse.epsilon.hutn.test.model.families.DogBreed
	 * @see #getBreed()
	 * @generated
	 */
	void setBreed(DogBreed value);

	/**
	 * Returns the value of the '<em><b>District</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.hutn.test.model.families.District#getDogs <em>Dogs</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>District</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>District</em>' container reference.
	 * @see #setDistrict(District)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getDog_District()
	 * @see org.eclipse.epsilon.hutn.test.model.families.District#getDogs
	 * @model opposite="dogs" transient="false"
	 * @generated
	 */
	District getDistrict();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Dog#getDistrict <em>District</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>District</em>' container reference.
	 * @see #getDistrict()
	 * @generated
	 */
	void setDistrict(District value);

} // Dog
