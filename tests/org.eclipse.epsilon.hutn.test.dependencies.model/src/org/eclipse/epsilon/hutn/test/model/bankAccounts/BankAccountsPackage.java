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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsFactory
 * @model kind="package"
 * @generated
 */
public interface BankAccountsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "bankAccounts";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "bankAccounts";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "bankAccounts";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BankAccountsPackage eINSTANCE = org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.BankAccountsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.AccountsImpl <em>Accounts</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.AccountsImpl
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.BankAccountsPackageImpl#getAccounts()
	 * @generated
	 */
	int ACCOUNTS = 0;

	/**
	 * The feature id for the '<em><b>Accounts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTS__ACCOUNTS = 0;

	/**
	 * The number of structural features of the '<em>Accounts</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNTS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.AccountImpl <em>Account</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.AccountImpl
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.BankAccountsPackageImpl#getAccount()
	 * @generated
	 */
	int ACCOUNT = 1;

	/**
	 * The feature id for the '<em><b>Sort Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__SORT_CODE = 0;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT__NUMBER = 1;

	/**
	 * The number of structural features of the '<em>Account</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCOUNT_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Accounts <em>Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Accounts</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.Accounts
	 * @generated
	 */
	EClass getAccounts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Accounts#getAccounts <em>Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Accounts</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.Accounts#getAccounts()
	 * @see #getAccounts()
	 * @generated
	 */
	EReference getAccounts_Accounts();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account <em>Account</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Account</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.Account
	 * @generated
	 */
	EClass getAccount();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account#getSortCode <em>Sort Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sort Code</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.Account#getSortCode()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_SortCode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.Account#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.Account#getNumber()
	 * @see #getAccount()
	 * @generated
	 */
	EAttribute getAccount_Number();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BankAccountsFactory getBankAccountsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.AccountsImpl <em>Accounts</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.AccountsImpl
		 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.BankAccountsPackageImpl#getAccounts()
		 * @generated
		 */
		EClass ACCOUNTS = eINSTANCE.getAccounts();

		/**
		 * The meta object literal for the '<em><b>Accounts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNTS__ACCOUNTS = eINSTANCE.getAccounts_Accounts();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.AccountImpl <em>Account</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.AccountImpl
		 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.impl.BankAccountsPackageImpl#getAccount()
		 * @generated
		 */
		EClass ACCOUNT = eINSTANCE.getAccount();

		/**
		 * The meta object literal for the '<em><b>Sort Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__SORT_CODE = eINSTANCE.getAccount_SortCode();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__NUMBER = eINSTANCE.getAccount_Number();

	}

} //BankAccountsPackage
