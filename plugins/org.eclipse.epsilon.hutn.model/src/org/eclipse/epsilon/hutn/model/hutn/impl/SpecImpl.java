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
 * $Id: SpecImpl.java,v 1.3 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.eclipse.epsilon.hutn.model.hutn.Spec;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Spec</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.impl.SpecImpl#getNsUris <em>Ns Uris</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.impl.SpecImpl#getObjects <em>Objects</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.impl.SpecImpl#getModelFile <em>Model File</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.impl.SpecImpl#getSourceFile <em>Source File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SpecImpl extends EObjectImpl implements Spec {
	/**
	 * The cached value of the '{@link #getNsUris() <em>Ns Uris</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNsUris()
	 * @generated
	 * @ordered
	 */
	protected EList<NsUri> nsUris;

	/**
	 * The cached value of the '{@link #getObjects() <em>Objects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<PackageObject> objects;

	/**
	 * The default value of the '{@link #getModelFile() <em>Model File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelFile()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModelFile() <em>Model File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelFile()
	 * @generated
	 * @ordered
	 */
	protected String modelFile = MODEL_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceFile() <em>Source File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFile()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceFile() <em>Source File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFile()
	 * @generated
	 * @ordered
	 */
	protected String sourceFile = SOURCE_FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SpecImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HutnPackage.Literals.SPEC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NsUri> getNsUris() {
		if (nsUris == null) {
			nsUris = new EObjectContainmentEList<>(NsUri.class, this, HutnPackage.SPEC__NS_URIS);
		}
		return nsUris;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PackageObject> getObjects() {
		if (objects == null) {
			objects = new EObjectContainmentEList<>(PackageObject.class, this, HutnPackage.SPEC__OBJECTS);
		}
		return objects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModelFile() {
		return modelFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelFile(String newModelFile) {
		String oldModelFile = modelFile;
		modelFile = newModelFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HutnPackage.SPEC__MODEL_FILE, oldModelFile, modelFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourceFile() {
		return sourceFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceFile(String newSourceFile) {
		String oldSourceFile = sourceFile;
		sourceFile = newSourceFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HutnPackage.SPEC__SOURCE_FILE, oldSourceFile, sourceFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HutnPackage.SPEC__NS_URIS:
				return ((InternalEList<?>)getNsUris()).basicRemove(otherEnd, msgs);
			case HutnPackage.SPEC__OBJECTS:
				return ((InternalEList<?>)getObjects()).basicRemove(otherEnd, msgs);
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
			case HutnPackage.SPEC__NS_URIS:
				return getNsUris();
			case HutnPackage.SPEC__OBJECTS:
				return getObjects();
			case HutnPackage.SPEC__MODEL_FILE:
				return getModelFile();
			case HutnPackage.SPEC__SOURCE_FILE:
				return getSourceFile();
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
			case HutnPackage.SPEC__NS_URIS:
				getNsUris().clear();
				getNsUris().addAll((Collection<? extends NsUri>)newValue);
				return;
			case HutnPackage.SPEC__OBJECTS:
				getObjects().clear();
				getObjects().addAll((Collection<? extends PackageObject>)newValue);
				return;
			case HutnPackage.SPEC__MODEL_FILE:
				setModelFile((String)newValue);
				return;
			case HutnPackage.SPEC__SOURCE_FILE:
				setSourceFile((String)newValue);
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
			case HutnPackage.SPEC__NS_URIS:
				getNsUris().clear();
				return;
			case HutnPackage.SPEC__OBJECTS:
				getObjects().clear();
				return;
			case HutnPackage.SPEC__MODEL_FILE:
				setModelFile(MODEL_FILE_EDEFAULT);
				return;
			case HutnPackage.SPEC__SOURCE_FILE:
				setSourceFile(SOURCE_FILE_EDEFAULT);
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
			case HutnPackage.SPEC__NS_URIS:
				return nsUris != null && !nsUris.isEmpty();
			case HutnPackage.SPEC__OBJECTS:
				return objects != null && !objects.isEmpty();
			case HutnPackage.SPEC__MODEL_FILE:
				return MODEL_FILE_EDEFAULT == null ? modelFile != null : !MODEL_FILE_EDEFAULT.equals(modelFile);
			case HutnPackage.SPEC__SOURCE_FILE:
				return SOURCE_FILE_EDEFAULT == null ? sourceFile != null : !SOURCE_FILE_EDEFAULT.equals(sourceFile);
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
		result.append(" (modelFile: ");
		result.append(modelFile);
		result.append(", sourceFile: ");
		result.append(sourceFile);
		result.append(')');
		return result.toString();
	}

} //SpecImpl
