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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Family</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getAddress <em>Address</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getPets <em>Pets</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getNumberOfChildren <em>Number Of Children</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getMembers <em>Members</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#isNuclear <em>Nuclear</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getAverageAge <em>Average Age</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getDogs <em>Dogs</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getDistrict <em>District</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Family#getLotteryNumbers <em>Lottery Numbers</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily()
 * @model
 * @generated
 */
public interface Family extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Address</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Address</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' attribute list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_Address()
	 * @model
	 * @generated
	 */
	EList<String> getAddress();

	/**
	 * Returns the value of the '<em><b>Pets</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.families.Pet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pets</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pets</em>' reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_Pets()
	 * @model
	 * @generated
	 */
	EList<Pet> getPets();

	/**
	 * Returns the value of the '<em><b>Number Of Children</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Children</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Children</em>' attribute.
	 * @see #setNumberOfChildren(int)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_NumberOfChildren()
	 * @model
	 * @generated
	 */
	int getNumberOfChildren();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getNumberOfChildren <em>Number Of Children</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Children</em>' attribute.
	 * @see #getNumberOfChildren()
	 * @generated
	 */
	void setNumberOfChildren(int value);

	/**
	 * Returns the value of the '<em><b>Members</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.families.Person}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Members</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Members</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_Members()
	 * @model containment="true"
	 * @generated
	 */
	EList<Person> getMembers();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Nuclear</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nuclear</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nuclear</em>' attribute.
	 * @see #setNuclear(boolean)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_Nuclear()
	 * @model
	 * @generated
	 */
	boolean isNuclear();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Family#isNuclear <em>Nuclear</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nuclear</em>' attribute.
	 * @see #isNuclear()
	 * @generated
	 */
	void setNuclear(boolean value);

	/**
	 * Returns the value of the '<em><b>Average Age</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Average Age</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Average Age</em>' attribute.
	 * @see #setAverageAge(float)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_AverageAge()
	 * @model
	 * @generated
	 */
	float getAverageAge();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getAverageAge <em>Average Age</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Average Age</em>' attribute.
	 * @see #getAverageAge()
	 * @generated
	 */
	void setAverageAge(float value);

	/**
	 * Returns the value of the '<em><b>Dogs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.families.Dog}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dogs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dogs</em>' reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_Dogs()
	 * @model required="true" changeable="false" derived="true"
	 * @generated
	 */
	EList<Dog> getDogs();

	/**
	 * Returns the value of the '<em><b>District</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.hutn.test.model.families.District#getFamilies <em>Families</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>District</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>District</em>' container reference.
	 * @see #setDistrict(District)
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_District()
	 * @see org.eclipse.epsilon.hutn.test.model.families.District#getFamilies
	 * @model opposite="families" transient="false"
	 * @generated
	 */
	District getDistrict();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getDistrict <em>District</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>District</em>' container reference.
	 * @see #getDistrict()
	 * @generated
	 */
	void setDistrict(District value);

	/**
	 * Returns the value of the '<em><b>Lottery Numbers</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lottery Numbers</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lottery Numbers</em>' attribute list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getFamily_LotteryNumbers()
	 * @model
	 * @generated
	 */
	EList<Integer> getLotteryNumbers();

} // Family
