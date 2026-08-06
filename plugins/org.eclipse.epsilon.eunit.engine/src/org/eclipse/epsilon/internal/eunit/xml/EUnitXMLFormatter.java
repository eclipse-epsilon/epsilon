/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.internal.eunit.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.EUnitTestResultType;
import org.eclipse.epsilon.internal.eunit.io.ByteBufferTeePrintStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class which saves the results of an EUnit test run as an XML file, using the
 * de facto standard format used by the JUnit Ant task.
 * 
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class EUnitXMLFormatter {

	private EUnitModule module;

	public EUnitXMLFormatter(EUnitModule module) {
		this.module = module;
	}

	/**
	 * Generates a DOM tree with the results of the provided EUnit module, following
	 * the format of the JUnit Ant task.
	 */
	public Document generate() throws EolRuntimeException
	{
		// Create the document element
		Document doc;
		try {
			doc = createDocument();
		} catch (ParserConfigurationException e) {
			throw new EolInternalException(e);
		}
		Element suiteElem = doc.createElement("testsuite");
		doc.appendChild(suiteElem);

		// Populate the document with the results of the test suite
		final List<EUnitTest> allTests = populateSuiteElement(doc, suiteElem);
		saveProperties(doc, suiteElem);
		for (EUnitTest test : allTests) {
			saveTestCase(doc, suiteElem, test);
		}
		saveOutput(doc, suiteElem, "system-out",
				(ByteBufferTeePrintStream) module.getContext().getOutputStream());
		saveOutput(doc, suiteElem, "system-err",
				(ByteBufferTeePrintStream) module.getContext().getErrorStream());

		return doc;
	}

	/**
	 * Generates the report using {@link #generate()}, and then saves the XML document into a file.
	 *
	 * @param reportDirectory Destination directory.
	 */
	public void generate(File reportDirectory) throws EolRuntimeException
	{
		Document doc = generate();
		File reportFile = new File(reportDirectory,
			"TEST-" + module.getPackage() + "." + module.getClassName() + ".xml");

		try {
			// Uses an XSLT identity transform to save a DOM tree to a file.
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			
			try (FileOutputStream osReport = new FileOutputStream(reportFile)) {
				transformer.transform(new DOMSource(doc), new StreamResult(osReport));
			}
		} catch (Exception e) {
			throw new EolInternalException(e);
		}
	}

	private Document createDocument() throws ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument();
		return doc;
	}

	private void saveOutput(Document doc, Element suiteElem,
			final String tagName, ByteBufferTeePrintStream bbStdout) {
		Element sysOutElem = doc.createElement(tagName);
		suiteElem.appendChild(sysOutElem);
		sysOutElem.setTextContent(new String(bbStdout.getBytes()));
	}

	private void saveTestCase(Document doc, Element suiteElem, EUnitTest test) {
		final Element caseElem = doc.createElement("testcase");
		suiteElem.appendChild(caseElem);

		caseElem.setAttribute("classname", test.getQualifiedName(module.getPackage()));
		caseElem.setAttribute("name", test.getMethodName());
		caseElem.setAttribute("time", formatWallclockTime(test));

		Element resultChild = null;
		switch (test.getResult()) {
			case FAILURE:
				resultChild = doc.createElement("failure");
				break;
			case ERROR:
				resultChild = doc.createElement("error");
				break;
			default:
				break;
		}
		if (resultChild != null) {
			caseElem.appendChild(resultChild);
			resultChild.setAttribute("message", test.getException()
					.getMessage());
			resultChild.setAttribute("type", test.getException().getClass()
					.getName());

			final StringWriter stringWriter = new StringWriter();
			test.getException().printStackTrace(new PrintWriter(stringWriter));
			resultChild.setTextContent(stringWriter.toString());
		}
	}

	private void saveProperties(Document doc, Element suiteElem) {
		Element propertiesElem = doc.createElement("properties");
		suiteElem.appendChild(propertiesElem);
		Properties sysProperties = System.getProperties();
		for (Entry<Object, Object> entry : sysProperties.entrySet()) {
			Element propertyElem = doc.createElement("property");
			propertiesElem.appendChild(propertyElem);
			propertyElem.setAttribute("name", entry.getKey().toString());
			propertyElem.setAttribute("value", entry.getValue().toString());
		}
	}

	private List<EUnitTest> populateSuiteElement(Document doc, Element suiteElem) throws EolRuntimeException {
		final EUnitTest root = module.getSuiteRoot();

		@SuppressWarnings("rawtypes")
		final List selectedOperations = module.getSelectedOperations();

		final List<EUnitTest> allTests = root.collectLeafTests(selectedOperations, null);
		final List<EUnitTest> testsWithErrors = root.collectLeafTests(selectedOperations, EUnitTestResultType.ERROR);
		final List<EUnitTest> testsWithFailures = root.collectLeafTests(selectedOperations, EUnitTestResultType.FAILURE);

		suiteElem.setAttribute("name", module.getQualifiedName());
		suiteElem.setAttribute("time", formatWallclockTime(root));
		suiteElem.setAttribute("tests", Integer.toString(allTests.size()));
		suiteElem.setAttribute("errors",
				Integer.toString(testsWithErrors.size()));
		suiteElem.setAttribute("failures",
				Integer.toString(testsWithFailures.size()));
		try {
			suiteElem.setAttribute("hostname", InetAddress.getLocalHost()
					.getHostName());
		} catch (UnknownHostException ex) {
			suiteElem.setAttribute("hostname", "localhost");
		}
		return allTests;
	}

	private String formatWallclockTime(final EUnitTest root) {
		return String.format(Locale.ENGLISH, "%g",
				root.getWallclockTimeMillis() / 1000.0);
	}

}
