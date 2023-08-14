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
public class EmployeeSettingIsManagerTest extends AcmeTests {
	
	@Parameters( name = "User:isManager {0}: {1}" )
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { "6", true},
            {"45", false},
            {"3", true},
        });
    }

	public EmployeeSettingIsManagerTest(
		String employeeId,
		boolean isManager) {
		super();
		this.employee = (Employee) resource.getEObject(employeeId); 
		this.isManager = isManager;
	}
	
	@Test
	public void employeeIsManager() {
		assertEquals(this.isManager, this.employee.isIsManager());
	}
	
	private final Employee employee;
	private final boolean isManager;

}
