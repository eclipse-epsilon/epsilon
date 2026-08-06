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
package org.eclipse.epsilon.hutn.test.model.bankAccounts.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.epsilon.hutn.test.model.bankAccounts.Account;
import org.eclipse.epsilon.hutn.test.model.bankAccounts.Accounts;
import org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsFactory;
import org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsPackage;

import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;

import org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BankAccountsPackageImpl extends EPackageImpl implements BankAccountsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass accountsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass accountEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BankAccountsPackageImpl() {
		super(eNS_URI, BankAccountsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link BankAccountsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BankAccountsPackage init() {
		if (isInited) return (BankAccountsPackage)EPackage.Registry.INSTANCE.getEPackage(BankAccountsPackage.eNS_URI);

		// Obtain or create and register package
		BankAccountsPackageImpl theBankAccountsPackage = (BankAccountsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BankAccountsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BankAccountsPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		FamiliesPackageImpl theFamiliesPackage = (FamiliesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FamiliesPackage.eNS_URI) instanceof FamiliesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FamiliesPackage.eNS_URI) : FamiliesPackage.eINSTANCE);

		// Create package meta-data objects
		theBankAccountsPackage.createPackageContents();
		theFamiliesPackage.createPackageContents();

		// Initialize created meta-data
		theBankAccountsPackage.initializePackageContents();
		theFamiliesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBankAccountsPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BankAccountsPackage.eNS_URI, theBankAccountsPackage);
		return theBankAccountsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAccounts() {
		return accountsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAccounts_Accounts() {
		return (EReference)accountsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAccount() {
		return accountEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAccount_SortCode() {
		return (EAttribute)accountEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAccount_Number() {
		return (EAttribute)accountEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BankAccountsFactory getBankAccountsFactory() {
		return (BankAccountsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		accountsEClass = createEClass(ACCOUNTS);
		createEReference(accountsEClass, ACCOUNTS__ACCOUNTS);

		accountEClass = createEClass(ACCOUNT);
		createEAttribute(accountEClass, ACCOUNT__SORT_CODE);
		createEAttribute(accountEClass, ACCOUNT__NUMBER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(accountsEClass, Accounts.class, "Accounts", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAccounts_Accounts(), this.getAccount(), null, "accounts", null, 0, -1, Accounts.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(accountEClass, Account.class, "Account", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAccount_SortCode(), ecorePackage.getEString(), "sortCode", null, 0, 1, Account.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAccount_Number(), ecorePackage.getEString(), "number", null, 0, 1, Account.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //BankAccountsPackageImpl
