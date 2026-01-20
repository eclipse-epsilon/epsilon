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
public class EmployeeOperationAllReportsTest extends AcmeTests {
	
	@Parameters( name = "Employee:Size {0}: {1}" )
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
        	{ "6", 10},
            {"3", 37} ,
            {"14", 20}
        });
    }

	public EmployeeOperationAllReportsTest(String employeeId, int expectedSize) {
		super();
		this.employee = (Employee) resource.getEObject(employeeId); 
		this.expectedSize = expectedSize;
	}

	@Test
	public void allReportsIsClosure() {
		assertEquals(this.expectedSize, employee.allReports().size());
	}
	
	private final Employee employee;
	private final int expectedSize;

}
