package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	EmployeeSettingDepartmentTest.class,
	EmployeeOperationAllReportsTest.class,
	EmployeeOperationReportingChainTest.class,
	EmployeeOperationReportsToTest.class,
	DepartmentSettingEmployeesTest.class,
	EmployeeSettingIsManagerTest.class,
	ValidationTest.class})
public class DelegatesSuite {

}
