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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Accounts</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Accounts#getAccounts <em>Accounts</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsPackage#getAccounts()
 * @model
 * @generated
 */
public interface Accounts extends EObject {
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
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsPackage#getAccounts_Accounts()
	 * @model containment="true"
	 * @generated
	 */
	EList<Account> getAccounts();

} // Accounts
