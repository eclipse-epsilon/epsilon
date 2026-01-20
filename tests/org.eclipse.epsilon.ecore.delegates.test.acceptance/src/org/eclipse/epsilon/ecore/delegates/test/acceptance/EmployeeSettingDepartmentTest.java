package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epsilon.ecore.delegates.test.employee.Department;
import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EmployeeSettingDepartmentTest extends AcmeTests {
	
	@Parameters( name = "Employee:Department {0}: {1}" )
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { "9", "100"},
            {"37", "-300"},
            {"14", "200"},
        });
    }

	public EmployeeSettingDepartmentTest(
		String employeeId,
		String deptId) {
		super();
		this.employee = (Employee) resource.getEObject(employeeId); 
		this.dept = (Department) resource.getEObject(deptId); ;
	}
	
	@Test
	public void employeeHasCorrectDepartment() {
		assertEquals(this.dept, this.employee.getDepartment());
	}
	
	private final Employee employee;
	private final Department dept;

}
