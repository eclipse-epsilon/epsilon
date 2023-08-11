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
import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Employee_Setting_DepartmentTest {

	@BeforeClass
	public static void loadModel() throws IOException {
		InputStream stream = Employee_Setting_DepartmentTest.class.getResourceAsStream("/ACME.xmi");
		ResourceSet rs = new ResourceSetImpl();
		resource = rs.createResource(URI.createURI("ACME.xmi"));
		resource.load(stream, null);
	}
	
	@Parameters( name = "Employee:Department {0}: {1}" )
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "9", "100"},
                {"37", "-300"},
                {"14", "200"},
        });
    }

	

	public Employee_Setting_DepartmentTest(
		String employeeId,
		String deptId) {
		super();
		this.employee = (Employee) resource.getEObject(employeeId); 
		this.dept = (Department) resource.getEObject(deptId); ;
	}
	
	@Test
	public void retports_to_managers() {
		assertEquals(this.dept, this.employee.getDepartment());
	}
	
	private static Resource resource;
	private final Employee employee;
	private final Department dept;

}
