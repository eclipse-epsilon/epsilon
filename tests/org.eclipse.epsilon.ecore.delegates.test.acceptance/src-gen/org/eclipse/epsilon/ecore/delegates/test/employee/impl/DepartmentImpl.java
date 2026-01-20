/**
 */
package org.eclipse.epsilon.ecore.delegates.test.employee.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.ecore.delegates.test.employee.Company;
import org.eclipse.epsilon.ecore.delegates.test.employee.Department;
import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Department</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.DepartmentImpl#getManager <em>Manager</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.DepartmentImpl#getDeptID <em>Dept ID</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.DepartmentImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.DepartmentImpl#getEmployees <em>Employees</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.DepartmentImpl#getCompany <em>Company</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DepartmentImpl extends MinimalEObjectImpl.Container implements Department {
	/**
	 * An array of objects representing the values of non-primitive features.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Object[] eVirtualValues;
	/**
	 * A bit field representing the indices of non-primitive feature values.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected int eVirtualIndexBits0;
	/**
	 * The default value of the '{@link #getDeptID() <em>Dept ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeptID()
	 * @generated
	 * @ordered
	 */
	protected static final int DEPT_ID_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getDeptID() <em>Dept ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeptID()
	 * @generated
	 * @ordered
	 */
	protected int deptID = DEPT_ID_EDEFAULT;
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
	 * The cached setting delegate for the '{@link #getEmployees() <em>Employees</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmployees()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate EMPLOYEES__ESETTING_DELEGATE = ((EStructuralFeature.Internal)EmployeePackage.Literals.DEPARTMENT__EMPLOYEES).getSettingDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DepartmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmployeePackage.Literals.DEPARTMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Employee getManager() {
		Employee manager = (Employee)eVirtualGet(EmployeePackage.DEPARTMENT__MANAGER);
		if (manager != null && manager.eIsProxy()) {
			InternalEObject oldManager = (InternalEObject)manager;
			manager = (Employee)eResolveProxy(oldManager);
			if (manager != oldManager) {
				eVirtualSet(EmployeePackage.DEPARTMENT__MANAGER, manager);
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EmployeePackage.DEPARTMENT__MANAGER, oldManager, manager));
			}
		}
		return manager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Employee basicGetManager() {
		return (Employee)eVirtualGet(EmployeePackage.DEPARTMENT__MANAGER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setManager(Employee newManager) {
		Employee manager = newManager;
		Object oldManager = eVirtualSet(EmployeePackage.DEPARTMENT__MANAGER, manager);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmployeePackage.DEPARTMENT__MANAGER, oldManager == EVIRTUAL_NO_VALUE ? null : oldManager, manager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDeptID() {
		return deptID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeptID(int newDeptID) {
		int oldDeptID = deptID;
		deptID = newDeptID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmployeePackage.DEPARTMENT__DEPT_ID, oldDeptID, deptID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return (String)eVirtualGet(EmployeePackage.DEPARTMENT__NAME, NAME_EDEFAULT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String name = newName;
		Object oldName = eVirtualSet(EmployeePackage.DEPARTMENT__NAME, name);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmployeePackage.DEPARTMENT__NAME, oldName == EVIRTUAL_NO_VALUE ? NAME_EDEFAULT : oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<Employee> getEmployees() {
		return (EList<Employee>)EMPLOYEES__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Company getCompany() {
		if (eContainerFeatureID() != EmployeePackage.DEPARTMENT__COMPANY) return null;
		return (Company)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompany(Company newCompany, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newCompany, EmployeePackage.DEPARTMENT__COMPANY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompany(Company newCompany) {
		if (newCompany != eInternalContainer() || (eContainerFeatureID() != EmployeePackage.DEPARTMENT__COMPANY && newCompany != null)) {
			if (EcoreUtil.isAncestor(this, newCompany))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newCompany != null)
				msgs = ((InternalEObject)newCompany).eInverseAdd(this, EmployeePackage.COMPANY__DEPARTMENTS, Company.class, msgs);
			msgs = basicSetCompany(newCompany, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmployeePackage.DEPARTMENT__COMPANY, newCompany, newCompany));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmployeePackage.DEPARTMENT__COMPANY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetCompany((Company)otherEnd, msgs);
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
			case EmployeePackage.DEPARTMENT__COMPANY:
				return basicSetCompany(null, msgs);
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
			case EmployeePackage.DEPARTMENT__COMPANY:
				return eInternalContainer().eInverseRemove(this, EmployeePackage.COMPANY__DEPARTMENTS, Company.class, msgs);
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
			case EmployeePackage.DEPARTMENT__MANAGER:
				if (resolve) return getManager();
				return basicGetManager();
			case EmployeePackage.DEPARTMENT__DEPT_ID:
				return getDeptID();
			case EmployeePackage.DEPARTMENT__NAME:
				return getName();
			case EmployeePackage.DEPARTMENT__EMPLOYEES:
				return getEmployees();
			case EmployeePackage.DEPARTMENT__COMPANY:
				return getCompany();
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
			case EmployeePackage.DEPARTMENT__MANAGER:
				setManager((Employee)newValue);
				return;
			case EmployeePackage.DEPARTMENT__DEPT_ID:
				setDeptID((Integer)newValue);
				return;
			case EmployeePackage.DEPARTMENT__NAME:
				setName((String)newValue);
				return;
			case EmployeePackage.DEPARTMENT__COMPANY:
				setCompany((Company)newValue);
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
			case EmployeePackage.DEPARTMENT__MANAGER:
				setManager((Employee)null);
				return;
			case EmployeePackage.DEPARTMENT__DEPT_ID:
				setDeptID(DEPT_ID_EDEFAULT);
				return;
			case EmployeePackage.DEPARTMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EmployeePackage.DEPARTMENT__COMPANY:
				setCompany((Company)null);
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
			case EmployeePackage.DEPARTMENT__MANAGER:
				return eVirtualGet(EmployeePackage.DEPARTMENT__MANAGER) != null;
			case EmployeePackage.DEPARTMENT__DEPT_ID:
				return deptID != DEPT_ID_EDEFAULT;
			case EmployeePackage.DEPARTMENT__NAME:
				String name = (String)eVirtualGet(EmployeePackage.DEPARTMENT__NAME, NAME_EDEFAULT);
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EmployeePackage.DEPARTMENT__EMPLOYEES:
				return EMPLOYEES__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case EmployeePackage.DEPARTMENT__COMPANY:
				return getCompany() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Object[] eVirtualValues() {
		return eVirtualValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void eSetVirtualValues(Object[] newValues) {
		eVirtualValues = newValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eVirtualIndexBits(int offset) {
		switch (offset) {
			case 0 :
				return eVirtualIndexBits0;
			default :
				throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void eSetVirtualIndexBits(int offset, int newIndexBits) {
		switch (offset) {
			case 0 :
				eVirtualIndexBits0 = newIndexBits;
				break;
			default :
				throw new IndexOutOfBoundsException();
		}
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
		result.append(" (deptID: ");
		result.append(deptID);
		result.append(", name: ");
		result.append(eVirtualGet(EmployeePackage.DEPARTMENT__NAME, NAME_EDEFAULT));
		result.append(')');
		return result.toString();
	}

} //DepartmentImpl
