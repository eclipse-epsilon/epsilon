package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EmployeeOperationReportsToTest extends AcmeTests {
	
	@Parameters( name = "Employee:Manager {0}: {1}->{2}" )
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { "6", "3", false},
            {"45", "6", true},
            {"45", "3", false},
            {"25", "14", true},
            {"25", "3", true},
        });
    }

	public EmployeeOperationReportsToTest(
		String employeeId,
		String managerId,
		boolean reportsTo) {
		super();
		this.employee = (Employee) resource.getEObject(employeeId); 
		this.manager = (Employee) resource.getEObject(managerId); 
		this.reportsTo = reportsTo;
	}
	
	@Test
	public void employeeReportsToCorrectManager() {
		assertEquals(this.reportsTo, this.employee.reportsTo(this.manager));
	}
	
	private final Employee employee;
	private final Employee manager;
	private final boolean reportsTo;

}
