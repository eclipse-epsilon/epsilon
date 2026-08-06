/**
 */
package org.eclipse.epsilon.ecore.delegates.test.employee;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Employee</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#isIsManager <em>Is Manager</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getDepartment <em>Department</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getManager <em>Manager</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getDirectReports <em>Direct Reports</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getEmpID <em>Emp ID</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getCompany <em>Company</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getEmployee()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='positiveID validName'"
 *        annotation="http://eclipse.dev/epsilon/ecore/EVL positiveID='self.empID &gt; 0'"
 * @generated
 */
public interface Employee extends EObject {
	/**
	 * Returns the value of the '<em><b>Is Manager</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Manager</em>' attribute.
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getEmployee_IsManager()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://eclipse.dev/epsilon/ecore/EOL derivation='return self.directReports.notEmpty();'"
	 * @generated
	 */
	boolean isIsManager();

	/**
	 * Returns the value of the '<em><b>Department</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getEmployees <em>Employees</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Department</em>' reference.
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getEmployee_Department()
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.Department#getEmployees
	 * @model opposite="employees" transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://eclipse.dev/epsilon/ecore/EOL derivation='return self.company.departments.selectOne(d | d.employees.includes(self));'"
	 * @generated
	 */
	Department getDepartment();

	/**
	 * Returns the value of the '<em><b>Manager</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getDirectReports <em>Direct Reports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manager</em>' reference.
	 * @see #setManager(Employee)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getEmployee_Manager()
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getDirectReports
	 * @model opposite="directReports"
	 * @generated
	 */
	Employee getManager();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getManager <em>Manager</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manager</em>' reference.
	 * @see #getManager()
	 * @generated
	 */
	void setManager(Employee value);

	/**
	 * Returns the value of the '<em><b>Direct Reports</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getManager <em>Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direct Reports</em>' reference list.
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getEmployee_DirectReports()
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getManager
	 * @model opposite="manager" ordered="false"
	 * @generated
	 */
	EList<Employee> getDirectReports();

	/**
	 * Returns the value of the '<em><b>Emp ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Emp ID</em>' attribute.
	 * @see #setEmpID(int)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getEmployee_EmpID()
	 * @model id="true" required="true"
	 * @generated
	 */
	int getEmpID();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getEmpID <em>Emp ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Emp ID</em>' attribute.
	 * @see #getEmpID()
	 * @generated
	 */
	void setEmpID(int value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getEmployee_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Company</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Company#getEmployees <em>Employees</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Company</em>' container reference.
	 * @see #setCompany(Company)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getEmployee_Company()
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.Company#getEmployees
	 * @model opposite="employees" transient="false"
	 * @generated
	 */
	Company getCompany();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getCompany <em>Company</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Company</em>' container reference.
	 * @see #getCompany()
	 * @generated
	 */
	void setCompany(Company value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ordered="false"
	 *        annotation="http://eclipse.dev/epsilon/ecore/EOL body='return self.closure(t | t.directReports);'"
	 * @generated
	 */
	EList<Employee> allReports();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://eclipse.dev/epsilon/ecore/EOL body='if (self.manager.isUndefined()) {\n\treturn Sequence{};\n} else {\n\tvar result = Sequence {self.manager};\n\treturn result.includingAll(self.manager.reportingChain());\n}'"
	 * @generated
	 */
	EList<Employee> reportingChain();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ordered="false"
	 *        annotation="http://eclipse.dev/epsilon/ecore/EOL body='return self.reportingChain().includes(mgr);'"
	 * @generated
	 */
	boolean reportsTo(Employee mgr);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://eclipse.dev/epsilon/ecore/EVL body='not self.name.isUndefined() and self.name.length() &gt; 0'"
	 * @generated
	 */
	boolean validName(DiagnosticChain diagnostics, Map<Object, Object> context);

} // Employee
