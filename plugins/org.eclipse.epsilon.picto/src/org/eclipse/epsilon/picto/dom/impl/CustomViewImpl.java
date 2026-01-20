/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
/**
 */
package org.eclipse.epsilon.picto.dom.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.picto.dom.CustomView;
import org.eclipse.epsilon.picto.dom.Parameter;
import org.eclipse.epsilon.picto.dom.Patch;
import org.eclipse.epsilon.picto.dom.PictoPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Custom View</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getPath <em>Path</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getIcon <em>Icon</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getLayers <em>Layers</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getPatches <em>Patches</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomViewImpl extends MinimalEObjectImpl.Container implements CustomView {
	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected EList<String> path;

	/**
	 * The default value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected static final String ICON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected String icon = ICON_EDEFAULT;

	/**
	 * The default value of the '{@link #getFormat() <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFormat() <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormat()
	 * @generated
	 * @ordered
	 */
	protected String format = FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected String source = SOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected static final Integer POSITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Integer position = POSITION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLayers() <em>Layers</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayers()
	 * @generated
	 * @ordered
	 */
	protected EList<String> layers;

	/**
	 * The cached value of the '{@link #getPatches() <em>Patches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatches()
	 * @generated
	 * @ordered
	 */
	protected EList<Patch> patches;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CustomViewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PictoPackage.Literals.CUSTOM_VIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getPath() {
		if (path == null) {
			path = new EDataTypeUniqueEList<String>(String.class, this, PictoPackage.CUSTOM_VIEW__PATH);
		}
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIcon() {
		return icon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIcon(String newIcon) {
		String oldIcon = icon;
		icon = newIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PictoPackage.CUSTOM_VIEW__ICON, oldIcon, icon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFormat() {
		return format;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFormat(String newFormat) {
		String oldFormat = format;
		format = newFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PictoPackage.CUSTOM_VIEW__FORMAT, oldFormat, format));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PictoPackage.CUSTOM_VIEW__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSource(String newSource) {
		String oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PictoPackage.CUSTOM_VIEW__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Integer getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPosition(Integer newPosition) {
		Integer oldPosition = position;
		position = newPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PictoPackage.CUSTOM_VIEW__POSITION, oldPosition, position));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getLayers() {
		if (layers == null) {
			layers = new EDataTypeUniqueEList<String>(String.class, this, PictoPackage.CUSTOM_VIEW__LAYERS);
		}
		return layers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Patch> getPatches() {
		if (patches == null) {
			patches = new EObjectContainmentEList<Patch>(Patch.class, this, PictoPackage.CUSTOM_VIEW__PATCHES);
		}
		return patches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, PictoPackage.CUSTOM_VIEW__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PictoPackage.CUSTOM_VIEW__PATCHES:
				return ((InternalEList<?>)getPatches()).basicRemove(otherEnd, msgs);
			case PictoPackage.CUSTOM_VIEW__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
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
			case PictoPackage.CUSTOM_VIEW__PATH:
				return getPath();
			case PictoPackage.CUSTOM_VIEW__ICON:
				return getIcon();
			case PictoPackage.CUSTOM_VIEW__FORMAT:
				return getFormat();
			case PictoPackage.CUSTOM_VIEW__TYPE:
				return getType();
			case PictoPackage.CUSTOM_VIEW__SOURCE:
				return getSource();
			case PictoPackage.CUSTOM_VIEW__POSITION:
				return getPosition();
			case PictoPackage.CUSTOM_VIEW__LAYERS:
				return getLayers();
			case PictoPackage.CUSTOM_VIEW__PATCHES:
				return getPatches();
			case PictoPackage.CUSTOM_VIEW__PARAMETERS:
				return getParameters();
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
			case PictoPackage.CUSTOM_VIEW__PATH:
				getPath().clear();
				getPath().addAll((Collection<? extends String>)newValue);
				return;
			case PictoPackage.CUSTOM_VIEW__ICON:
				setIcon((String)newValue);
				return;
			case PictoPackage.CUSTOM_VIEW__FORMAT:
				setFormat((String)newValue);
				return;
			case PictoPackage.CUSTOM_VIEW__TYPE:
				setType((String)newValue);
				return;
			case PictoPackage.CUSTOM_VIEW__SOURCE:
				setSource((String)newValue);
				return;
			case PictoPackage.CUSTOM_VIEW__POSITION:
				setPosition((Integer)newValue);
				return;
			case PictoPackage.CUSTOM_VIEW__LAYERS:
				getLayers().clear();
				getLayers().addAll((Collection<? extends String>)newValue);
				return;
			case PictoPackage.CUSTOM_VIEW__PATCHES:
				getPatches().clear();
				getPatches().addAll((Collection<? extends Patch>)newValue);
				return;
			case PictoPackage.CUSTOM_VIEW__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
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
			case PictoPackage.CUSTOM_VIEW__PATH:
				getPath().clear();
				return;
			case PictoPackage.CUSTOM_VIEW__ICON:
				setIcon(ICON_EDEFAULT);
				return;
			case PictoPackage.CUSTOM_VIEW__FORMAT:
				setFormat(FORMAT_EDEFAULT);
				return;
			case PictoPackage.CUSTOM_VIEW__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case PictoPackage.CUSTOM_VIEW__SOURCE:
				setSource(SOURCE_EDEFAULT);
				return;
			case PictoPackage.CUSTOM_VIEW__POSITION:
				setPosition(POSITION_EDEFAULT);
				return;
			case PictoPackage.CUSTOM_VIEW__LAYERS:
				getLayers().clear();
				return;
			case PictoPackage.CUSTOM_VIEW__PATCHES:
				getPatches().clear();
				return;
			case PictoPackage.CUSTOM_VIEW__PARAMETERS:
				getParameters().clear();
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
			case PictoPackage.CUSTOM_VIEW__PATH:
				return path != null && !path.isEmpty();
			case PictoPackage.CUSTOM_VIEW__ICON:
				return ICON_EDEFAULT == null ? icon != null : !ICON_EDEFAULT.equals(icon);
			case PictoPackage.CUSTOM_VIEW__FORMAT:
				return FORMAT_EDEFAULT == null ? format != null : !FORMAT_EDEFAULT.equals(format);
			case PictoPackage.CUSTOM_VIEW__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case PictoPackage.CUSTOM_VIEW__SOURCE:
				return SOURCE_EDEFAULT == null ? source != null : !SOURCE_EDEFAULT.equals(source);
			case PictoPackage.CUSTOM_VIEW__POSITION:
				return POSITION_EDEFAULT == null ? position != null : !POSITION_EDEFAULT.equals(position);
			case PictoPackage.CUSTOM_VIEW__LAYERS:
				return layers != null && !layers.isEmpty();
			case PictoPackage.CUSTOM_VIEW__PATCHES:
				return patches != null && !patches.isEmpty();
			case PictoPackage.CUSTOM_VIEW__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
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
		result.append(" (path: ");
		result.append(path);
		result.append(", icon: ");
		result.append(icon);
		result.append(", format: ");
		result.append(format);
		result.append(", type: ");
		result.append(type);
		result.append(", source: ");
		result.append(source);
		result.append(", position: ");
		result.append(position);
		result.append(", layers: ");
		result.append(layers);
		result.append(')');
		return result.toString();
	}

} //CustomViewImpl
