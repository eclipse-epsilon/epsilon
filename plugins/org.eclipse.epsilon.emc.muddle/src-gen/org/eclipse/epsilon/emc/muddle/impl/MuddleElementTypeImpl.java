/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
/**
 */
package org.eclipse.epsilon.emc.muddle.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.emc.muddle.Feature;
import org.eclipse.epsilon.emc.muddle.MuddleElement;
import org.eclipse.epsilon.emc.muddle.MuddleElementType;
import org.eclipse.epsilon.emc.muddle.MuddlePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementTypeImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementTypeImpl#getFeatures <em>Features</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementTypeImpl#getSuperTypes <em>Super Types</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementTypeImpl#getSubTypes <em>Sub Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MuddleElementTypeImpl extends TypeImpl implements MuddleElementType {
	/**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<MuddleElement> instances;

	/**
	 * The cached value of the '{@link #getFeatures() <em>Features</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> features;

	/**
	 * The cached value of the '{@link #getSuperTypes() <em>Super Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<MuddleElementType> superTypes;

	/**
	 * The cached value of the '{@link #getSubTypes() <em>Sub Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<MuddleElementType> subTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MuddleElementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MuddlePackage.Literals.MUDDLE_ELEMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MuddleElement> getInstances() {
		if (instances == null) {
			instances = new EObjectWithInverseResolvingEList<>(MuddleElement.class, this, MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES, MuddlePackage.MUDDLE_ELEMENT__TYPE);
		}
		return instances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Feature> getFeatures() {
		if (features == null) {
			features = new EObjectContainmentWithInverseEList<>(Feature.class, this, MuddlePackage.MUDDLE_ELEMENT_TYPE__FEATURES, MuddlePackage.FEATURE__OWNING_TYPE);
		}
		return features;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MuddleElementType> getSuperTypes() {
		if (superTypes == null) {
			superTypes = new EObjectWithInverseResolvingEList.ManyInverse<>(MuddleElementType.class, this, MuddlePackage.MUDDLE_ELEMENT_TYPE__SUPER_TYPES, MuddlePackage.MUDDLE_ELEMENT_TYPE__SUB_TYPES);
		}
		return superTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MuddleElementType> getSubTypes() {
		if (subTypes == null) {
			subTypes = new EObjectWithInverseResolvingEList.ManyInverse<>(MuddleElementType.class, this, MuddlePackage.MUDDLE_ELEMENT_TYPE__SUB_TYPES, MuddlePackage.MUDDLE_ELEMENT_TYPE__SUPER_TYPES);
		}
		return subTypes;
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
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInstances()).basicAdd(otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__FEATURES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFeatures()).basicAdd(otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUPER_TYPES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSuperTypes()).basicAdd(otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUB_TYPES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSubTypes()).basicAdd(otherEnd, msgs);
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
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES:
				return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__FEATURES:
				return ((InternalEList<?>)getFeatures()).basicRemove(otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUPER_TYPES:
				return ((InternalEList<?>)getSuperTypes()).basicRemove(otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUB_TYPES:
				return ((InternalEList<?>)getSubTypes()).basicRemove(otherEnd, msgs);
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
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES:
				return getInstances();
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__FEATURES:
				return getFeatures();
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUPER_TYPES:
				return getSuperTypes();
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUB_TYPES:
				return getSubTypes();
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
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends MuddleElement>)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__FEATURES:
				getFeatures().clear();
				getFeatures().addAll((Collection<? extends Feature>)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUPER_TYPES:
				getSuperTypes().clear();
				getSuperTypes().addAll((Collection<? extends MuddleElementType>)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUB_TYPES:
				getSubTypes().clear();
				getSubTypes().addAll((Collection<? extends MuddleElementType>)newValue);
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
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES:
				getInstances().clear();
				return;
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__FEATURES:
				getFeatures().clear();
				return;
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUPER_TYPES:
				getSuperTypes().clear();
				return;
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUB_TYPES:
				getSubTypes().clear();
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
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES:
				return instances != null && !instances.isEmpty();
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__FEATURES:
				return features != null && !features.isEmpty();
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUPER_TYPES:
				return superTypes != null && !superTypes.isEmpty();
			case MuddlePackage.MUDDLE_ELEMENT_TYPE__SUB_TYPES:
				return subTypes != null && !subTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MuddleElementTypeImpl
