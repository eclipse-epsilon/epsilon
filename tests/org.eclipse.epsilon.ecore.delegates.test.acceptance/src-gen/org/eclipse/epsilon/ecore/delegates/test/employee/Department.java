/**
 */
package org.eclipse.epsilon.ecore.delegates.test.employee;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Department</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getManager <em>Manager</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getDeptID <em>Dept ID</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getEmployees <em>Employees</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getCompany <em>Company</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getDepartment()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='deptHasEmployees positiveID validName'"
 *        annotation="http://eclipse.dev/epsilon/ecore/EVL deptHasEmployees='not self.manager.isUndefined() implies self.employees.notEmpty()' positiveID='self.deptID &gt; 0' validName='not self.name.isUndefined() and self.name.length() &gt; 0'"
 * @generated
 */
public interface Department extends EObject {
	/**
	 * Returns the value of the '<em><b>Manager</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manager</em>' reference.
	 * @see #setManager(Employee)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getDepartment_Manager()
	 * @model
	 * @generated
	 */
	Employee getManager();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getManager <em>Manager</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manager</em>' reference.
	 * @see #getManager()
	 * @generated
	 */
	void setManager(Employee value);

	/**
	 * Returns the value of the '<em><b>Dept ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dept ID</em>' attribute.
	 * @see #setDeptID(int)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getDepartment_DeptID()
	 * @model id="true" required="true"
	 * @generated
	 */
	int getDeptID();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getDeptID <em>Dept ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dept ID</em>' attribute.
	 * @see #getDeptID()
	 * @generated
	 */
	void setDeptID(int value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getDepartment_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Employees</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getDepartment <em>Department</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Employees</em>' reference list.
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getDepartment_Employees()
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getDepartment
	 * @model opposite="department" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 *        annotation="http://eclipse.dev/epsilon/ecore/EOL derivation='if (self.manager.isUndefined()) {\n  return Sequence{};\n} else {\n  return self.manager.directReports;\n}'"
	 * @generated
	 */
	EList<Employee> getEmployees();

	/**
	 * Returns the value of the '<em><b>Company</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Company#getDepartments <em>Departments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Company</em>' container reference.
	 * @see #setCompany(Company)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getDepartment_Company()
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.Company#getDepartments
	 * @model opposite="departments" transient="false"
	 * @generated
	 */
	Company getCompany();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getCompany <em>Company</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Company</em>' container reference.
	 * @see #getCompany()
	 * @generated
	 */
	void setCompany(Company value);

} // Department
