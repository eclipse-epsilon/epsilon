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
import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Employee_Setting_IsManagerTest {

	@BeforeClass
	public static void loadModel() throws IOException {
		InputStream stream = Employee_Setting_IsManagerTest.class.getResourceAsStream("/ACME.xmi");
		ResourceSet rs = new ResourceSetImpl();
		resource = rs.createResource(URI.createURI("ACME.xmi"));
		resource.load(stream, null);
	}
	
	@Parameters( name = "User:isManager {0}: {1}" )
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "6", true},
                {"45", false},
                {"3", true},
        });
    }

	public Employee_Setting_IsManagerTest(
		String employeeId,
		boolean isManager) {
		super();
		this.employee = (Employee) resource.getEObject(employeeId); 
		this.isManager = isManager;
	}
	
	@Test
	public void retports_to_managers() {
		assertEquals(this.isManager, this.employee.isIsManager());
	}
	
	private static Resource resource;
	private final Employee employee;
	private final boolean isManager;

}
