/**
 */
package org.eclipse.epsilon.coverage.model.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.coverage.model.CoverageElementType;
import org.eclipse.epsilon.coverage.model.CoveragePackage;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Session</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl#getLaunchConfiguration <em>Launch Configuration</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl#getFiles <em>Files</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CoverageSessionImpl extends MinimalEObjectImpl.Container implements CoverageSession {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final Date TIMESTAMP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected Date timestamp = TIMESTAMP_EDEFAULT;

	/**
	 * The default value of the '{@link #getLaunchConfiguration() <em>Launch Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLaunchConfiguration()
	 * @generated
	 * @ordered
	 */
	protected static final String LAUNCH_CONFIGURATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLaunchConfiguration() <em>Launch Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLaunchConfiguration()
	 * @generated
	 * @ordered
	 */
	protected String launchConfiguration = LAUNCH_CONFIGURATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFiles() <em>Files</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<SourceFile> files;

	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<CoverageElementType> types;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CoverageSessionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoveragePackage.Literals.COVERAGE_SESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_SESSION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTimestamp(Date newTimestamp) {
		Date oldTimestamp = timestamp;
		timestamp = newTimestamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_SESSION__TIMESTAMP, oldTimestamp, timestamp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLaunchConfiguration() {
		return launchConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLaunchConfiguration(String newLaunchConfiguration) {
		String oldLaunchConfiguration = launchConfiguration;
		launchConfiguration = newLaunchConfiguration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_SESSION__LAUNCH_CONFIGURATION, oldLaunchConfiguration, launchConfiguration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SourceFile> getFiles() {
		if (files == null) {
			files = new EObjectContainmentWithInverseEList<SourceFile>(SourceFile.class, this, CoveragePackage.COVERAGE_SESSION__FILES, CoveragePackage.SOURCE_FILE__SESSION);
		}
		return files;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CoverageElementType> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<CoverageElementType>(CoverageElementType.class, this, CoveragePackage.COVERAGE_SESSION__TYPES);
		}
		return types;
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
			case CoveragePackage.COVERAGE_SESSION__FILES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFiles()).basicAdd(otherEnd, msgs);
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
			case CoveragePackage.COVERAGE_SESSION__FILES:
				return ((InternalEList<?>)getFiles()).basicRemove(otherEnd, msgs);
			case CoveragePackage.COVERAGE_SESSION__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
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
			case CoveragePackage.COVERAGE_SESSION__NAME:
				return getName();
			case CoveragePackage.COVERAGE_SESSION__TIMESTAMP:
				return getTimestamp();
			case CoveragePackage.COVERAGE_SESSION__LAUNCH_CONFIGURATION:
				return getLaunchConfiguration();
			case CoveragePackage.COVERAGE_SESSION__FILES:
				return getFiles();
			case CoveragePackage.COVERAGE_SESSION__TYPES:
				return getTypes();
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
			case CoveragePackage.COVERAGE_SESSION__NAME:
				setName((String)newValue);
				return;
			case CoveragePackage.COVERAGE_SESSION__TIMESTAMP:
				setTimestamp((Date)newValue);
				return;
			case CoveragePackage.COVERAGE_SESSION__LAUNCH_CONFIGURATION:
				setLaunchConfiguration((String)newValue);
				return;
			case CoveragePackage.COVERAGE_SESSION__FILES:
				getFiles().clear();
				getFiles().addAll((Collection<? extends SourceFile>)newValue);
				return;
			case CoveragePackage.COVERAGE_SESSION__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends CoverageElementType>)newValue);
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
			case CoveragePackage.COVERAGE_SESSION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_SESSION__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_SESSION__LAUNCH_CONFIGURATION:
				setLaunchConfiguration(LAUNCH_CONFIGURATION_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_SESSION__FILES:
				getFiles().clear();
				return;
			case CoveragePackage.COVERAGE_SESSION__TYPES:
				getTypes().clear();
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
			case CoveragePackage.COVERAGE_SESSION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CoveragePackage.COVERAGE_SESSION__TIMESTAMP:
				return TIMESTAMP_EDEFAULT == null ? timestamp != null : !TIMESTAMP_EDEFAULT.equals(timestamp);
			case CoveragePackage.COVERAGE_SESSION__LAUNCH_CONFIGURATION:
				return LAUNCH_CONFIGURATION_EDEFAULT == null ? launchConfiguration != null : !LAUNCH_CONFIGURATION_EDEFAULT.equals(launchConfiguration);
			case CoveragePackage.COVERAGE_SESSION__FILES:
				return files != null && !files.isEmpty();
			case CoveragePackage.COVERAGE_SESSION__TYPES:
				return types != null && !types.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", timestamp: ");
		result.append(timestamp);
		result.append(", launchConfiguration: ");
		result.append(launchConfiguration);
		result.append(')');
		return result.toString();
	}

} //CoverageSessionImpl
