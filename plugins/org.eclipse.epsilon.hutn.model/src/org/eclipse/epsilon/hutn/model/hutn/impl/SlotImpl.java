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
 * $Id: SlotImpl.java,v 1.3 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn.impl;

import java.lang.Object;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.Slot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Slot</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.impl.SlotImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.impl.SlotImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.impl.SlotImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SlotImpl<T> extends ModelElementImpl implements Slot<T> {
	/**
	 * The default value of the '{@link #getFeature() <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected static final String FEATURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected String feature = FEATURE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<T> values;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlotImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HutnPackage.Literals.SLOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(String newFeature) {
		String oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HutnPackage.SLOT__FEATURE, oldFeature, feature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassObject getOwner() {
		if (eContainerFeatureID() != HutnPackage.SLOT__OWNER) return null;
		return (ClassObject)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwner(ClassObject newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwner, HutnPackage.SLOT__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwner(ClassObject newOwner) {
		if (newOwner != eInternalContainer() || (eContainerFeatureID() != HutnPackage.SLOT__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, HutnPackage.CLASS_OBJECT__SLOTS, ClassObject.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HutnPackage.SLOT__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<T> getValues() {
		if (values == null) {
			values = new EDataTypeEList<>(Object.class, this, HutnPackage.SLOT__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public abstract boolean typeCompatibleWith(EStructuralFeature structuralFeature);
	
	/**
	 * <!-- begin-user-doc -->
	 * Indicates whether the contents of this Slot has the same
	 * type as the specified EStructuralFeature.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public boolean compatibleWith(EStructuralFeature feature) {
		if (getFeature() == null) return false;
		if (feature.getName() == null) return false;
		
		return getFeature().equals(feature.getName()) &&
		       typeCompatibleWith(feature) &&
		       multiplicityCompatibleWith(feature);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Indicates whether the contents of this Slot can fit in the
	 * specified EStructuralFeature.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public boolean multiplicityCompatibleWith(EStructuralFeature feature) {
		return getNumberOfValues() >= feature.getLowerBound() &&
		       getNumberOfValues() <= feature.getUpperBound();
	}

	/**
	 * * <!-- begin-user-doc -->
	 * Returns the size of the contents of this Slot.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	protected int getNumberOfValues() {
		return getValues().size();
	}

	/**
	 * <!-- begin-user-doc -->
	 * Returns the corresponding EStructuralFeature in the containing 
	 * PackageObject's metamodel.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public EStructuralFeature getEStructuralFeature() {
		if (getOwner() == null)
			return null;
		
		final EClass owner = getOwner().getEClass();
		
		if (getFeature() == null || owner == null)
			return null;
		
		for (EStructuralFeature feature : owner.getEAllStructuralFeatures()) {
			if (getFeature().equals(feature.getName())) {
				return feature;
			}
		}
		
		return null;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Returns true only if this Slot has a corresponding EStructuralFeature
	 * in the containing PackageObject's metamodel.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public boolean hasEStructuralFeature() {
		return getEStructuralFeature() != null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setValues(EList<T> values) {
		getValues().clear();
		getValues().addAll(values);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HutnPackage.SLOT__OWNER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwner((ClassObject)otherEnd, msgs);
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
			case HutnPackage.SLOT__OWNER:
				return basicSetOwner(null, msgs);
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
			case HutnPackage.SLOT__OWNER:
				return eInternalContainer().eInverseRemove(this, HutnPackage.CLASS_OBJECT__SLOTS, ClassObject.class, msgs);
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
			case HutnPackage.SLOT__FEATURE:
				return getFeature();
			case HutnPackage.SLOT__OWNER:
				return getOwner();
			case HutnPackage.SLOT__VALUES:
				return getValues();
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
			case HutnPackage.SLOT__FEATURE:
				setFeature((String)newValue);
				return;
			case HutnPackage.SLOT__OWNER:
				setOwner((ClassObject)newValue);
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
			case HutnPackage.SLOT__FEATURE:
				setFeature(FEATURE_EDEFAULT);
				return;
			case HutnPackage.SLOT__OWNER:
				setOwner((ClassObject)null);
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
			case HutnPackage.SLOT__FEATURE:
				return FEATURE_EDEFAULT == null ? feature != null : !FEATURE_EDEFAULT.equals(feature);
			case HutnPackage.SLOT__OWNER:
				return getOwner() != null;
			case HutnPackage.SLOT__VALUES:
				return values != null && !values.isEmpty();
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
		result.append(" (feature: ");
		result.append(feature);
		result.append(", values: ");
		result.append(values);
		result.append(')');
		return result.toString();
	}

} //SlotImpl
