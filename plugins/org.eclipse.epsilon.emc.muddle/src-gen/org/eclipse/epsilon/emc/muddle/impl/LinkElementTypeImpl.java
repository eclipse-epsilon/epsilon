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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.epsilon.emc.muddle.Feature;
import org.eclipse.epsilon.emc.muddle.LinkElementType;
import org.eclipse.epsilon.emc.muddle.MuddlePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link Element Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.LinkElementTypeImpl#getSourceFeature <em>Source Feature</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.LinkElementTypeImpl#getTargetFeature <em>Target Feature</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.LinkElementTypeImpl#getRoleInSourceFeature <em>Role In Source Feature</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.LinkElementTypeImpl#getRoleInTargetFeature <em>Role In Target Feature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LinkElementTypeImpl extends MuddleElementTypeImpl implements LinkElementType {
	/**
	 * The cached value of the '{@link #getSourceFeature() <em>Source Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFeature()
	 * @generated
	 * @ordered
	 */
	protected Feature sourceFeature;

	/**
	 * The cached value of the '{@link #getTargetFeature() <em>Target Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetFeature()
	 * @generated
	 * @ordered
	 */
	protected Feature targetFeature;

	/**
	 * The cached value of the '{@link #getRoleInSourceFeature() <em>Role In Source Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoleInSourceFeature()
	 * @generated
	 * @ordered
	 */
	protected Feature roleInSourceFeature;

	/**
	 * The cached value of the '{@link #getRoleInTargetFeature() <em>Role In Target Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoleInTargetFeature()
	 * @generated
	 * @ordered
	 */
	protected Feature roleInTargetFeature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkElementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MuddlePackage.Literals.LINK_ELEMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature getSourceFeature() {
		if (sourceFeature != null && sourceFeature.eIsProxy()) {
			InternalEObject oldSourceFeature = (InternalEObject)sourceFeature;
			sourceFeature = (Feature)eResolveProxy(oldSourceFeature);
			if (sourceFeature != oldSourceFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MuddlePackage.LINK_ELEMENT_TYPE__SOURCE_FEATURE, oldSourceFeature, sourceFeature));
			}
		}
		return sourceFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature basicGetSourceFeature() {
		return sourceFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceFeature(Feature newSourceFeature) {
		Feature oldSourceFeature = sourceFeature;
		sourceFeature = newSourceFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.LINK_ELEMENT_TYPE__SOURCE_FEATURE, oldSourceFeature, sourceFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature getTargetFeature() {
		if (targetFeature != null && targetFeature.eIsProxy()) {
			InternalEObject oldTargetFeature = (InternalEObject)targetFeature;
			targetFeature = (Feature)eResolveProxy(oldTargetFeature);
			if (targetFeature != oldTargetFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MuddlePackage.LINK_ELEMENT_TYPE__TARGET_FEATURE, oldTargetFeature, targetFeature));
			}
		}
		return targetFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature basicGetTargetFeature() {
		return targetFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetFeature(Feature newTargetFeature) {
		Feature oldTargetFeature = targetFeature;
		targetFeature = newTargetFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.LINK_ELEMENT_TYPE__TARGET_FEATURE, oldTargetFeature, targetFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature getRoleInSourceFeature() {
		return roleInSourceFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoleInSourceFeature(Feature newRoleInSourceFeature, NotificationChain msgs) {
		Feature oldRoleInSourceFeature = roleInSourceFeature;
		roleInSourceFeature = newRoleInSourceFeature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE, oldRoleInSourceFeature, newRoleInSourceFeature);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoleInSourceFeature(Feature newRoleInSourceFeature) {
		if (newRoleInSourceFeature != roleInSourceFeature) {
			NotificationChain msgs = null;
			if (roleInSourceFeature != null)
				msgs = ((InternalEObject)roleInSourceFeature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE, null, msgs);
			if (newRoleInSourceFeature != null)
				msgs = ((InternalEObject)newRoleInSourceFeature).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE, null, msgs);
			msgs = basicSetRoleInSourceFeature(newRoleInSourceFeature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE, newRoleInSourceFeature, newRoleInSourceFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature getRoleInTargetFeature() {
		return roleInTargetFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoleInTargetFeature(Feature newRoleInTargetFeature, NotificationChain msgs) {
		Feature oldRoleInTargetFeature = roleInTargetFeature;
		roleInTargetFeature = newRoleInTargetFeature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE, oldRoleInTargetFeature, newRoleInTargetFeature);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoleInTargetFeature(Feature newRoleInTargetFeature) {
		if (newRoleInTargetFeature != roleInTargetFeature) {
			NotificationChain msgs = null;
			if (roleInTargetFeature != null)
				msgs = ((InternalEObject)roleInTargetFeature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE, null, msgs);
			if (newRoleInTargetFeature != null)
				msgs = ((InternalEObject)newRoleInTargetFeature).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE, null, msgs);
			msgs = basicSetRoleInTargetFeature(newRoleInTargetFeature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE, newRoleInTargetFeature, newRoleInTargetFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE:
				return basicSetRoleInSourceFeature(null, msgs);
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE:
				return basicSetRoleInTargetFeature(null, msgs);
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
			case MuddlePackage.LINK_ELEMENT_TYPE__SOURCE_FEATURE:
				if (resolve) return getSourceFeature();
				return basicGetSourceFeature();
			case MuddlePackage.LINK_ELEMENT_TYPE__TARGET_FEATURE:
				if (resolve) return getTargetFeature();
				return basicGetTargetFeature();
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE:
				return getRoleInSourceFeature();
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE:
				return getRoleInTargetFeature();
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
			case MuddlePackage.LINK_ELEMENT_TYPE__SOURCE_FEATURE:
				setSourceFeature((Feature)newValue);
				return;
			case MuddlePackage.LINK_ELEMENT_TYPE__TARGET_FEATURE:
				setTargetFeature((Feature)newValue);
				return;
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE:
				setRoleInSourceFeature((Feature)newValue);
				return;
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE:
				setRoleInTargetFeature((Feature)newValue);
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
			case MuddlePackage.LINK_ELEMENT_TYPE__SOURCE_FEATURE:
				setSourceFeature((Feature)null);
				return;
			case MuddlePackage.LINK_ELEMENT_TYPE__TARGET_FEATURE:
				setTargetFeature((Feature)null);
				return;
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE:
				setRoleInSourceFeature((Feature)null);
				return;
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE:
				setRoleInTargetFeature((Feature)null);
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
			case MuddlePackage.LINK_ELEMENT_TYPE__SOURCE_FEATURE:
				return sourceFeature != null;
			case MuddlePackage.LINK_ELEMENT_TYPE__TARGET_FEATURE:
				return targetFeature != null;
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE:
				return roleInSourceFeature != null;
			case MuddlePackage.LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE:
				return roleInTargetFeature != null;
		}
		return super.eIsSet(featureID);
	}

} //LinkElementTypeImpl
