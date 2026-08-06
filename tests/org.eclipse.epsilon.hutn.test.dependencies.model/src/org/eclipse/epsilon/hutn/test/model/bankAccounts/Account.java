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
package org.eclipse.epsilon.hutn.test.model.bankAccounts;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Account</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account#getSortCode <em>Sort Code</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account#getNumber <em>Number</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsPackage#getAccount()
 * @model
 * @generated
 */
public interface Account extends EObject {
	/**
	 * Returns the value of the '<em><b>Sort Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sort Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sort Code</em>' attribute.
	 * @see #setSortCode(String)
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsPackage#getAccount_SortCode()
	 * @model
	 * @generated
	 */
	String getSortCode();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account#getSortCode <em>Sort Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort Code</em>' attribute.
	 * @see #getSortCode()
	 * @generated
	 */
	void setSortCode(String value);

	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(String)
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsPackage#getAccount_Number()
	 * @model
	 * @generated
	 */
	String getNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(String value);

} // Account
