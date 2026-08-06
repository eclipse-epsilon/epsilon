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

import org.eclipse.epsilon.hutn.test.model.bankAccounts.Account;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Person#getSharedAccounts <em>Shared Accounts</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Person#getAccounts <em>Accounts</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Person#getFriends <em>Friends</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getPerson()
 * @model
 * @generated
 */
public interface Person extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Shared Accounts</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Accounts</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Accounts</em>' reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getPerson_SharedAccounts()
	 * @model
	 * @generated
	 */
	EList<Account> getSharedAccounts();

	/**
	 * Returns the value of the '<em><b>Accounts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accounts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accounts</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getPerson_Accounts()
	 * @model containment="true"
	 * @generated
	 */
	EList<Account> getAccounts();

	/**
	 * Returns the value of the '<em><b>Friends</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.families.Person}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Friends</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Friends</em>' reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getPerson_Friends()
	 * @model
	 * @generated
	 */
	EList<Person> getFriends();

} // Person
