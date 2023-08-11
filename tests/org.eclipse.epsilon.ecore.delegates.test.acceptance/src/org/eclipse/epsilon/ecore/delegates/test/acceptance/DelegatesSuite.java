package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	Employee_Setting_DepartmentTest.class,
	Employee_Operation_AllReprotsTest.class,
	Employee_Operation_ReportingChainTest.class,
	Employee_Operation_ReportsToTest.class,
	Department_Setting_EmployeesTest.class,
	Employee_Setting_IsManagerTest.class,
	ValidationTest.class})
public class DelegatesSuite {

}
