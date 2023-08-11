package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.ecore.delegates.test.employee.Department;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Department_Setting_EmployeesTest {

	@BeforeClass
	public static void loadModel() throws IOException {
		InputStream stream = Department_Setting_EmployeesTest.class.getResourceAsStream("/ACME.xmi");
		ResourceSet rs = new ResourceSetImpl();
		resource = rs.createResource(URI.createURI("ACME.xmi"));
		resource.load(stream, null);
	}
	
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

	public Department_Setting_EmployeesTest(
		String deptId,
		int expectedSize) {
		super();
		this.dept = (Department) resource.getEObject(deptId);
		this.expectedSize = expectedSize;
	}
	
	@Test
	public void retports_to_managers() {
		assertEquals(this.expectedSize, this.dept.getEmployees().size());
	}
	
	private static Resource resource;
	private final Department dept;
	private final int expectedSize;

}
