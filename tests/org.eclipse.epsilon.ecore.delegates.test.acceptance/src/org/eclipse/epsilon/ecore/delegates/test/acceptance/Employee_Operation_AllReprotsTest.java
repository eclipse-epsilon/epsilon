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
public class Employee_Operation_AllReprotsTest {

	@BeforeClass
	public static void loadModel() throws IOException {
		InputStream stream = Employee_Operation_AllReprotsTest.class.getResourceAsStream("/ACME.xmi");
		ResourceSet rs = new ResourceSetImpl();
		resource = rs.createResource(URI.createURI("ACME.xmi"));
		resource.load(stream, null);
	}
	
	@Parameters( name = "Employee:Size {0}: {1}" )
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "6", 10}, {"3", 37} , {"14", 20}
        });
    }

	public Employee_Operation_AllReprotsTest(String employeeId, int expectedSize) {
		super();
		this.employee = (Employee) resource.getEObject(employeeId); 
		this.expectedSize = expectedSize;
	}

	@Test
	public void all_reports_is_closure() {
		assertEquals(this.expectedSize, employee.allReports().size());
	}
	
	private static Resource resource;
	private final Employee employee;
	private final int expectedSize;

}
