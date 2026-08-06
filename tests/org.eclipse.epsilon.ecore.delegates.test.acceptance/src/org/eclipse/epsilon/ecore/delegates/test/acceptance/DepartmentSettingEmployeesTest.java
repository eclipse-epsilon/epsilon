package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epsilon.ecore.delegates.test.employee.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DepartmentSettingEmployeesTest extends AcmeTests {

	@Parameters( name = "Department:Size {0}: {1}" )
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { "100", 10},
            {"200", 17},
            {"-300", 20},
            {"400", 0},
            {"500", 0},
        });
    }

	public DepartmentSettingEmployeesTest(
		String deptId,
		int expectedSize) {
		super();
		this.dept = (Department) resource.getEObject(deptId);
		this.expectedSize = expectedSize;
	}
	
	@Test
	public void employeesHasCorrectSize() {
		assertEquals(this.expectedSize, this.dept.getEmployees().size());
	}
	
	private final Department dept;
	private final int expectedSize;

}
