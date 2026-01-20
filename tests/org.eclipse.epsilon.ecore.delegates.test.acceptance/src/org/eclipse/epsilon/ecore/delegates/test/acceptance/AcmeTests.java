package org.eclipse.epsilon.ecore.delegates.test.acceptance;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.BeforeClass;

public class AcmeTests {

	@BeforeClass
	public static void loadModel() throws IOException {
		InputStream stream = EmployeeSettingDepartmentTest.class.getResourceAsStream("/ACME.xmi");
		ResourceSet rs = new ResourceSetImpl();
		resource = rs.createResource(URI.createURI("ACME.xmi"));
		resource.load(stream, null);
	}

	protected static Resource resource;

	public AcmeTests() {
		super();
	}

}