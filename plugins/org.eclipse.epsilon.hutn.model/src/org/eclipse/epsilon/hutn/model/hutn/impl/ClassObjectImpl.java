/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: ClassObjectImpl.java,v 1.3 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObjectSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectImpl#getSlots <em>Slots</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassObjectImpl extends ObjectImpl implements ClassObject {
	/**
	 * The cached value of the '{@link #getSlots() <em>Slots</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlots()
	 * @generated
	 * @ordered
	 */
	protected EList<Slot<?>> slots;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HutnPackage.Literals.CLASS_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Slot<?>> getSlots() {
		if (slots == null) {
			slots = new EObjectContainmentWithInverseEList<>(Slot.class, this, HutnPackage.CLASS_OBJECT__SLOTS, HutnPackage.SLOT__OWNER);
		}
		return slots;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Finds an AttributeSlot contained in this ClassObject with
	 * the specified feature. If this ClassObject contains no such
	 * AttributeSlot, this method returns a new AttributeSlot
	 * with the feature specified. If this ClassObject contains
	 * more than one matching AttributeSlot, this method returns 
	 * any one of the matching AttributeSlots.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Slot<?> findSlot(String feature) {
		for (Slot<?> slot : getSlots()) {
			if (feature.equals(slot.getFeature()))
				return slot;
		}
		
		return null;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Finds an AttributeSlot contained in this ClassObject with
	 * the specified feature. If this ClassObject contains no such
	 * AttributeSlot, this method returns a new AttributeSlot
	 * with the feature specified. If this ClassObject contains
	 * more than one matching AttributeSlot, this method returns 
	 * any one of the matching AttributeSlots.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public AttributeSlot findOrCreateAttributeSlot(String feature) {
		return findOrCreateSlot(feature, AttributeSlot.class, HutnPackage.eINSTANCE.getAttributeSlot());
	}

	/**
	 * <!-- begin-user-doc -->
	 * Finds a ReferenceSlot contained in this ClassObject with
	 * the specified feature. If this ClassObject contains no such
	 * ReferenceSlot, this method returns a new ReferenceSlot
	 * with the feature specified. If this ClassObject contains
	 * more than one matching ReferenceSlot, this method returns 
	 * any one of the matching ReferenceSlots.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ReferenceSlot findOrCreateReferenceSlot(String feature) {
		return findOrCreateSlot(feature, ReferenceSlot.class, HutnPackage.eINSTANCE.getReferenceSlot());
	}

	/**
	 * <!-- begin-user-doc -->
	 * Finds a ContainmentSlot contained in this ClassObject with
	 * the specified feature. If this ClassObject contains no such
	 * ContainmentSlot, this method returns a new ContainmentSlot
	 * with the feature specified. If this ClassObject contains
	 * more than one matching ContainmentSlot, this method returns 
	 * any one of the matching ContainmentSlots.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ContainmentSlot findOrCreateContainmentSlot(String feature) {
		return findOrCreateSlot(feature, ContainmentSlot.class, HutnPackage.eINSTANCE.getContainmentSlot());
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private <T extends Slot<?>> T findOrCreateSlot(String feature, Class<T> type, EClass eClass) {
		final Slot<?> existingSlot = findSlot(feature);

		if (type.isInstance(existingSlot)) {
			return type.cast(existingSlot);
		
		} else {
			final T newSlot = type.cast(HutnFactory.eINSTANCE.create(eClass));
			newSlot.setFeature(feature);
			newSlot.setOwner(this);
			return newSlot;
		}
	}
	

	/**
	 * <!-- begin-user-doc -->
	 * Returns the PackageObject that contains this ClassObject.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PackageObject getPackageObject() {
		if (eContainer() == null) {
			return null;
			
		} else if (eContainer() instanceof PackageObject) {
			return (PackageObject)eContainer();
		
		} else {
			return ((ClassObjectSlot<?>)eContainer()).getOwner().getPackageObject();
		}
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
			case HutnPackage.CLASS_OBJECT__SLOTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSlots()).basicAdd(otherEnd, msgs);
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
			case HutnPackage.CLASS_OBJECT__SLOTS:
				return ((InternalEList<?>)getSlots()).basicRemove(otherEnd, msgs);
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
			case HutnPackage.CLASS_OBJECT__SLOTS:
				return getSlots();
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
			case HutnPackage.CLASS_OBJECT__SLOTS:
				getSlots().clear();
				getSlots().addAll((Collection<? extends Slot<?>>)newValue);
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
			case HutnPackage.CLASS_OBJECT__SLOTS:
				getSlots().clear();
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
			case HutnPackage.CLASS_OBJECT__SLOTS:
				return slots != null && !slots.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * Returns the corresponding EClass in the containing 
	 * PackageObject's metamodel.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public EClass getEClass() {
		if (getType() == null)
			return null;
		
		for (EClass eClass : getPackageObject().getAllEClasses()) {
			if (getType().equals(eClass.getName())) {
				return eClass;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns true only if this Slot has a corresponding EClass
	 * in the containing PackageObject's metamodel.
	 * @model
	 * @generated NOT
	 */
	public boolean hasEClass() {
		return getEClass() != null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Indicates whether every Slot contained in this ClassObject is
	 * type compatible with some EStructuralFeature contained in eClass
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public boolean typeCompatibleWith(EClass eClass) {
		for (Slot<?> slot : getSlots()) {
			boolean foundFeature = false;
			int index = 0;
			
			while (!foundFeature && index < eClass.getEAllStructuralFeatures().size()) {
				final EStructuralFeature feature = eClass.getEAllStructuralFeatures().get(index++);
				
				foundFeature = slot.getFeature().equals(feature.getName()) &&
				               slot.typeCompatibleWith(feature);
			}
			
			if (!foundFeature) return false;
		}
		
		return true;
	}

} //ClassObjectImpl
