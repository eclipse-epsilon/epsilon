/**
 */
package org.eclipse.epsilon.ecore.delegates.test.employee;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Company</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Company#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Company#getEmployees <em>Employees</em>}</li>
 *   <li>{@link org.eclipse.epsilon.ecore.delegates.test.employee.Company#getDepartments <em>Departments</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getCompany()
 * @model
 * @generated
 */
public interface Company extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getCompany_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Company#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Employees</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getCompany <em>Company</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Employees</em>' containment reference list.
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getCompany_Employees()
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.Employee#getCompany
	 * @model opposite="company" containment="true"
	 * @generated
	 */
	EList<Employee> getEmployees();

	/**
	 * Returns the value of the '<em><b>Departments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.ecore.delegates.test.employee.Department}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.ecore.delegates.test.employee.Department#getCompany <em>Company</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Departments</em>' containment reference list.
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#getCompany_Departments()
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.Department#getCompany
	 * @model opposite="company" containment="true"
	 * @generated
	 */
	EList<Department> getDepartments();

} // Company
