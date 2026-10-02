/********************************************************************************
 * Copyright (c) 2026 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.emf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.rdf.emf.IllFormedDateCorrector;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the {@code lenientDates} option, which corrects ill-formed
 * date/time literals during loading.
 */
public class LenientDatesTest {

	private static final String NS_URI = "http://eclipse.org/epsilon/rdf/lenientDates";

	private static EClass requirementEClass;
	private static EAttribute modifiedOnEAttribute;

	@BeforeClass
	public static void setup() {
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap()
			.put("rdfres", new RDFGraphResourceFactory());

		EcoreFactory factory = EcoreFactory.eINSTANCE;
		EPackage ePackage = factory.createEPackage();
		ePackage.setName("lenientDates");
		ePackage.setNsPrefix("lenientDates");
		ePackage.setNsURI(NS_URI);

		requirementEClass = factory.createEClass();
		requirementEClass.setName("Requirement");
		ePackage.getEClassifiers().add(requirementEClass);

		modifiedOnEAttribute = factory.createEAttribute();
		modifiedOnEAttribute.setName("modifiedOn");
		modifiedOnEAttribute.setEType(EcorePackage.eINSTANCE.getEDate());
		requirementEClass.getEStructuralFeatures().add(modifiedOnEAttribute);

		EPackage.Registry.INSTANCE.put(NS_URI, ePackage);
	}

	@Test
	public void strictByDefault() {
		assertThrows(DatatypeFormatException.class, () -> loadResource("strict.rdfres"));
	}

	@Test
	public void lenientCorrectsDates() {
		RDFGraphResourceImpl r = loadResource("lenient.rdfres");

		// No timezone in the original literal: Jena uses the JVM default timezone
		Instant localMidnight = LocalDateTime.parse("2026-09-10T00:00:00").atZone(ZoneId.systemDefault()).toInstant();
		assertEquals(localMidnight, getModifiedOn(r, "dateOnly"));
		assertEquals(Instant.parse("2026-09-01T12:05:00Z"), getModifiedOn(r, "noSeconds"));
		assertEquals(Instant.parse("2026-09-10T11:12:13Z"), getModifiedOn(r, "valid"));
	}

	@Test
	public void correctLexicalForms() {
		assertEquals("2026-09-10T00:00:00", correct("2026-09-10", XSDDatatype.XSDdateTime));
		assertEquals("2026-09-10T00:00:00Z", correct("2026-09-10Z", XSDDatatype.XSDdateTime));
		assertEquals("2026-09-01T04:05:00+02:00", correct("2026-9-1T4:5+0200", XSDDatatype.XSDdateTime));
		assertEquals("2026-09-10T04:05:06.789-05:00", correct(" 2026-09-10 04:05:06.789 -05:00 ", XSDDatatype.XSDdateTime));
		assertEquals("2026-09-10T00:00:00Z", correct("2026-09-10", XSDDatatype.XSDdateTimeStamp));
		assertEquals("2026-09-10", correct("2026-09-10T04:05:06", XSDDatatype.XSDdate));
		assertEquals("2026-09-10+01:00", correct("2026-09-10T04:05:06+01:00", XSDDatatype.XSDdate));

		// Uncorrectable values
		assertNull(correct("yesterday", XSDDatatype.XSDdateTime));
		assertNull(correct("2026-13-10", XSDDatatype.XSDdateTime));
		assertNull(correct("2026-09-10", XSDDatatype.XSDstring));
	}

	private String correct(String lexicalForm, XSDDatatype dt) {
		return IllFormedDateCorrector.correctLexicalForm(lexicalForm, dt);
	}

	private RDFGraphResourceImpl loadResource(String fileName) {
		ResourceSet rs = new ResourceSetImpl();
		Path rdfResPath = Paths.get("resources", "lenientDates", fileName);
		URI rdfResURI = URI.createFileURI(rdfResPath.toAbsolutePath().toString());
		return (RDFGraphResourceImpl) rs.getResource(rdfResURI, true);
	}

	private Instant getModifiedOn(RDFGraphResourceImpl r, String localName) {
		String iri = "http://example.org/req/" + localName;
		for (EObject eob : r.getContents()) {
			if (iri.equals(r.getRDFResource(eob).getURI())) {
				return ((Date) eob.eGet(modifiedOnEAttribute)).toInstant();
			}
		}
		throw new AssertionError("Could not find requirement " + iri);
	}

}
