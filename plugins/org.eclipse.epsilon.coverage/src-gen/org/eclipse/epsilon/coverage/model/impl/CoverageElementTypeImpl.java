/**
 */
package org.eclipse.epsilon.coverage.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.epsilon.coverage.model.CoverageElementType;
import org.eclipse.epsilon.coverage.model.CoveragePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementTypeImpl#getImpl <em>Impl</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CoverageElementTypeImpl extends MinimalEObjectImpl.Container implements CoverageElementType {
	/**
	 * The default value of the '{@link #getImpl() <em>Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpl()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImpl() <em>Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpl()
	 * @generated
	 * @ordered
	 */
	protected String impl = IMPL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CoverageElementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoveragePackage.Literals.COVERAGE_ELEMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImpl() {
		return impl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImpl(String newImpl) {
		String oldImpl = impl;
		impl = newImpl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT_TYPE__IMPL, oldImpl, impl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoveragePackage.COVERAGE_ELEMENT_TYPE__IMPL:
				return getImpl();
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
			case CoveragePackage.COVERAGE_ELEMENT_TYPE__IMPL:
				setImpl((String)newValue);
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
			case CoveragePackage.COVERAGE_ELEMENT_TYPE__IMPL:
				setImpl(IMPL_EDEFAULT);
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
			case CoveragePackage.COVERAGE_ELEMENT_TYPE__IMPL:
				return IMPL_EDEFAULT == null ? impl != null : !IMPL_EDEFAULT.equals(impl);
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
		result.append(" (impl: ");
		result.append(impl);
		result.append(')');
		return result.toString();
	}

} //CoverageElementTypeImpl
