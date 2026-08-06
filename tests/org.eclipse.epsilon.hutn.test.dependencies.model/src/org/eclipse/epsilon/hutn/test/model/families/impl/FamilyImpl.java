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
package org.eclipse.epsilon.hutn.test.model.families.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.hutn.test.model.families.District;
import org.eclipse.epsilon.hutn.test.model.families.Dog;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.eclipse.epsilon.hutn.test.model.families.Family;
import org.eclipse.epsilon.hutn.test.model.families.Person;
import org.eclipse.epsilon.hutn.test.model.families.Pet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Family</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getPets <em>Pets</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getNumberOfChildren <em>Number Of Children</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getMembers <em>Members</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#isNuclear <em>Nuclear</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getAverageAge <em>Average Age</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getDogs <em>Dogs</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getDistrict <em>District</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.FamilyImpl#getLotteryNumbers <em>Lottery Numbers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FamilyImpl extends NamedElementImpl implements Family {
	/**
	 * The cached value of the '{@link #getAddress() <em>Address</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected EList<String> address;

	/**
	 * The cached value of the '{@link #getPets() <em>Pets</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPets()
	 * @generated
	 * @ordered
	 */
	protected EList<Pet> pets;

	/**
	 * The default value of the '{@link #getNumberOfChildren() <em>Number Of Children</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfChildren()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_CHILDREN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfChildren() <em>Number Of Children</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfChildren()
	 * @generated
	 * @ordered
	 */
	protected int numberOfChildren = NUMBER_OF_CHILDREN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMembers() <em>Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<Person> members;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isNuclear() <em>Nuclear</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNuclear()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NUCLEAR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNuclear() <em>Nuclear</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNuclear()
	 * @generated
	 * @ordered
	 */
	protected boolean nuclear = NUCLEAR_EDEFAULT;

	/**
	 * The default value of the '{@link #getAverageAge() <em>Average Age</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAverageAge()
	 * @generated
	 * @ordered
	 */
	protected static final float AVERAGE_AGE_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getAverageAge() <em>Average Age</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAverageAge()
	 * @generated
	 * @ordered
	 */
	protected float averageAge = AVERAGE_AGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDogs() <em>Dogs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDogs()
	 * @generated
	 * @ordered
	 */
	protected EList<Dog> dogs;

	/**
	 * The cached value of the '{@link #getLotteryNumbers() <em>Lottery Numbers</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLotteryNumbers()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> lotteryNumbers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FamilyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FamiliesPackage.Literals.FAMILY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAddress() {
		if (address == null) {
			address = new EDataTypeUniqueEList<>(String.class, this, FamiliesPackage.FAMILY__ADDRESS);
		}
		return address;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Pet> getPets() {
		if (pets == null) {
			pets = new EObjectResolvingEList<>(Pet.class, this, FamiliesPackage.FAMILY__PETS);
		}
		return pets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfChildren(int newNumberOfChildren) {
		int oldNumberOfChildren = numberOfChildren;
		numberOfChildren = newNumberOfChildren;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__NUMBER_OF_CHILDREN, oldNumberOfChildren, numberOfChildren));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Person> getMembers() {
		if (members == null) {
			members = new EObjectContainmentEList<>(Person.class, this, FamiliesPackage.FAMILY__MEMBERS);
		}
		return members;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNuclear() {
		return nuclear;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNuclear(boolean newNuclear) {
		boolean oldNuclear = nuclear;
		nuclear = newNuclear;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__NUCLEAR, oldNuclear, nuclear));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getAverageAge() {
		return averageAge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAverageAge(float newAverageAge) {
		float oldAverageAge = averageAge;
		averageAge = newAverageAge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__AVERAGE_AGE, oldAverageAge, averageAge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dog> getDogs() {
		if (dogs == null) {
			dogs = new EObjectResolvingEList<>(Dog.class, this, FamiliesPackage.FAMILY__DOGS);
		}
		return dogs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public District getDistrict() {
		if (eContainerFeatureID() != FamiliesPackage.FAMILY__DISTRICT) return null;
		return (District)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDistrict(District newDistrict, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDistrict, FamiliesPackage.FAMILY__DISTRICT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDistrict(District newDistrict) {
		if (newDistrict != eInternalContainer() || (eContainerFeatureID() != FamiliesPackage.FAMILY__DISTRICT && newDistrict != null)) {
			if (EcoreUtil.isAncestor(this, newDistrict))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDistrict != null)
				msgs = ((InternalEObject)newDistrict).eInverseAdd(this, FamiliesPackage.DISTRICT__FAMILIES, District.class, msgs);
			msgs = basicSetDistrict(newDistrict, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.FAMILY__DISTRICT, newDistrict, newDistrict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> getLotteryNumbers() {
		if (lotteryNumbers == null) {
			lotteryNumbers = new EDataTypeUniqueEList<>(Integer.class, this, FamiliesPackage.FAMILY__LOTTERY_NUMBERS);
		}
		return lotteryNumbers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FamiliesPackage.FAMILY__DISTRICT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDistrict((District)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FamiliesPackage.FAMILY__MEMBERS:
				return ((InternalEList<?>)getMembers()).basicRemove(otherEnd, msgs);
			case FamiliesPackage.FAMILY__DISTRICT:
				return basicSetDistrict(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case FamiliesPackage.FAMILY__DISTRICT:
				return eInternalContainer().eInverseRemove(this, FamiliesPackage.DISTRICT__FAMILIES, District.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FamiliesPackage.FAMILY__ADDRESS:
				return getAddress();
			case FamiliesPackage.FAMILY__PETS:
				return getPets();
			case FamiliesPackage.FAMILY__NUMBER_OF_CHILDREN:
				return getNumberOfChildren();
			case FamiliesPackage.FAMILY__MEMBERS:
				return getMembers();
			case FamiliesPackage.FAMILY__ID:
				return getId();
			case FamiliesPackage.FAMILY__NUCLEAR:
				return isNuclear();
			case FamiliesPackage.FAMILY__AVERAGE_AGE:
				return getAverageAge();
			case FamiliesPackage.FAMILY__DOGS:
				return getDogs();
			case FamiliesPackage.FAMILY__DISTRICT:
				return getDistrict();
			case FamiliesPackage.FAMILY__LOTTERY_NUMBERS:
				return getLotteryNumbers();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FamiliesPackage.FAMILY__ADDRESS:
				getAddress().clear();
				getAddress().addAll((Collection<? extends String>)newValue);
				return;
			case FamiliesPackage.FAMILY__PETS:
				getPets().clear();
				getPets().addAll((Collection<? extends Pet>)newValue);
				return;
			case FamiliesPackage.FAMILY__NUMBER_OF_CHILDREN:
				setNumberOfChildren((Integer)newValue);
				return;
			case FamiliesPackage.FAMILY__MEMBERS:
				getMembers().clear();
				getMembers().addAll((Collection<? extends Person>)newValue);
				return;
			case FamiliesPackage.FAMILY__ID:
				setId((String)newValue);
				return;
			case FamiliesPackage.FAMILY__NUCLEAR:
				setNuclear((Boolean)newValue);
				return;
			case FamiliesPackage.FAMILY__AVERAGE_AGE:
				setAverageAge((Float)newValue);
				return;
			case FamiliesPackage.FAMILY__DISTRICT:
				setDistrict((District)newValue);
				return;
			case FamiliesPackage.FAMILY__LOTTERY_NUMBERS:
				getLotteryNumbers().clear();
				getLotteryNumbers().addAll((Collection<? extends Integer>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FamiliesPackage.FAMILY__ADDRESS:
				getAddress().clear();
				return;
			case FamiliesPackage.FAMILY__PETS:
				getPets().clear();
				return;
			case FamiliesPackage.FAMILY__NUMBER_OF_CHILDREN:
				setNumberOfChildren(NUMBER_OF_CHILDREN_EDEFAULT);
				return;
			case FamiliesPackage.FAMILY__MEMBERS:
				getMembers().clear();
				return;
			case FamiliesPackage.FAMILY__ID:
				setId(ID_EDEFAULT);
				return;
			case FamiliesPackage.FAMILY__NUCLEAR:
				setNuclear(NUCLEAR_EDEFAULT);
				return;
			case FamiliesPackage.FAMILY__AVERAGE_AGE:
				setAverageAge(AVERAGE_AGE_EDEFAULT);
				return;
			case FamiliesPackage.FAMILY__DISTRICT:
				setDistrict((District)null);
				return;
			case FamiliesPackage.FAMILY__LOTTERY_NUMBERS:
				getLotteryNumbers().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FamiliesPackage.FAMILY__ADDRESS:
				return address != null && !address.isEmpty();
			case FamiliesPackage.FAMILY__PETS:
				return pets != null && !pets.isEmpty();
			case FamiliesPackage.FAMILY__NUMBER_OF_CHILDREN:
				return numberOfChildren != NUMBER_OF_CHILDREN_EDEFAULT;
			case FamiliesPackage.FAMILY__MEMBERS:
				return members != null && !members.isEmpty();
			case FamiliesPackage.FAMILY__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case FamiliesPackage.FAMILY__NUCLEAR:
				return nuclear != NUCLEAR_EDEFAULT;
			case FamiliesPackage.FAMILY__AVERAGE_AGE:
				return averageAge != AVERAGE_AGE_EDEFAULT;
			case FamiliesPackage.FAMILY__DOGS:
				return dogs != null && !dogs.isEmpty();
			case FamiliesPackage.FAMILY__DISTRICT:
				return getDistrict() != null;
			case FamiliesPackage.FAMILY__LOTTERY_NUMBERS:
				return lotteryNumbers != null && !lotteryNumbers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (address: ");
		result.append(address);
		result.append(", numberOfChildren: ");
		result.append(numberOfChildren);
		result.append(", id: ");
		result.append(id);
		result.append(", nuclear: ");
		result.append(nuclear);
		result.append(", averageAge: ");
		result.append(averageAge);
		result.append(", lotteryNumbers: ");
		result.append(lotteryNumbers);
		result.append(')');
		return result.toString();
	}

} //FamilyImpl
