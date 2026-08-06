/*******************************************************************************
 * Copyright (c) 2011-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 *     Sina Madani - stateless refactoring (i.e. work outside IDE in JAR)
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.eunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.CallTarget;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.MacroDef;
import org.apache.tools.ant.taskdefs.Property;
import org.apache.tools.ant.taskdefs.Sequential;
import org.apache.tools.ant.taskdefs.TempFile;
import org.apache.tools.ant.taskdefs.optional.junit.XMLResultAggregator;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.workflow.tasks.EUnitTask;
import org.eclipse.epsilon.workflow.tasks.EglTask;
import org.eclipse.epsilon.workflow.tasks.EtlTask;
import org.eclipse.epsilon.workflow.tasks.EvlTask;
import org.eclipse.epsilon.workflow.tasks.StoreModelTask;
import org.eclipse.epsilon.workflow.tasks.emf.LoadEmfModelTask;
import org.eclipse.epsilon.workflow.tasks.emf.RegisterTask;
import org.eclipse.epsilon.workflow.test.WorkflowTestCase;
import org.junit.After;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Abstract superclass for all EUnit test suites.
 *
 * @author Antonio Garcia-Dominguez
 */
public abstract class EUnitTestCase extends WorkflowTestCase implements ErrorHandler {

	protected static final File BASE_DIR;
	protected static final File ANT_BUILD_FILE;

	static {
		File baseTemp = null;
		try {
			FileUtil.getDirectoryStandalone("../../resources", EUnitTestCase.class);
			baseTemp = FileUtil.getDirectoryStandalone("../../resources/eunit", EUnitTestCase.class);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		if (baseTemp == null) {
			// Plugged-in test
			String path = "../org.eclipse.epsilon.workflow.test/src/org/eclipse/epsilon/workflow/resources/eunit/";
			if (!(baseTemp = new File(path)).exists()) {
				baseTemp = new File(path.replace("/src/", "/bin/"));
			}
			if (!(baseTemp = new File(path)).exists()) {
				baseTemp = new File(path.replace("/src/", "/target/"));
			}
			if (!(baseTemp = new File(path)).exists()) {
				baseTemp = new File(path.replace("/src/", ""));
			}
		}
		BASE_DIR = baseTemp;
		ANT_BUILD_FILE = new File(BASE_DIR, "eunit.xml");
	}
	
	@Override
	protected void addTaskDefinitionsTo(Project project) {
		project.addTaskDefinition("antcall", CallTarget.class);
		project.addTaskDefinition("delete", Delete.class);
		project.addTaskDefinition("junitreport", XMLResultAggregator.class);
		project.addTaskDefinition("macrodef", MacroDef.class);
		project.addTaskDefinition("property", Property.class);
		project.addTaskDefinition("sequential", Sequential.class);
		project.addTaskDefinition("tempfile", TempFile.class);
	
		// Epsilon tasks
		project.addTaskDefinition("epsilon.egl", EglTask.class);
		project.addTaskDefinition("epsilon.emf.loadModel", LoadEmfModelTask.class);
		project.addTaskDefinition("epsilon.emf.register", RegisterTask.class);
		project.addTaskDefinition("epsilon.etl", EtlTask.class);
		project.addTaskDefinition("epsilon.eunit", EUnitTask.class);
		project.addTaskDefinition("epsilon.evl", EvlTask.class);
		project.addTaskDefinition("epsilon.storeModel", StoreModelTask.class);
	}

	@After
	public void cleanUp() {
		deleteTestReports(BASE_DIR);
		deleteTestReports(BASE_DIR.getParentFile());
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		failSAXParseException("error", exception);
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		failSAXParseException("fatal error", exception);
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		failSAXParseException("warning", exception);
	}

	/**
	 * Checks that the JUnit-style XML reports are well-formed. There is no
	 * official schema for these, so we have defined our own schema for internal
	 * testing. We've based it on <a href="http://goo.gl/N5ZgP">the Ant source
	 * code</a> and <a href="http://goo.gl/Sznbo">this mailing list post</a>.
	 * @throws SAXException
	 *             XML Schema could not be loaded, or the JUnit XML output could
	 *             not be parsed or was invalid.
	 * @throws ParserConfigurationException
	 *             There was a problem while configuring the XML parser for the
	 *             JUnit XML output.
	 * @throws IOException
	 *             There was a problem while reading from the specified path.
	 */
	protected void checkOutput(File file, String expectedPackage, String[] expectedTestCases,
			Set<String> expectedCasesWithFailures, Set<String> expectedCasesWithErrors) throws SAXException,
			ParserConfigurationException, IOException {
				Schema junitXSD = loadJUnitSchema();
				Document doc = parseAndValidate(file, junitXSD);
			
				final Element suiteElem = doc.getDocumentElement();
				final String qualifiedSuiteName = suiteElem.getAttribute("name");
				final String expectedPackagePrefix = expectedPackage + ".";
				assertTrue(qualifiedSuiteName.startsWith(expectedPackagePrefix));
			
				NodeList docChildren = suiteElem.getChildNodes();
				int nTestCases = 0;
				String sysOut = null;
				String sysErr = null;
				for (int i = 0; i < docChildren.getLength(); ++i) {
					Node n = docChildren.item(i);
					if (n instanceof Element) {
						Element e = (Element)n;
						final String tagName = e.getTagName();
						if ("testcase".equals(tagName)) {
							if (nTestCases >= expectedTestCases.length) {
								fail(String.format(
									"There are more test cases (%d or more) than expected (%d)",
									nTestCases + 1, expectedTestCases.length));
							}
							
							final String testCaseName = e.getAttribute("name");
							assertEquals(
								String.format("The %d-th test case in the report should be %s",
										nTestCases+1, expectedTestCases[nTestCases]),
								expectedTestCases[nTestCases], testCaseName);

							final String className = e.getAttribute("classname");
							assertTrue(className.startsWith(expectedPackagePrefix));
			
							if (expectedCasesWithFailures.contains(testCaseName)) {
								assertHasChildWithTag(e, "failure");
							}
							else if (expectedCasesWithErrors.contains(testCaseName)) {
								assertHasChildWithTag(e, "error");
							}
							else {
								assertEquals("The test " + testCaseName + " should pass",
									0, e.getChildNodes().getLength());
							}
							nTestCases++;
						}
						else if (tagName.equals("system-out")) {
							sysOut = e.getTextContent();
						}
						else if (tagName.equals("system-err")) {
							sysErr = e.getTextContent();
						}
					}
				}
				assertEquals(
					"There should be " + expectedTestCases.length + " test cases in the report",
					nTestCases, expectedTestCases.length);
			
				assertNotNull("Report should include stdout", sysOut);
				assertNotNull("Report should include stderr", sysErr);
				for (String testCase : expectedTestCases) {
					assertTrue("Report should mention test case " + testCase,
							sysOut.contains(testCase) || sysErr.contains(testCase));
				}
			}

	private void assertHasChildWithTag(Element e, final String findTagName) {
		final NodeList caseChildren = e.getChildNodes();
		for (int iCase = 0; iCase < caseChildren.getLength(); ++iCase) {
			Node nCase = caseChildren.item(iCase);
			if (nCase instanceof Element) {
				Element elemCase = (Element)nCase;
				if (elemCase.getTagName().equals(findTagName)) {
					return;
				}
			}
		}
		fail("Element " + e.getAttribute("name") + " does not have a child with the tag " + findTagName);
	}

	private void deleteTestReports(final File directory) {
		for (String entry : directory.list()) {
			if (entry.startsWith("TEST") && entry.endsWith(".xml") || entry.endsWith(".html")) {
				new File(directory, entry).delete();
			}
		}
	}

	private void failSAXParseException(String type, SAXParseException exception) {
		fail(String.format("SAX %s at %d:%d: %s",
				type,
				exception.getLineNumber(),
				exception.getColumnNumber(),
				exception.getMessage()));
	}

	private Schema loadJUnitSchema() throws SAXException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema junitXSD = schemaFactory.newSchema(new File(BASE_DIR, "junit.xsd"));
		return junitXSD;
	}

	private Document parseAndValidate(File file, Schema junitXSD) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setSchema(junitXSD);
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		docBuilder.setErrorHandler(this);
		Document doc = docBuilder.parse(file);
		return doc;
	}
}
