/**
 */
package org.eclipse.epsilon.ecore.delegates.test.employee.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.epsilon.ecore.delegates.test.employee.Company;
import org.eclipse.epsilon.ecore.delegates.test.employee.Department;
import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.eclipse.epsilon.ecore.delegates.test.employee.EmployeeFactory;
import org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage;

import org.eclipse.epsilon.ecore.delegates.test.employee.util.EmployeeValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmployeePackageImpl extends EPackageImpl implements EmployeePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass companyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass departmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass employeeEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.epsilon.ecore.delegates.test.employee.EmployeePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EmployeePackageImpl() {
		super(eNS_URI, EmployeeFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link EmployeePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EmployeePackage init() {
		if (isInited) return (EmployeePackage)EPackage.Registry.INSTANCE.getEPackage(EmployeePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredEmployeePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		EmployeePackageImpl theEmployeePackage = registeredEmployeePackage instanceof EmployeePackageImpl ? (EmployeePackageImpl)registeredEmployeePackage : new EmployeePackageImpl();

		isInited = true;

		// Create package meta-data objects
		theEmployeePackage.createPackageContents();

		// Initialize created meta-data
		theEmployeePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theEmployeePackage,
			 new EValidator.Descriptor() {
				 @Override
				 public EValidator getEValidator() {
					 return EmployeeValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theEmployeePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EmployeePackage.eNS_URI, theEmployeePackage);
		return theEmployeePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompany() {
		return companyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompany_Name() {
		return (EAttribute)companyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompany_Employees() {
		return (EReference)companyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompany_Departments() {
		return (EReference)companyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDepartment() {
		return departmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDepartment_Manager() {
		return (EReference)departmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDepartment_DeptID() {
		return (EAttribute)departmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDepartment_Name() {
		return (EAttribute)departmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDepartment_Employees() {
		return (EReference)departmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDepartment_Company() {
		return (EReference)departmentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEmployee() {
		return employeeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEmployee_IsManager() {
		return (EAttribute)employeeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEmployee_Department() {
		return (EReference)employeeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEmployee_Manager() {
		return (EReference)employeeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEmployee_DirectReports() {
		return (EReference)employeeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEmployee_EmpID() {
		return (EAttribute)employeeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEmployee_Name() {
		return (EAttribute)employeeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEmployee_Company() {
		return (EReference)employeeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getEmployee__AllReports() {
		return employeeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getEmployee__ReportingChain() {
		return employeeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getEmployee__ReportsTo__Employee() {
		return employeeEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getEmployee__ValidName__DiagnosticChain_Map() {
		return employeeEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EmployeeFactory getEmployeeFactory() {
		return (EmployeeFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		companyEClass = createEClass(COMPANY);
		createEAttribute(companyEClass, COMPANY__NAME);
		createEReference(companyEClass, COMPANY__EMPLOYEES);
		createEReference(companyEClass, COMPANY__DEPARTMENTS);

		departmentEClass = createEClass(DEPARTMENT);
		createEReference(departmentEClass, DEPARTMENT__MANAGER);
		createEAttribute(departmentEClass, DEPARTMENT__DEPT_ID);
		createEAttribute(departmentEClass, DEPARTMENT__NAME);
		createEReference(departmentEClass, DEPARTMENT__EMPLOYEES);
		createEReference(departmentEClass, DEPARTMENT__COMPANY);

		employeeEClass = createEClass(EMPLOYEE);
		createEAttribute(employeeEClass, EMPLOYEE__IS_MANAGER);
		createEReference(employeeEClass, EMPLOYEE__DEPARTMENT);
		createEReference(employeeEClass, EMPLOYEE__MANAGER);
		createEReference(employeeEClass, EMPLOYEE__DIRECT_REPORTS);
		createEAttribute(employeeEClass, EMPLOYEE__EMP_ID);
		createEAttribute(employeeEClass, EMPLOYEE__NAME);
		createEReference(employeeEClass, EMPLOYEE__COMPANY);
		createEOperation(employeeEClass, EMPLOYEE___ALL_REPORTS);
		createEOperation(employeeEClass, EMPLOYEE___REPORTING_CHAIN);
		createEOperation(employeeEClass, EMPLOYEE___REPORTS_TO__EMPLOYEE);
		createEOperation(employeeEClass, EMPLOYEE___VALID_NAME__DIAGNOSTICCHAIN_MAP);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(companyEClass, Company.class, "Company", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompany_Name(), ecorePackage.getEString(), "name", null, 0, 1, Company.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompany_Employees(), this.getEmployee(), this.getEmployee_Company(), "employees", null, 0, -1, Company.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompany_Departments(), this.getDepartment(), this.getDepartment_Company(), "departments", null, 0, -1, Company.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(departmentEClass, Department.class, "Department", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDepartment_Manager(), this.getEmployee(), null, "manager", null, 0, 1, Department.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDepartment_DeptID(), ecorePackage.getEInt(), "deptID", null, 1, 1, Department.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDepartment_Name(), ecorePackage.getEString(), "name", null, 0, 1, Department.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDepartment_Employees(), this.getEmployee(), this.getEmployee_Department(), "employees", null, 0, -1, Department.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getDepartment_Company(), this.getCompany(), this.getCompany_Departments(), "company", null, 0, 1, Department.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(employeeEClass, Employee.class, "Employee", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEmployee_IsManager(), ecorePackage.getEBoolean(), "isManager", null, 0, 1, Employee.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEmployee_Department(), this.getDepartment(), this.getDepartment_Employees(), "department", null, 0, 1, Employee.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEmployee_Manager(), this.getEmployee(), this.getEmployee_DirectReports(), "manager", null, 0, 1, Employee.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEmployee_DirectReports(), this.getEmployee(), this.getEmployee_Manager(), "directReports", null, 0, -1, Employee.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getEmployee_EmpID(), ecorePackage.getEInt(), "empID", null, 1, 1, Employee.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEmployee_Name(), ecorePackage.getEString(), "name", null, 0, 1, Employee.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEmployee_Company(), this.getCompany(), this.getCompany_Employees(), "company", null, 0, 1, Employee.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getEmployee__AllReports(), this.getEmployee(), "allReports", 0, -1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getEmployee__ReportingChain(), this.getEmployee(), "reportingChain", 0, -1, IS_UNIQUE, IS_ORDERED);

		EOperation op = initEOperation(getEmployee__ReportsTo__Employee(), ecorePackage.getEBoolean(), "reportsTo", 0, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getEmployee(), "mgr", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getEmployee__ValidName__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "validName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://eclipse.dev/epsilon/ecore/EVL
		createEVLAnnotations();
		// http://eclipse.dev/epsilon/ecore/EOL
		createEOLAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "validationDelegates", "http://eclipse.dev/epsilon/ecore/EVL",
			   "invocationDelegates", "http://eclipse.dev/epsilon/ecore/EOL",
			   "settingDelegates", "http://eclipse.dev/epsilon/ecore/EOL"
		   });
		addAnnotation
		  (departmentEClass,
		   source,
		   new String[] {
			   "constraints", "deptHasEmployees positiveID validName"
		   });
		addAnnotation
		  (employeeEClass,
		   source,
		   new String[] {
			   "constraints", "positiveID validName"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://eclipse.dev/epsilon/ecore/EVL</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEVLAnnotations() {
		String source = "http://eclipse.dev/epsilon/ecore/EVL";
		addAnnotation
		  (departmentEClass,
		   source,
		   new String[] {
			   "deptHasEmployees", "not self.manager.isUndefined() implies self.employees.notEmpty()",
			   "positiveID", "self.deptID > 0",
			   "validName", "not self.name.isUndefined() and self.name.length() > 0"
		   });
		addAnnotation
		  (employeeEClass,
		   source,
		   new String[] {
			   "positiveID", "self.empID > 0"
		   });
		addAnnotation
		  (getEmployee__ValidName__DiagnosticChain_Map(),
		   source,
		   new String[] {
			   "body", "not self.name.isUndefined() and self.name.length() > 0"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://eclipse.dev/epsilon/ecore/EOL</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEOLAnnotations() {
		String source = "http://eclipse.dev/epsilon/ecore/EOL";
		addAnnotation
		  (getDepartment_Employees(),
		   source,
		   new String[] {
			   "derivation", "if (self.manager.isUndefined()) {\n  return Sequence{};\n} else {\n  return self.manager.directReports;\n}"
		   });
		addAnnotation
		  (getEmployee__AllReports(),
		   source,
		   new String[] {
			   "body", "return self.closure(t | t.directReports);"
		   });
		addAnnotation
		  (getEmployee__ReportingChain(),
		   source,
		   new String[] {
			   "body", "if (self.manager.isUndefined()) {\n\treturn Sequence{};\n} else {\n\tvar result = Sequence {self.manager};\n\treturn result.includingAll(self.manager.reportingChain());\n}"
		   });
		addAnnotation
		  (getEmployee__ReportsTo__Employee(),
		   source,
		   new String[] {
			   "body", "return self.reportingChain().includes(mgr);"
		   });
		addAnnotation
		  (getEmployee_IsManager(),
		   source,
		   new String[] {
			   "derivation", "return self.directReports.notEmpty();"
		   });
		addAnnotation
		  (getEmployee_Department(),
		   source,
		   new String[] {
			   "derivation", "return self.company.departments.selectOne(d | d.employees.includes(self));"
		   });
	}

} //EmployeePackageImpl
