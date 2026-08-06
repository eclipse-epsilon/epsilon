/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.xml;

import static org.junit.Assert.assertTrue;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.junit.BeforeClass;
import org.junit.Test;

public class XmlModelTests {
	
	private static String SCHEMA_FILE_PATH, MODEL_PATH;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Class<?> relClass = XmlModelTests.class;
		// Load dependencies
		FileUtil.getFileStandalone("graphml/yfeatures.xsd", relClass);
		FileUtil.getFileStandalone("graphml/ygraphics.xsd", relClass);
		FileUtil.getFileStandalone("graphml/yprocessors.xsd", relClass);
		FileUtil.getFileStandalone("graphml/xlink.xsd", relClass);
		FileUtil.getFileStandalone("graphml/graphml-structure.xsd", relClass);
		SCHEMA_FILE_PATH = FileUtil.getFileStandalone("graphml/ygraphml.xsd", relClass).getAbsolutePath();
		MODEL_PATH = FileUtil.getFileStandalone("graphml/Example.graphml", relClass).getAbsolutePath();
	}
	
	@Test
	public void testFileURI() throws Exception {
		XmlModel model = createXmlModel(URI.createFileURI(MODEL_PATH).toString(), SCHEMA_FILE_PATH);
		Resource resource = model.getResource();
		resource.getContents().add(createRootElement(resource));
		assertTrue(model.store());
	}
	
	private XmlModel createXmlModel(String uri, String xsdFile) throws EolModelLoadingException {
	  	XmlModel xmlModel = new XmlModel();
		StringProperties properties = new StringProperties();
	    properties.put(XmlModel.PROPERTY_MODEL_URI, uri);
	    properties.put(XmlModel.PROPERTY_XSD_URI, URI.createFileURI(xsdFile).toString());
	    properties.put(XmlModel.PROPERTY_READONLOAD, "false");
	    properties.put(XmlModel.PROPERTY_STOREONDISPOSAL, "true");
	    xmlModel.load(properties);
		return xmlModel;
	}
	
	
	private EObject createRootElement(Resource resource) {
		EPackage ePck = resource.getResourceSet().getPackageRegistry().getEPackage("http://www.yworks.com/xml/graphml");
		EClass rootEClass = (EClass) ePck.getEClassifier("DocumentRoot");
		return ePck.getEFactoryInstance().create(rootEClass);
	}
}
