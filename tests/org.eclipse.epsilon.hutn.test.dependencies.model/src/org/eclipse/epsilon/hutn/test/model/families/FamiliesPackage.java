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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesFactory
 * @model kind="package"
 * @generated
 */
public interface FamiliesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "families";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "families";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "families";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FamiliesPackage eINSTANCE = org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.NamedElementImpl
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl <em>Family</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getFamily()
	 * @generated
	 */
	int FAMILY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__ADDRESS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__PETS = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Number Of Children</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__NUMBER_OF_CHILDREN = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__MEMBERS = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__ID = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Nuclear</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__NUCLEAR = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Average Age</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__AVERAGE_AGE = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Dogs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__DOGS = NAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>District</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__DISTRICT = NAMED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Lottery Numbers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY__LOTTERY_NUMBERS = NAMED_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Family</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAMILY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.PetImpl <em>Pet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.PetImpl
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getPet()
	 * @generated
	 */
	int PET = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PET__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Male</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PET__MALE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PET_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.PersonImpl <em>Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.PersonImpl
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getPerson()
	 * @generated
	 */
	int PERSON = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Shared Accounts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__SHARED_ACCOUNTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Accounts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__ACCOUNTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Friends</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__FRIENDS = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.DogImpl <em>Dog</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.DogImpl
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getDog()
	 * @generated
	 */
	int DOG = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOG__NAME = PET__NAME;

	/**
	 * The feature id for the '<em><b>Male</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOG__MALE = PET__MALE;

	/**
	 * The feature id for the '<em><b>Loud</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOG__LOUD = PET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Breed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOG__BREED = PET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>District</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOG__DISTRICT = PET_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Dog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOG_FEATURE_COUNT = PET_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.DistrictImpl <em>District</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.DistrictImpl
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getDistrict()
	 * @generated
	 */
	int DISTRICT = 5;

	/**
	 * The feature id for the '<em><b>Families</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISTRICT__FAMILIES = 0;

	/**
	 * The feature id for the '<em><b>Dogs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISTRICT__DOGS = 1;

	/**
	 * The number of structural features of the '<em>District</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISTRICT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.ModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.ModelImpl
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 6;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__CONTENTS = 0;

	/**
	 * The feature id for the '<em><b>Contents2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__CONTENTS2 = 1;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.BikeImpl <em>Bike</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.BikeImpl
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getBike()
	 * @generated
	 */
	int BIKE = 7;

	/**
	 * The feature id for the '<em><b>Rider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIKE__RIDER = 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIKE__OWNER = 1;

	/**
	 * The number of structural features of the '<em>Bike</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIKE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.test.model.families.DogBreed <em>Dog Breed</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.test.model.families.DogBreed
	 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getDogBreed()
	 * @generated
	 */
	int DOG_BREED = 8;


	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.families.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.families.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.families.Family <em>Family</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Family</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family
	 * @generated
	 */
	EClass getFamily();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Address</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getAddress()
	 * @see #getFamily()
	 * @generated
	 */
	EAttribute getFamily_Address();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getPets <em>Pets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Pets</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getPets()
	 * @see #getFamily()
	 * @generated
	 */
	EReference getFamily_Pets();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getNumberOfChildren <em>Number Of Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Children</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getNumberOfChildren()
	 * @see #getFamily()
	 * @generated
	 */
	EAttribute getFamily_NumberOfChildren();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getMembers <em>Members</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Members</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getMembers()
	 * @see #getFamily()
	 * @generated
	 */
	EReference getFamily_Members();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getId()
	 * @see #getFamily()
	 * @generated
	 */
	EAttribute getFamily_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.families.Family#isNuclear <em>Nuclear</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nuclear</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#isNuclear()
	 * @see #getFamily()
	 * @generated
	 */
	EAttribute getFamily_Nuclear();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getAverageAge <em>Average Age</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Average Age</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getAverageAge()
	 * @see #getFamily()
	 * @generated
	 */
	EAttribute getFamily_AverageAge();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getDogs <em>Dogs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dogs</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getDogs()
	 * @see #getFamily()
	 * @generated
	 */
	EReference getFamily_Dogs();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getDistrict <em>District</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>District</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getDistrict()
	 * @see #getFamily()
	 * @generated
	 */
	EReference getFamily_District();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.hutn.test.model.families.Family#getLotteryNumbers <em>Lottery Numbers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Lottery Numbers</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Family#getLotteryNumbers()
	 * @see #getFamily()
	 * @generated
	 */
	EAttribute getFamily_LotteryNumbers();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.families.Pet <em>Pet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pet</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Pet
	 * @generated
	 */
	EClass getPet();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.families.Pet#isMale <em>Male</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Male</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Pet#isMale()
	 * @see #getPet()
	 * @generated
	 */
	EAttribute getPet_Male();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.families.Person <em>Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Person</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Person
	 * @generated
	 */
	EClass getPerson();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.hutn.test.model.families.Person#getSharedAccounts <em>Shared Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Shared Accounts</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Person#getSharedAccounts()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_SharedAccounts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.test.model.families.Person#getAccounts <em>Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Accounts</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Person#getAccounts()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_Accounts();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.hutn.test.model.families.Person#getFriends <em>Friends</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Friends</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Person#getFriends()
	 * @see #getPerson()
	 * @generated
	 */
	EReference getPerson_Friends();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.families.Dog <em>Dog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dog</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Dog
	 * @generated
	 */
	EClass getDog();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.families.Dog#isLoud <em>Loud</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Loud</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Dog#isLoud()
	 * @see #getDog()
	 * @generated
	 */
	EAttribute getDog_Loud();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.test.model.families.Dog#getBreed <em>Breed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Breed</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Dog#getBreed()
	 * @see #getDog()
	 * @generated
	 */
	EAttribute getDog_Breed();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.hutn.test.model.families.Dog#getDistrict <em>District</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>District</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Dog#getDistrict()
	 * @see #getDog()
	 * @generated
	 */
	EReference getDog_District();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.families.District <em>District</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>District</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.District
	 * @generated
	 */
	EClass getDistrict();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.test.model.families.District#getFamilies <em>Families</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Families</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.District#getFamilies()
	 * @see #getDistrict()
	 * @generated
	 */
	EReference getDistrict_Families();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.test.model.families.District#getDogs <em>Dogs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dogs</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.District#getDogs()
	 * @see #getDistrict()
	 * @generated
	 */
	EReference getDistrict_Dogs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.families.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Model
	 * @generated
	 */
	EClass getModel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.test.model.families.Model#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Model#getContents()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Contents();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.test.model.families.Model#getContents2 <em>Contents2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents2</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Model#getContents2()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Contents2();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.test.model.families.Bike <em>Bike</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bike</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Bike
	 * @generated
	 */
	EClass getBike();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.epsilon.hutn.test.model.families.Bike#getRider <em>Rider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rider</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Bike#getRider()
	 * @see #getBike()
	 * @generated
	 */
	EReference getBike_Rider();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.epsilon.hutn.test.model.families.Bike#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.Bike#getOwner()
	 * @see #getBike()
	 * @generated
	 */
	EReference getBike_Owner();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.epsilon.hutn.test.model.families.DogBreed <em>Dog Breed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Dog Breed</em>'.
	 * @see org.eclipse.epsilon.hutn.test.model.families.DogBreed
	 * @generated
	 */
	EEnum getDogBreed();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FamiliesFactory getFamiliesFactory();

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
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.NamedElementImpl
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl <em>Family</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getFamily()
		 * @generated
		 */
		EClass FAMILY = eINSTANCE.getFamily();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY__ADDRESS = eINSTANCE.getFamily_Address();

		/**
		 * The meta object literal for the '<em><b>Pets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FAMILY__PETS = eINSTANCE.getFamily_Pets();

		/**
		 * The meta object literal for the '<em><b>Number Of Children</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY__NUMBER_OF_CHILDREN = eINSTANCE.getFamily_NumberOfChildren();

		/**
		 * The meta object literal for the '<em><b>Members</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FAMILY__MEMBERS = eINSTANCE.getFamily_Members();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY__ID = eINSTANCE.getFamily_Id();

		/**
		 * The meta object literal for the '<em><b>Nuclear</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY__NUCLEAR = eINSTANCE.getFamily_Nuclear();

		/**
		 * The meta object literal for the '<em><b>Average Age</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY__AVERAGE_AGE = eINSTANCE.getFamily_AverageAge();

		/**
		 * The meta object literal for the '<em><b>Dogs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FAMILY__DOGS = eINSTANCE.getFamily_Dogs();

		/**
		 * The meta object literal for the '<em><b>District</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FAMILY__DISTRICT = eINSTANCE.getFamily_District();

		/**
		 * The meta object literal for the '<em><b>Lottery Numbers</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FAMILY__LOTTERY_NUMBERS = eINSTANCE.getFamily_LotteryNumbers();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.PetImpl <em>Pet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.PetImpl
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getPet()
		 * @generated
		 */
		EClass PET = eINSTANCE.getPet();

		/**
		 * The meta object literal for the '<em><b>Male</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PET__MALE = eINSTANCE.getPet_Male();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.PersonImpl <em>Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.PersonImpl
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getPerson()
		 * @generated
		 */
		EClass PERSON = eINSTANCE.getPerson();

		/**
		 * The meta object literal for the '<em><b>Shared Accounts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__SHARED_ACCOUNTS = eINSTANCE.getPerson_SharedAccounts();

		/**
		 * The meta object literal for the '<em><b>Accounts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__ACCOUNTS = eINSTANCE.getPerson_Accounts();

		/**
		 * The meta object literal for the '<em><b>Friends</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON__FRIENDS = eINSTANCE.getPerson_Friends();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.DogImpl <em>Dog</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.DogImpl
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getDog()
		 * @generated
		 */
		EClass DOG = eINSTANCE.getDog();

		/**
		 * The meta object literal for the '<em><b>Loud</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOG__LOUD = eINSTANCE.getDog_Loud();

		/**
		 * The meta object literal for the '<em><b>Breed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOG__BREED = eINSTANCE.getDog_Breed();

		/**
		 * The meta object literal for the '<em><b>District</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOG__DISTRICT = eINSTANCE.getDog_District();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.DistrictImpl <em>District</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.DistrictImpl
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getDistrict()
		 * @generated
		 */
		EClass DISTRICT = eINSTANCE.getDistrict();

		/**
		 * The meta object literal for the '<em><b>Families</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DISTRICT__FAMILIES = eINSTANCE.getDistrict_Families();

		/**
		 * The meta object literal for the '<em><b>Dogs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DISTRICT__DOGS = eINSTANCE.getDistrict_Dogs();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.ModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.ModelImpl
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getModel()
		 * @generated
		 */
		EClass MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__CONTENTS = eINSTANCE.getModel_Contents();

		/**
		 * The meta object literal for the '<em><b>Contents2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__CONTENTS2 = eINSTANCE.getModel_Contents2();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.impl.BikeImpl <em>Bike</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.BikeImpl
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getBike()
		 * @generated
		 */
		EClass BIKE = eINSTANCE.getBike();

		/**
		 * The meta object literal for the '<em><b>Rider</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BIKE__RIDER = eINSTANCE.getBike_Rider();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BIKE__OWNER = eINSTANCE.getBike_Owner();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.test.model.families.DogBreed <em>Dog Breed</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.test.model.families.DogBreed
		 * @see org.eclipse.epsilon.hutn.test.model.families.impl.FamiliesPackageImpl#getDogBreed()
		 * @generated
		 */
		EEnum DOG_BREED = eINSTANCE.getDogBreed();

	}

} //FamiliesPackage
