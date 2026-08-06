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

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.hutn.test.model.families.District;
import org.eclipse.epsilon.hutn.test.model.families.Dog;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.eclipse.epsilon.hutn.test.model.families.Family;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>District</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.DistrictImpl#getFamilies <em>Families</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.impl.DistrictImpl#getDogs <em>Dogs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DistrictImpl extends EObjectImpl implements District {
	/**
	 * The cached value of the '{@link #getFamilies() <em>Families</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFamilies()
	 * @generated
	 * @ordered
	 */
	protected EList<Family> families;

	/**
	 * The cached value of the '{@link #getDogs() <em>Dogs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDogs()
	 * @generated
	 * @ordered
	 */
	protected EList<Dog> dogs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DistrictImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FamiliesPackage.Literals.DISTRICT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Family> getFamilies() {
		if (families == null) {
			families = new EObjectContainmentWithInverseEList<>(Family.class, this, FamiliesPackage.DISTRICT__FAMILIES, FamiliesPackage.FAMILY__DISTRICT);
		}
		return families;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dog> getDogs() {
		if (dogs == null) {
			dogs = new EObjectContainmentWithInverseEList<>(Dog.class, this, FamiliesPackage.DISTRICT__DOGS, FamiliesPackage.DOG__DISTRICT);
		}
		return dogs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FamiliesPackage.DISTRICT__FAMILIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFamilies()).basicAdd(otherEnd, msgs);
			case FamiliesPackage.DISTRICT__DOGS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDogs()).basicAdd(otherEnd, msgs);
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
			case FamiliesPackage.DISTRICT__FAMILIES:
				return ((InternalEList<?>)getFamilies()).basicRemove(otherEnd, msgs);
			case FamiliesPackage.DISTRICT__DOGS:
				return ((InternalEList<?>)getDogs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FamiliesPackage.DISTRICT__FAMILIES:
				return getFamilies();
			case FamiliesPackage.DISTRICT__DOGS:
				return getDogs();
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
			case FamiliesPackage.DISTRICT__FAMILIES:
				getFamilies().clear();
				getFamilies().addAll((Collection<? extends Family>)newValue);
				return;
			case FamiliesPackage.DISTRICT__DOGS:
				getDogs().clear();
				getDogs().addAll((Collection<? extends Dog>)newValue);
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
			case FamiliesPackage.DISTRICT__FAMILIES:
				getFamilies().clear();
				return;
			case FamiliesPackage.DISTRICT__DOGS:
				getDogs().clear();
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
			case FamiliesPackage.DISTRICT__FAMILIES:
				return families != null && !families.isEmpty();
			case FamiliesPackage.DISTRICT__DOGS:
				return dogs != null && !dogs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DistrictImpl
