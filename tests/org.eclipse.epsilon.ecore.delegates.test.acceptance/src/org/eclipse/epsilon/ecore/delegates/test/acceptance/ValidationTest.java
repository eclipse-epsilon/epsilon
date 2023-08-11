package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidationTest {
	
	@BeforeClass
	public static void loadModel() throws IOException {
		InputStream stream = Employee_Setting_DepartmentTest.class.getResourceAsStream("/ACME.xmi");
		ResourceSet rs = new ResourceSetImpl();
		resource = rs.createResource(URI.createURI("ACME.xmi"));
		resource.load(stream, null);
	}

	@Test
	public void root_validation_correct_total() {
		Diagnostic d = Diagnostician.INSTANCE.validate(resource.getContents().get(0));
		assertEquals(10, d.getChildren().size());
	}
	
	@Test
	public void invalid_names() {
		Diagnostic d = Diagnostician.INSTANCE.validate(resource.getContents().get(0));
		List<Diagnostic> results = d.getChildren();
		assertEquals(5, results.stream()
				.filter(r -> r.getMessage().contains("'validName'"))
				.count());
		assertTrue(results.stream()
				.filter(r -> r.getMessage().contains("'validName'"))
				.map(r -> r.getData().get(0))
				.allMatch(Employee.class::isInstance));
	}
	
	@Test
	public void invalid_ids() {
		Diagnostic d = Diagnostician.INSTANCE.validate(resource.getContents().get(0));
		List<Diagnostic> results = d.getChildren();
		assertEquals(4, results.stream()
				.filter(r -> r.getMessage().contains("'positiveID'"))
				.count());
		assertEquals(3, results.stream()
				.filter(r -> r.getMessage().contains("'positiveID'"))
				.map(r -> r.getData().get(0))
				.filter(Employee.class::isInstance)
				.count());
	}
	
	private static Resource resource;
}
