/**
 */
package org.eclipse.epsilon.ecore.delegates.test.employee.util;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.epsilon.ecore.delegates.test.employee.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage
 * @generated
 */
public class EmployeeValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EmployeeValidator INSTANCE = new EmployeeValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.epsilon.ecore.delegates.test.employee";

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Valid Name' of 'Employee'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int EMPLOYEE__VALID_NAME = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Delegates evaluation of the given invariant expression against the object in the given context.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context, String validationDelegate, EOperation invariant, String expression, int severity, String source, int code) {
		return EObjectValidator.validate(eClass, eObject, diagnostics, context, validationDelegate, invariant, expression, severity, source, code);
	}

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmployeeValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return EmployeePackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case EmployeePackage.COMPANY:
				return validateCompany((Company)value, diagnostics, context);
			case EmployeePackage.DEPARTMENT:
				return validateDepartment((Department)value, diagnostics, context);
			case EmployeePackage.EMPLOYEE:
				return validateEmployee((Employee)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompany(Company company, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(company, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDepartment(Department department, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(department, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(department, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(department, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(department, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(department, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(department, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(department, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(department, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(department, diagnostics, context);
		if (result || diagnostics != null) result &= validateDepartment_deptHasEmployees(department, diagnostics, context);
		if (result || diagnostics != null) result &= validateDepartment_positiveID(department, diagnostics, context);
		if (result || diagnostics != null) result &= validateDepartment_validName(department, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the deptHasEmployees constraint of '<em>Department</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String DEPARTMENT__DEPT_HAS_EMPLOYEES__EEXPRESSION = "not self.manager.isUndefined() implies self.employees.notEmpty()";

	/**
	 * Validates the deptHasEmployees constraint of '<em>Department</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDepartment_deptHasEmployees(Department department, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(EmployeePackage.Literals.DEPARTMENT,
				 department,
				 diagnostics,
				 context,
				 "http://eclipse.dev/epsilon/ecore/EVL",
				 "deptHasEmployees",
				 DEPARTMENT__DEPT_HAS_EMPLOYEES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the positiveID constraint of '<em>Department</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String DEPARTMENT__POSITIVE_ID__EEXPRESSION = "self.deptID > 0";

	/**
	 * Validates the positiveID constraint of '<em>Department</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDepartment_positiveID(Department department, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(EmployeePackage.Literals.DEPARTMENT,
				 department,
				 diagnostics,
				 context,
				 "http://eclipse.dev/epsilon/ecore/EVL",
				 "positiveID",
				 DEPARTMENT__POSITIVE_ID__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the validName constraint of '<em>Department</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String DEPARTMENT__VALID_NAME__EEXPRESSION = "not self.name.isUndefined() and self.name.length() > 0";

	/**
	 * Validates the validName constraint of '<em>Department</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDepartment_validName(Department department, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(EmployeePackage.Literals.DEPARTMENT,
				 department,
				 diagnostics,
				 context,
				 "http://eclipse.dev/epsilon/ecore/EVL",
				 "validName",
				 DEPARTMENT__VALID_NAME__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEmployee(Employee employee, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(employee, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validateEmployee_positiveID(employee, diagnostics, context);
		if (result || diagnostics != null) result &= validateEmployee_validName(employee, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the positiveID constraint of '<em>Employee</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String EMPLOYEE__POSITIVE_ID__EEXPRESSION = "self.empID > 0";

	/**
	 * Validates the positiveID constraint of '<em>Employee</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEmployee_positiveID(Employee employee, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(EmployeePackage.Literals.EMPLOYEE,
				 employee,
				 diagnostics,
				 context,
				 "http://eclipse.dev/epsilon/ecore/EVL",
				 "positiveID",
				 EMPLOYEE__POSITIVE_ID__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * Validates the validName constraint of '<em>Employee</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEmployee_validName(Employee employee, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return employee.validName(diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //EmployeeValidator
