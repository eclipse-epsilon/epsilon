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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.epsilon.hutn.test.model.families.District;
import org.eclipse.epsilon.hutn.test.model.families.Dog;
import org.eclipse.epsilon.hutn.test.model.families.DogBreed;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dog</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.DogImpl#isLoud <em>Loud</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.DogImpl#getBreed <em>Breed</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.DogImpl#getDistrict <em>District</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DogImpl extends PetImpl implements Dog {
	/**
	 * The default value of the '{@link #isLoud() <em>Loud</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoud()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOUD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLoud() <em>Loud</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoud()
	 * @generated
	 * @ordered
	 */
	protected boolean loud = LOUD_EDEFAULT;

	/**
	 * The default value of the '{@link #getBreed() <em>Breed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBreed()
	 * @generated
	 * @ordered
	 */
	protected static final DogBreed BREED_EDEFAULT = DogBreed.POODLE;

	/**
	 * The cached value of the '{@link #getBreed() <em>Breed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBreed()
	 * @generated
	 * @ordered
	 */
	protected DogBreed breed = BREED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DogImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FamiliesPackage.Literals.DOG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLoud() {
		return loud;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoud(boolean newLoud) {
		boolean oldLoud = loud;
		loud = newLoud;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.DOG__LOUD, oldLoud, loud));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DogBreed getBreed() {
		return breed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBreed(DogBreed newBreed) {
		DogBreed oldBreed = breed;
		breed = newBreed == null ? BREED_EDEFAULT : newBreed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.DOG__BREED, oldBreed, breed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public District getDistrict() {
		if (eContainerFeatureID() != FamiliesPackage.DOG__DISTRICT) return null;
		return (District)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDistrict(District newDistrict, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDistrict, FamiliesPackage.DOG__DISTRICT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDistrict(District newDistrict) {
		if (newDistrict != eInternalContainer() || (eContainerFeatureID() != FamiliesPackage.DOG__DISTRICT && newDistrict != null)) {
			if (EcoreUtil.isAncestor(this, newDistrict))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDistrict != null)
				msgs = ((InternalEObject)newDistrict).eInverseAdd(this, FamiliesPackage.DISTRICT__DOGS, District.class, msgs);
			msgs = basicSetDistrict(newDistrict, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FamiliesPackage.DOG__DISTRICT, newDistrict, newDistrict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FamiliesPackage.DOG__DISTRICT:
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
			case FamiliesPackage.DOG__DISTRICT:
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
			case FamiliesPackage.DOG__DISTRICT:
				return eInternalContainer().eInverseRemove(this, FamiliesPackage.DISTRICT__DOGS, District.class, msgs);
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
			case FamiliesPackage.DOG__LOUD:
				return isLoud();
			case FamiliesPackage.DOG__BREED:
				return getBreed();
			case FamiliesPackage.DOG__DISTRICT:
				return getDistrict();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FamiliesPackage.DOG__LOUD:
				setLoud((Boolean)newValue);
				return;
			case FamiliesPackage.DOG__BREED:
				setBreed((DogBreed)newValue);
				return;
			case FamiliesPackage.DOG__DISTRICT:
				setDistrict((District)newValue);
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
			case FamiliesPackage.DOG__LOUD:
				setLoud(LOUD_EDEFAULT);
				return;
			case FamiliesPackage.DOG__BREED:
				setBreed(BREED_EDEFAULT);
				return;
			case FamiliesPackage.DOG__DISTRICT:
				setDistrict((District)null);
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
			case FamiliesPackage.DOG__LOUD:
				return loud != LOUD_EDEFAULT;
			case FamiliesPackage.DOG__BREED:
				return breed != BREED_EDEFAULT;
			case FamiliesPackage.DOG__DISTRICT:
				return getDistrict() != null;
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
		result.append(" (loud: ");
		result.append(loud);
		result.append(", breed: ");
		result.append(breed);
		result.append(')');
		return result.toString();
	}

} //DogImpl
