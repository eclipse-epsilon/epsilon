/**
 */
package org.eclipse.epsilon.ecore.delegates.test.employee.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.epsilon.ecore.delegates.test.employee.Company;
import org.eclipse.epsilon.ecore.delegates.test.employee.Department;
import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage;

import org.eclipse.epsilon.ecore.delegates.test.employee.util.EmployeeValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Employee</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.EmployeeImpl#isIsManager <em>Is Manager</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.EmployeeImpl#getDepartment <em>Department</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.EmployeeImpl#getManager <em>Manager</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.EmployeeImpl#getDirectReports <em>Direct Reports</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.EmployeeImpl#getEmpID <em>Emp ID</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.EmployeeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.impl.EmployeeImpl#getCompany <em>Company</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EmployeeImpl extends MinimalEObjectImpl.Container implements Employee {
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
	 * The cached setting delegate for the '{@link #isIsManager() <em>Is Manager</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsManager()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate IS_MANAGER__ESETTING_DELEGATE = ((EStructuralFeature.Internal)EmployeePackage.Literals.EMPLOYEE__IS_MANAGER).getSettingDelegate();
	/**
	 * The cached setting delegate for the '{@link #getDepartment() <em>Department</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDepartment()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate DEPARTMENT__ESETTING_DELEGATE = ((EStructuralFeature.Internal)EmployeePackage.Literals.EMPLOYEE__DEPARTMENT).getSettingDelegate();
	/**
	 * The default value of the '{@link #getEmpID() <em>Emp ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmpID()
	 * @generated
	 * @ordered
	 */
	protected static final int EMP_ID_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getEmpID() <em>Emp ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmpID()
	 * @generated
	 * @ordered
	 */
	protected int empID = EMP_ID_EDEFAULT;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EmployeeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmployeePackage.Literals.EMPLOYEE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsManager() {
		return (Boolean)IS_MANAGER__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Department getDepartment() {
		return (Department)DEPARTMENT__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Department basicGetDepartment() {
		return (Department)DEPARTMENT__ESETTING_DELEGATE.dynamicGet(this, null, 0, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Employee getManager() {
		Employee manager = (Employee)eVirtualGet(EmployeePackage.EMPLOYEE__MANAGER);
		if (manager != null && manager.eIsProxy()) {
			InternalEObject oldManager = (InternalEObject)manager;
			manager = (Employee)eResolveProxy(oldManager);
			if (manager != oldManager) {
				eVirtualSet(EmployeePackage.EMPLOYEE__MANAGER, manager);
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EmployeePackage.EMPLOYEE__MANAGER, oldManager, manager));
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
		return (Employee)eVirtualGet(EmployeePackage.EMPLOYEE__MANAGER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetManager(Employee newManager, NotificationChain msgs) {
		Object oldManager = eVirtualSet(EmployeePackage.EMPLOYEE__MANAGER, newManager);
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EmployeePackage.EMPLOYEE__MANAGER, oldManager == EVIRTUAL_NO_VALUE ? null : oldManager, newManager);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setManager(Employee newManager) {
		Employee manager = (Employee)eVirtualGet(EmployeePackage.EMPLOYEE__MANAGER);
		if (newManager != manager) {
			NotificationChain msgs = null;
			if (manager != null)
				msgs = ((InternalEObject)manager).eInverseRemove(this, EmployeePackage.EMPLOYEE__DIRECT_REPORTS, Employee.class, msgs);
			if (newManager != null)
				msgs = ((InternalEObject)newManager).eInverseAdd(this, EmployeePackage.EMPLOYEE__DIRECT_REPORTS, Employee.class, msgs);
			msgs = basicSetManager(newManager, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmployeePackage.EMPLOYEE__MANAGER, newManager, newManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<Employee> getDirectReports() {
		EList<Employee> directReports = (EList<Employee>)eVirtualGet(EmployeePackage.EMPLOYEE__DIRECT_REPORTS);
		if (directReports == null) {
			eVirtualSet(EmployeePackage.EMPLOYEE__DIRECT_REPORTS, directReports = new EObjectWithInverseResolvingEList<Employee>(Employee.class, this, EmployeePackage.EMPLOYEE__DIRECT_REPORTS, EmployeePackage.EMPLOYEE__MANAGER));
		}
		return directReports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getEmpID() {
		return empID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmpID(int newEmpID) {
		int oldEmpID = empID;
		empID = newEmpID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmployeePackage.EMPLOYEE__EMP_ID, oldEmpID, empID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return (String)eVirtualGet(EmployeePackage.EMPLOYEE__NAME, NAME_EDEFAULT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String name = newName;
		Object oldName = eVirtualSet(EmployeePackage.EMPLOYEE__NAME, name);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmployeePackage.EMPLOYEE__NAME, oldName == EVIRTUAL_NO_VALUE ? NAME_EDEFAULT : oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Company getCompany() {
		if (eContainerFeatureID() != EmployeePackage.EMPLOYEE__COMPANY) return null;
		return (Company)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompany(Company newCompany, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newCompany, EmployeePackage.EMPLOYEE__COMPANY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompany(Company newCompany) {
		if (newCompany != eInternalContainer() || (eContainerFeatureID() != EmployeePackage.EMPLOYEE__COMPANY && newCompany != null)) {
			if (EcoreUtil.isAncestor(this, newCompany))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newCompany != null)
				msgs = ((InternalEObject)newCompany).eInverseAdd(this, EmployeePackage.COMPANY__EMPLOYEES, Company.class, msgs);
			msgs = basicSetCompany(newCompany, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmployeePackage.EMPLOYEE__COMPANY, newCompany, newCompany));
	}

	/**
	 * The cached invocation delegate for the '{@link #allReports() <em>All Reports</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #allReports()
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate ALL_REPORTS__EINVOCATION_DELEGATE = ((EOperation.Internal)EmployeePackage.Literals.EMPLOYEE___ALL_REPORTS).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<Employee> allReports() {
		try {
			return (EList<Employee>)ALL_REPORTS__EINVOCATION_DELEGATE.dynamicInvoke(this, null);
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #reportingChain() <em>Reporting Chain</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #reportingChain()
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate REPORTING_CHAIN__EINVOCATION_DELEGATE = ((EOperation.Internal)EmployeePackage.Literals.EMPLOYEE___REPORTING_CHAIN).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<Employee> reportingChain() {
		try {
			return (EList<Employee>)REPORTING_CHAIN__EINVOCATION_DELEGATE.dynamicInvoke(this, null);
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #reportsTo(org.eclipse.epsilon.ecore.delegates.test.employee.Employee) <em>Reports To</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #reportsTo(org.eclipse.epsilon.ecore.delegates.test.employee.Employee)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate REPORTS_TO_EMPLOYEE__EINVOCATION_DELEGATE = ((EOperation.Internal)EmployeePackage.Literals.EMPLOYEE___REPORTS_TO__EMPLOYEE).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean reportsTo(Employee mgr) {
		try {
			return (Boolean)REPORTS_TO_EMPLOYEE__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{mgr}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached validation expression for the '{@link #validName(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Valid Name</em>}' invariant operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #validName(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 * @ordered
	 */
	protected static final String VALID_NAME_DIAGNOSTIC_CHAIN_MAP__EEXPRESSION = "not self.name.isUndefined() and self.name.length() > 0";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validName(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			EmployeeValidator.validate
				(EmployeePackage.Literals.EMPLOYEE,
				 this,
				 diagnostics,
				 context,
				 "http://eclipse.dev/epsilon/ecore/EVL",
				 EmployeePackage.Literals.EMPLOYEE___VALID_NAME__DIAGNOSTICCHAIN_MAP,
				 VALID_NAME_DIAGNOSTIC_CHAIN_MAP__EEXPRESSION,
				 Diagnostic.ERROR,
				 EmployeeValidator.DIAGNOSTIC_SOURCE,
				 EmployeeValidator.EMPLOYEE__VALID_NAME);
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
			case EmployeePackage.EMPLOYEE__MANAGER:
				Employee manager = (Employee)eVirtualGet(EmployeePackage.EMPLOYEE__MANAGER);
				if (manager != null)
					msgs = ((InternalEObject)manager).eInverseRemove(this, EmployeePackage.EMPLOYEE__DIRECT_REPORTS, Employee.class, msgs);
				return basicSetManager((Employee)otherEnd, msgs);
			case EmployeePackage.EMPLOYEE__DIRECT_REPORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDirectReports()).basicAdd(otherEnd, msgs);
			case EmployeePackage.EMPLOYEE__COMPANY:
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
			case EmployeePackage.EMPLOYEE__MANAGER:
				return basicSetManager(null, msgs);
			case EmployeePackage.EMPLOYEE__DIRECT_REPORTS:
				return ((InternalEList<?>)getDirectReports()).basicRemove(otherEnd, msgs);
			case EmployeePackage.EMPLOYEE__COMPANY:
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
			case EmployeePackage.EMPLOYEE__COMPANY:
				return eInternalContainer().eInverseRemove(this, EmployeePackage.COMPANY__EMPLOYEES, Company.class, msgs);
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
			case EmployeePackage.EMPLOYEE__IS_MANAGER:
				return isIsManager();
			case EmployeePackage.EMPLOYEE__DEPARTMENT:
				if (resolve) return getDepartment();
				return basicGetDepartment();
			case EmployeePackage.EMPLOYEE__MANAGER:
				if (resolve) return getManager();
				return basicGetManager();
			case EmployeePackage.EMPLOYEE__DIRECT_REPORTS:
				return getDirectReports();
			case EmployeePackage.EMPLOYEE__EMP_ID:
				return getEmpID();
			case EmployeePackage.EMPLOYEE__NAME:
				return getName();
			case EmployeePackage.EMPLOYEE__COMPANY:
				return getCompany();
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
			case EmployeePackage.EMPLOYEE__MANAGER:
				setManager((Employee)newValue);
				return;
			case EmployeePackage.EMPLOYEE__DIRECT_REPORTS:
				getDirectReports().clear();
				getDirectReports().addAll((Collection<? extends Employee>)newValue);
				return;
			case EmployeePackage.EMPLOYEE__EMP_ID:
				setEmpID((Integer)newValue);
				return;
			case EmployeePackage.EMPLOYEE__NAME:
				setName((String)newValue);
				return;
			case EmployeePackage.EMPLOYEE__COMPANY:
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
			case EmployeePackage.EMPLOYEE__MANAGER:
				setManager((Employee)null);
				return;
			case EmployeePackage.EMPLOYEE__DIRECT_REPORTS:
				getDirectReports().clear();
				return;
			case EmployeePackage.EMPLOYEE__EMP_ID:
				setEmpID(EMP_ID_EDEFAULT);
				return;
			case EmployeePackage.EMPLOYEE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EmployeePackage.EMPLOYEE__COMPANY:
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
	@SuppressWarnings("unchecked")
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EmployeePackage.EMPLOYEE__IS_MANAGER:
				return IS_MANAGER__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case EmployeePackage.EMPLOYEE__DEPARTMENT:
				return DEPARTMENT__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case EmployeePackage.EMPLOYEE__MANAGER:
				return eVirtualGet(EmployeePackage.EMPLOYEE__MANAGER) != null;
			case EmployeePackage.EMPLOYEE__DIRECT_REPORTS:
				EList<Employee> directReports = (EList<Employee>)eVirtualGet(EmployeePackage.EMPLOYEE__DIRECT_REPORTS);
				return directReports != null && !directReports.isEmpty();
			case EmployeePackage.EMPLOYEE__EMP_ID:
				return empID != EMP_ID_EDEFAULT;
			case EmployeePackage.EMPLOYEE__NAME:
				String name = (String)eVirtualGet(EmployeePackage.EMPLOYEE__NAME, NAME_EDEFAULT);
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EmployeePackage.EMPLOYEE__COMPANY:
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
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case EmployeePackage.EMPLOYEE___ALL_REPORTS:
				return allReports();
			case EmployeePackage.EMPLOYEE___REPORTING_CHAIN:
				return reportingChain();
			case EmployeePackage.EMPLOYEE___REPORTS_TO__EMPLOYEE:
				return reportsTo((Employee)arguments.get(0));
			case EmployeePackage.EMPLOYEE___VALID_NAME__DIAGNOSTICCHAIN_MAP:
				return validName((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (empID: ");
		result.append(empID);
		result.append(", name: ");
		result.append(eVirtualGet(EmployeePackage.EMPLOYEE__NAME, NAME_EDEFAULT));
		result.append(')');
		return result.toString();
	}

} //EmployeeImpl
