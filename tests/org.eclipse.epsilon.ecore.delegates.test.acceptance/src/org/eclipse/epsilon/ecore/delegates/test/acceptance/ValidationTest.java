package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.epsilon.ecore.delegates.test.employee.Employee;
import org.junit.Test;

public class ValidationTest extends AcmeTests {
	
	@Test
	public void rootValidationCorrectTotal() {
		Diagnostic d = Diagnostician.INSTANCE.validate(resource.getContents().get(0));
		assertEquals(10, d.getChildren().size());
	}
	
	@Test
	public void invalidNames() {
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
	public void invalidIDs() {
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
}
