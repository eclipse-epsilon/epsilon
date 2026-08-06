package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

public class EmfMetaModelTests {
	
	@Test
	public void testEcoreItself() throws Exception {
		
		EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		
		EmfMetaModel metamodel = new EmfMetaModel("M2", EcorePackage.eNS_URI);
		metamodel.load();
		
		assertTrue(metamodel.hasType("EClass"));
		assertTrue(metamodel.getAllOfKind("EStructuralFeature").size() > 0);
	}
	
}
