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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.emc.muddle.Muddle;
import org.eclipse.epsilon.emc.muddle.MuddleElement;
import org.eclipse.epsilon.emc.muddle.MuddleElementStyle;
import org.eclipse.epsilon.emc.muddle.MuddleElementType;
import org.eclipse.epsilon.emc.muddle.MuddlePackage;
import org.eclipse.epsilon.emc.muddle.Slot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl#getSlots <em>Slots</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl#getMuddle <em>Muddle</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl#getStyle <em>Style</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MuddleElementImpl extends MinimalEObjectImpl.Container implements MuddleElement {
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
	 * The cached value of the '{@link #getSlots() <em>Slots</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlots()
	 * @generated
	 * @ordered
	 */
	protected EList<Slot> slots;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected MuddleElementType type;

	/**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
	protected MuddleElementStyle style;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MuddleElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MuddlePackage.Literals.MUDDLE_ELEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Slot> getSlots() {
		if (slots == null) {
			slots = new EObjectContainmentWithInverseEList<>(Slot.class, this, MuddlePackage.MUDDLE_ELEMENT__SLOTS, MuddlePackage.SLOT__OWNING_ELEMENT);
		}
		return slots;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuddleElementType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (MuddleElementType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MuddlePackage.MUDDLE_ELEMENT__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuddleElementType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(MuddleElementType newType, NotificationChain msgs) {
		MuddleElementType oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT__TYPE, oldType, newType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(MuddleElementType newType) {
		if (newType != type) {
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES, MuddleElementType.class, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES, MuddleElementType.class, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Muddle getMuddle() {
		if (eContainerFeatureID() != MuddlePackage.MUDDLE_ELEMENT__MUDDLE) return null;
		return (Muddle)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMuddle(Muddle newMuddle, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newMuddle, MuddlePackage.MUDDLE_ELEMENT__MUDDLE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMuddle(Muddle newMuddle) {
		if (newMuddle != eInternalContainer() || (eContainerFeatureID() != MuddlePackage.MUDDLE_ELEMENT__MUDDLE && newMuddle != null)) {
			if (EcoreUtil.isAncestor(this, newMuddle))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newMuddle != null)
				msgs = ((InternalEObject)newMuddle).eInverseAdd(this, MuddlePackage.MUDDLE__ELEMENTS, Muddle.class, msgs);
			msgs = basicSetMuddle(newMuddle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT__MUDDLE, newMuddle, newMuddle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuddleElementStyle getStyle() {
		return style;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStyle(MuddleElementStyle newStyle, NotificationChain msgs) {
		MuddleElementStyle oldStyle = style;
		style = newStyle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStyle(MuddleElementStyle newStyle) {
		if (newStyle != style) {
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MuddlePackage.MUDDLE_ELEMENT__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MuddlePackage.MUDDLE_ELEMENT__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT__STYLE, newStyle, newStyle));
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
			case MuddlePackage.MUDDLE_ELEMENT__SLOTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSlots()).basicAdd(otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT__TYPE:
				if (type != null)
					msgs = ((InternalEObject)type).eInverseRemove(this, MuddlePackage.MUDDLE_ELEMENT_TYPE__INSTANCES, MuddleElementType.class, msgs);
				return basicSetType((MuddleElementType)otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT__MUDDLE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetMuddle((Muddle)otherEnd, msgs);
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
			case MuddlePackage.MUDDLE_ELEMENT__SLOTS:
				return ((InternalEList<?>)getSlots()).basicRemove(otherEnd, msgs);
			case MuddlePackage.MUDDLE_ELEMENT__TYPE:
				return basicSetType(null, msgs);
			case MuddlePackage.MUDDLE_ELEMENT__MUDDLE:
				return basicSetMuddle(null, msgs);
			case MuddlePackage.MUDDLE_ELEMENT__STYLE:
				return basicSetStyle(null, msgs);
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
			case MuddlePackage.MUDDLE_ELEMENT__MUDDLE:
				return eInternalContainer().eInverseRemove(this, MuddlePackage.MUDDLE__ELEMENTS, Muddle.class, msgs);
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
			case MuddlePackage.MUDDLE_ELEMENT__ID:
				return getId();
			case MuddlePackage.MUDDLE_ELEMENT__SLOTS:
				return getSlots();
			case MuddlePackage.MUDDLE_ELEMENT__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case MuddlePackage.MUDDLE_ELEMENT__MUDDLE:
				return getMuddle();
			case MuddlePackage.MUDDLE_ELEMENT__STYLE:
				return getStyle();
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
			case MuddlePackage.MUDDLE_ELEMENT__ID:
				setId((String)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT__SLOTS:
				getSlots().clear();
				getSlots().addAll((Collection<? extends Slot>)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT__TYPE:
				setType((MuddleElementType)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT__MUDDLE:
				setMuddle((Muddle)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT__STYLE:
				setStyle((MuddleElementStyle)newValue);
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
			case MuddlePackage.MUDDLE_ELEMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case MuddlePackage.MUDDLE_ELEMENT__SLOTS:
				getSlots().clear();
				return;
			case MuddlePackage.MUDDLE_ELEMENT__TYPE:
				setType((MuddleElementType)null);
				return;
			case MuddlePackage.MUDDLE_ELEMENT__MUDDLE:
				setMuddle((Muddle)null);
				return;
			case MuddlePackage.MUDDLE_ELEMENT__STYLE:
				setStyle((MuddleElementStyle)null);
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
			case MuddlePackage.MUDDLE_ELEMENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case MuddlePackage.MUDDLE_ELEMENT__SLOTS:
				return slots != null && !slots.isEmpty();
			case MuddlePackage.MUDDLE_ELEMENT__TYPE:
				return type != null;
			case MuddlePackage.MUDDLE_ELEMENT__MUDDLE:
				return getMuddle() != null;
			case MuddlePackage.MUDDLE_ELEMENT__STYLE:
				return style != null;
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //MuddleElementImpl
