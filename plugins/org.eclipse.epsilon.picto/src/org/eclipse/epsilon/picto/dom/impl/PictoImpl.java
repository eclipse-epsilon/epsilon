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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.picto.dom.CustomView;
import org.eclipse.epsilon.picto.dom.Model;
import org.eclipse.epsilon.picto.dom.Parameter;
import org.eclipse.epsilon.picto.dom.Picto;
import org.eclipse.epsilon.picto.dom.PictoPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Picto</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.PictoImpl#getTransformation <em>Transformation</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.PictoImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.PictoImpl#isStandalone <em>Standalone</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.PictoImpl#getModels <em>Models</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.PictoImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.epsilon.picto.dom.impl.PictoImpl#getCustomViews <em>Custom Views</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PictoImpl extends MinimalEObjectImpl.Container implements Picto {
	/**
	 * The default value of the '{@link #getTransformation() <em>Transformation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformation()
	 * @generated
	 * @ordered
	 */
	protected static final String TRANSFORMATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransformation() <em>Transformation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformation()
	 * @generated
	 * @ordered
	 */
	protected String transformation = TRANSFORMATION_EDEFAULT;

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
	 * The default value of the '{@link #isStandalone() <em>Standalone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStandalone()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STANDALONE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStandalone() <em>Standalone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStandalone()
	 * @generated
	 * @ordered
	 */
	protected boolean standalone = STANDALONE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModels() <em>Models</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModels()
	 * @generated
	 * @ordered
	 */
	protected EList<Model> models;

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
	 * The cached value of the '{@link #getCustomViews() <em>Custom Views</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomViews()
	 * @generated
	 * @ordered
	 */
	protected EList<CustomView> customViews;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PictoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PictoPackage.Literals.PICTO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTransformation() {
		return transformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransformation(String newTransformation) {
		String oldTransformation = transformation;
		transformation = newTransformation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PictoPackage.PICTO__TRANSFORMATION, oldTransformation, transformation));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PictoPackage.PICTO__FORMAT, oldFormat, format));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStandalone() {
		return standalone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStandalone(boolean newStandalone) {
		boolean oldStandalone = standalone;
		standalone = newStandalone;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PictoPackage.PICTO__STANDALONE, oldStandalone, standalone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Model> getModels() {
		if (models == null) {
			models = new EObjectContainmentEList<Model>(Model.class, this, PictoPackage.PICTO__MODELS);
		}
		return models;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, PictoPackage.PICTO__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CustomView> getCustomViews() {
		if (customViews == null) {
			customViews = new EObjectContainmentEList<CustomView>(CustomView.class, this, PictoPackage.PICTO__CUSTOM_VIEWS);
		}
		return customViews;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PictoPackage.PICTO__MODELS:
				return ((InternalEList<?>)getModels()).basicRemove(otherEnd, msgs);
			case PictoPackage.PICTO__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case PictoPackage.PICTO__CUSTOM_VIEWS:
				return ((InternalEList<?>)getCustomViews()).basicRemove(otherEnd, msgs);
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
			case PictoPackage.PICTO__TRANSFORMATION:
				return getTransformation();
			case PictoPackage.PICTO__FORMAT:
				return getFormat();
			case PictoPackage.PICTO__STANDALONE:
				return isStandalone();
			case PictoPackage.PICTO__MODELS:
				return getModels();
			case PictoPackage.PICTO__PARAMETERS:
				return getParameters();
			case PictoPackage.PICTO__CUSTOM_VIEWS:
				return getCustomViews();
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
			case PictoPackage.PICTO__TRANSFORMATION:
				setTransformation((String)newValue);
				return;
			case PictoPackage.PICTO__FORMAT:
				setFormat((String)newValue);
				return;
			case PictoPackage.PICTO__STANDALONE:
				setStandalone((Boolean)newValue);
				return;
			case PictoPackage.PICTO__MODELS:
				getModels().clear();
				getModels().addAll((Collection<? extends Model>)newValue);
				return;
			case PictoPackage.PICTO__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case PictoPackage.PICTO__CUSTOM_VIEWS:
				getCustomViews().clear();
				getCustomViews().addAll((Collection<? extends CustomView>)newValue);
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
			case PictoPackage.PICTO__TRANSFORMATION:
				setTransformation(TRANSFORMATION_EDEFAULT);
				return;
			case PictoPackage.PICTO__FORMAT:
				setFormat(FORMAT_EDEFAULT);
				return;
			case PictoPackage.PICTO__STANDALONE:
				setStandalone(STANDALONE_EDEFAULT);
				return;
			case PictoPackage.PICTO__MODELS:
				getModels().clear();
				return;
			case PictoPackage.PICTO__PARAMETERS:
				getParameters().clear();
				return;
			case PictoPackage.PICTO__CUSTOM_VIEWS:
				getCustomViews().clear();
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
			case PictoPackage.PICTO__TRANSFORMATION:
				return TRANSFORMATION_EDEFAULT == null ? transformation != null : !TRANSFORMATION_EDEFAULT.equals(transformation);
			case PictoPackage.PICTO__FORMAT:
				return FORMAT_EDEFAULT == null ? format != null : !FORMAT_EDEFAULT.equals(format);
			case PictoPackage.PICTO__STANDALONE:
				return standalone != STANDALONE_EDEFAULT;
			case PictoPackage.PICTO__MODELS:
				return models != null && !models.isEmpty();
			case PictoPackage.PICTO__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case PictoPackage.PICTO__CUSTOM_VIEWS:
				return customViews != null && !customViews.isEmpty();
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
		result.append(" (transformation: ");
		result.append(transformation);
		result.append(", format: ");
		result.append(format);
		result.append(", standalone: ");
		result.append(standalone);
		result.append(')');
		return result.toString();
	}

} //PictoImpl
